package DAO;

import modelos.Trabajo;
import java.sql.*;
import java.util.ArrayList;

public class TrabajoDAO {

    // Método para obtener los trabajos que tienen una máquina null
    public ArrayList<Trabajo> getTrabajosSinMaquina(Connection conexion) {
        ArrayList<Trabajo> trabajosSinMaquina = new ArrayList<>();
        String sql = "SELECT * FROM trabajos WHERE idMaquina IS NULL";  // Consulta SQL para obtener trabajos sin máquina

        try (Statement stmt = conexion.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                int idTrabajo = rs.getInt("idTrabajo");
                int idAgricultor = rs.getInt("idAgricultor");
                String tipo = rs.getString("tipo");
                Date fechaInicio = rs.getDate("fechaInicio");
                Date fechaFin = rs.getDate("fechaFin");
                Integer idMaquina = (Integer) rs.getObject("idMaquina");  // Será null si no tiene máquina
                Integer idMaquinista = (Integer) rs.getObject("idMaquinista");  // Puede ser null
                int idParcela = rs.getInt("idParcela");
                String estado = rs.getString("estado");

                // Crear objeto Trabajo y añadirlo a la lista
                Trabajo trabajo = new Trabajo(idTrabajo, idAgricultor, tipo, fechaInicio, fechaFin, idMaquina, idMaquinista, idParcela, estado);
                trabajosSinMaquina.add(trabajo);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return trabajosSinMaquina;
    }

    public boolean asignarMaquinaATrabajo(Connection con, int idTrabajo, int idMaquina) {
        String sql = "UPDATE trabajos SET idMaquina = ? WHERE idTrabajo = ?";

        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, idMaquina);
            stmt.setInt(2, idTrabajo);

            int filasActualizadas = stmt.executeUpdate();
            return filasActualizadas > 0; // Devuelve true si se actualizó correctamente
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean crearTrabajo(Connection con, int idAgricultor, String tipo, int idParcela) {
        String sql = "INSERT INTO trabajos (idAgricultor, tipo, fechaInicio, fechaFin, idMaquina, idMaquinista, idParcela, estado) "
                + "VALUES (?, ?, NULL, NULL, NULL, NULL, ?, 'pendiente')";

        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, idAgricultor);
            stmt.setString(2, tipo);
            stmt.setInt(3, idParcela);

            int filasInsertadas = stmt.executeUpdate();
            return filasInsertadas > 0; // Devuelve true si la inserción fue exitosa
        } catch (SQLException e) {
            e.printStackTrace();
            return false; // Devuelve false en caso de error
        }
    }

}
