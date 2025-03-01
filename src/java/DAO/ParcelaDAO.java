package DAO;

import modelos.Parcela;
import java.sql.*;
import java.util.ArrayList;


public class ParcelaDAO {

    // Método para añadir una parcela
    public boolean insertarParcela(Connection conexion, Parcela parcela) {
        String sql = "INSERT INTO parcelas (catastro, idAgricultor, superficie) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setString(1, parcela.getCatastro());
            stmt.setInt(2, parcela.getIdUsuario());
            stmt.setInt(3, parcela.getSuperficie()); // Cambio a int
            int filasAfectadas = stmt.executeUpdate();
            return filasAfectadas > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Método para eliminar una parcela por catastro
    public boolean eliminarParcela(Connection conexion, String catastro) {
        String sql = "DELETE FROM parcelas WHERE catastro = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setString(1, catastro);
            int filasAfectadas = stmt.executeUpdate();
            return filasAfectadas > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Método para obtener todas las parcelas
    public ArrayList<Parcela> getParcelas(Connection conexion) {
        ArrayList<Parcela> listaParcelas = new ArrayList<>();
        String sql = "SELECT * FROM parcelas";
        try (Statement stmt = conexion.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                String catastro = rs.getString("catastro");
                int idUsuario = rs.getInt("idAgricultor");
                int superficie = rs.getInt("superficie");

                Parcela parcela = new Parcela(catastro, idUsuario, superficie);
                listaParcelas.add(parcela);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listaParcelas;
    }

    // PARCELAS AGRICULTOR
// Método para obtener las parcelas de un agricultor específico
    public ArrayList<Parcela> getParcelasPorAgricultor(Connection conexion, int idUsuario) {
        ArrayList<Parcela> listaParcelas = new ArrayList<>();
        String sql = "SELECT * FROM parcelas WHERE idAgricultor = ?";  // Filtra por el idAgricultor
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, idUsuario);  // Establece el ID del agricultor como parámetro
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    String catastro = rs.getString("catastro");  // Obtén el catastro de la parcela
                    int superficie = rs.getInt("superficie");  // Obtén la superficie de la parcela

                    // Crea un objeto Parcela con los datos obtenidos
                    Parcela parcela = new Parcela(catastro, idUsuario, superficie);
                    listaParcelas.add(parcela);  // Añade la parcela a la lista
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listaParcelas;  // Devuelve la lista de parcelas
    }


}
