package DAO;

import modelos.Maquina;
import java.sql.*;
import java.util.ArrayList;

public class MaquinaDAO {

    // Método para añadir una máquina
    public void insertarMaquina(Connection conexion, Maquina maquina) {
        String query = "INSERT INTO maquinas (modelo, tipo, capacidad, anho, estado) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = conexion.prepareStatement(query)) {
            stmt.setString(1, maquina.getModelo());
            stmt.setString(2, maquina.getTipo());
            stmt.setInt(3, maquina.getCapacidad());
            stmt.setInt(4, maquina.getAnho());
            stmt.setString(5, maquina.getEstado());

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Método para eliminar una máquina por su ID
    public void eliminarMaquina(Connection conexion, int id) {
        String query = "DELETE FROM maquinas WHERE id = ?";

        try (PreparedStatement stmt = conexion.prepareStatement(query)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Método para obtener todas las máquinas
    public ArrayList<Maquina> getMaquinas(Connection conexion) {
        ArrayList<Maquina> maquinas = new ArrayList<>();
        String query = "SELECT * FROM maquinas";

        try (Statement stmt = conexion.createStatement(); ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String modelo = rs.getString("modelo");
                String tipo = rs.getString("tipo");
                int capacidad = rs.getInt("capacidad");
                int anho = rs.getInt("anho");
                String estado = rs.getString("estado");

                Maquina maquina = new Maquina(id, modelo, tipo, capacidad, anho, estado);
                maquinas.add(maquina);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return maquinas;
    }

    public boolean cambiarEstadoMaquina(Connection con, int idMaquina, String nuevoEstado) {
        String sql = "UPDATE maquinas SET estado = ? WHERE id = ?";

        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, nuevoEstado.toLowerCase()); // Se convierte a minúsculas para que coincida con MySQL
            stmt.setInt(2, idMaquina);

            int filasActualizadas = stmt.executeUpdate();
            return filasActualizadas > 0; // Devuelve true si la actualización tuvo éxito
        } catch (SQLException e) {
            System.err.println("Error al actualizar el estado de la máquina: " + e.getMessage());
            return false;
        }
    }

}
