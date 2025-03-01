package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import modelos.Usuario;

public class UsuarioDAO {

    public Usuario crearUsuario(Connection BD, String email, String password) {
        String selectSQL = "SELECT * FROM usuarios WHERE email = ? AND password = ?";
        Usuario usuario = null;

        try (PreparedStatement pst = BD.prepareStatement(selectSQL)) {
            pst.setString(1, email);
            pst.setString(2, password);
            try (ResultSet rst = pst.executeQuery()) {
                if (rst.next()) {
                    usuario = new Usuario(
                            rst.getString("id"),
                            rst.getString("nombre"),
                            rst.getString("email"),
                            rst.getString("password"),
                            rst.getInt("rol")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return usuario;
    }
}
