package DAO;

import java.sql.*;


public class AdministradorDAO {


    // Método para insertar un nuevo usuario
    public boolean insertarUsuario(Connection conexion, String usuario, String email, String contraseña, int rol) {
        String insertSQL = "INSERT INTO usuarios (nombre, email, password, rol) VALUES (?, ?, ?, ?)";
        boolean resultado = false;

        try (PreparedStatement pst = conexion.prepareStatement(insertSQL)) {
            // Establecemos los valores para la consulta
            pst.setString(1, usuario);
            pst.setString(2, email);
            pst.setString(3, contraseña);
            pst.setInt(4, rol);

            // Ejecutamos la inserción
            int filasAfectadas = pst.executeUpdate();

            if (filasAfectadas > 0) {
                resultado = true;  // Si la inserción fue exitosa
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return resultado;
    }

    // Método para eliminar un usuario
    public boolean eliminarUsuario(Connection conexion, int id) {
        String deleteSQL = "DELETE FROM usuarios WHERE id = ?";
        boolean resultado = false;

        try (PreparedStatement pst = conexion.prepareStatement(deleteSQL)) {
            // Establecemos el valor del id para la consulta
            pst.setInt(1, id);

            // Ejecutamos la eliminación
            int filasAfectadas = pst.executeUpdate();

            if (filasAfectadas > 0) {
                resultado = true;  // Si la eliminación fue exitosa
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return resultado;
    }

}
