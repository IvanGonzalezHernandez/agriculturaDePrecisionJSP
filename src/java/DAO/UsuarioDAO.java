package DAO;

import com.mysql.jdbc.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import modelos.Usuario;

public class UsuarioDAO {

// Método para obtener todos los usuarios
    public ArrayList<Usuario> getUsuarios(Connection BD) {
        String selectSQL = "SELECT * FROM usuarios";
        ArrayList<Usuario> arrayUsuarios = new ArrayList<>();

        try {
            // Crear una sentencia para ejecutar la consulta
            java.sql.Statement st = BD.createStatement();
            ResultSet rs = st.executeQuery(selectSQL);

            // Procesar los resultados de la consulta
            while (rs.next()) {
                int id = rs.getInt("id");
                String nombre = rs.getString("nombre");
                String email = rs.getString("email");
                String password = rs.getString("password");
                int rol = rs.getInt("rol");

                // Crear un nuevo objeto Usuario con todos los atributos
                arrayUsuarios.add(new Usuario(id, nombre, email, password, rol));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return arrayUsuarios;
    }

    public Usuario crearUsuario(Connection BD, String email, String password) {
        String selectSQL = "SELECT * FROM usuarios WHERE email = ? AND password = ?";
        Usuario usuario = null;

        try (PreparedStatement pst = BD.prepareStatement(selectSQL)) {
            pst.setString(1, email);
            pst.setString(2, password);
            try (ResultSet rst = pst.executeQuery()) {
                if (rst.next()) {
                    usuario = new Usuario(
                            rst.getInt("id"),
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
