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
                + "VALUES (?, ?, NULL, NULL, NULL, NULL, ?, 'pendiente')"; // 'pendiente' para estado inicial

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

    public ArrayList<Trabajo> obtenerTrabajosSinFechaFin(Connection con) {
        ArrayList<Trabajo> trabajos = new ArrayList<>();
        String sql = "SELECT * FROM trabajos WHERE idMaquina IS NOT NULL AND fechaFin IS NULL";

        try (PreparedStatement stmt = con.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Trabajo trabajo = new Trabajo(
                        rs.getInt("idTrabajo"),
                        rs.getInt("idAgricultor"),
                        rs.getString("tipo"),
                        rs.getDate("fechaInicio"),
                        null, // fechaFin es NULL
                        rs.getInt("idMaquina"),
                        rs.getInt("idMaquinista"),
                        rs.getInt("idParcela"),
                        rs.getString("estado")
                );
                trabajos.add(trabajo);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return trabajos;
    }

    public ArrayList<Trabajo> obtenerTrabajosFinalizadosAgricultor(Connection con, int idAgricultor) {
        ArrayList<Trabajo> trabajos = new ArrayList<>();
        String sql = "SELECT * FROM trabajos WHERE estado = 'finalizado' AND idAgricultor = ?";

        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, idAgricultor);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Trabajo trabajo = new Trabajo(
                            rs.getInt("idTrabajo"),
                            rs.getInt("idAgricultor"),
                            rs.getString("tipo"),
                            rs.getDate("fechaInicio"),
                            rs.getDate("fechaFin"), // Ahora se obtiene la fecha de finalización
                            rs.getInt("idMaquina"),
                            rs.getInt("idMaquinista"),
                            rs.getInt("idParcela"),
                            rs.getString("estado")
                    );
                    trabajos.add(trabajo);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return trabajos;
    }

    public ArrayList<Trabajo> obtenerTrabajosFinalizadosMaquinista(Connection con, int idMaquinista) {
        ArrayList<Trabajo> trabajos = new ArrayList<>();
        String sql = "SELECT * FROM trabajos WHERE estado = 'finalizado' AND idMaquinista = ?";

        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, idMaquinista);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Trabajo trabajo = new Trabajo(
                            rs.getInt("idTrabajo"),
                            rs.getInt("idAgricultor"),
                            rs.getString("tipo"),
                            rs.getDate("fechaInicio"),
                            rs.getDate("fechaFin"), // Ahora se obtiene la fecha de finalización
                            rs.getInt("idMaquina"),
                            rs.getInt("idMaquinista"),
                            rs.getInt("idParcela"),
                            rs.getString("estado")
                    );
                    trabajos.add(trabajo);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return trabajos;
    }

    // Método para comenzar un trabajo y asignar el maquinista
    public boolean comenzarTrabajo(Connection con, int idTrabajo, int idMaquinista) {
        String sql = "UPDATE trabajos SET fechaInicio = CURRENT_DATE, estado = 'procesado', idMaquinista = ? WHERE idTrabajo = ?";
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, idMaquinista);
            stmt.setInt(2, idTrabajo);
            int filasActualizadas = stmt.executeUpdate();
            return filasActualizadas > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean finalizarTrabajo(Connection con, int idTrabajo, Date fechaFin) {
        String sql = "UPDATE trabajos SET fechaFin = ?, estado = 'finalizado' WHERE idTrabajo = ?";
        String actualizarMaquina = "UPDATE maquinas SET estado = 1 WHERE idMaquina = (SELECT idMaquina FROM trabajos WHERE idTrabajo = ?)";

        try (PreparedStatement stmtTrabajo = con.prepareStatement(sql); PreparedStatement stmtMaquina = con.prepareStatement(actualizarMaquina)) {

            // Actualizar trabajo
            stmtTrabajo.setDate(1, fechaFin);
            stmtTrabajo.setInt(2, idTrabajo);
            int filasTrabajo = stmtTrabajo.executeUpdate();

            // Actualizar estado de la máquina
            stmtMaquina.setInt(1, idTrabajo);
            int filasMaquina = stmtMaquina.executeUpdate();

            return filasTrabajo > 0 && filasMaquina > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

}
