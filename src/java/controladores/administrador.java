package controladores;

import DAO.ConectorBD;
import DAO.AdministradorDAO;
import DAO.MaquinaDAO;
import DAO.ParcelaDAO;
import DAO.UsuarioDAO;
import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import modelos.Maquina;
import modelos.Parcela;
import modelos.Usuario;

public class administrador extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        // Recuperar la sesión actual o crear una nueva si no existe
        HttpSession session = request.getSession(true);

        // Crear la conexión a la base de datos
        ConectorBD conector = new ConectorBD("localhost", "agriculturadeprecisionjsp", "root", "");
        Connection conexion = conector.getConexion();

        // Obtener el usuarioDAO a la base de datos desde el contexto
        UsuarioDAO usuarioDAO = (UsuarioDAO) getServletContext().getAttribute("usuarioDAO");

        // Obtener la lista de usuarios desde la base de datos
        ArrayList<Usuario> usuarios = usuarioDAO.getUsuarios(conexion);

        // Almacenar la lista de usuarios en el contexto de la aplicación
        getServletContext().setAttribute("usuarios", usuarios);

        // Obtener la lista de máquinas desde la base de datos
        MaquinaDAO maquinaDAO = new MaquinaDAO();

        // Obtener la lista de maquinas
        ArrayList<Maquina> maquinas = maquinaDAO.getMaquinas(conexion);

        // Almacenar la lista de parcelas en el contexto de la aplicación
        getServletContext().setAttribute("maquinas", maquinas);

        //Crear objeto parcelaDAO
        ParcelaDAO parcelaDAO = new ParcelaDAO();

        // Obtener la lista de parcelas
        ArrayList<Parcela> parcelas = parcelaDAO.getParcelas(conexion);

        // Almacenar la lista de parcelas en el contexto de la aplicación
        getServletContext().setAttribute("parcelas", parcelas);

        // Obtener el valor del botón
        String boton = request.getParameter("boton");

        // Verificar si el botón es "anadirUsuario"
        if (boton != null && boton.equals("anadirUsuario")) {
            // Obtener los datos del usuario del formulario
            String usuario = request.getParameter("usuario");
            String email = request.getParameter("email");
            String password = request.getParameter("password");
            int rol = Integer.parseInt(request.getParameter("rol"));

            // Crear un objeto AdministradorDAO para insertar el usuario
            AdministradorDAO administradorDAO = new AdministradorDAO();
            administradorDAO.insertarUsuario(conexion, usuario, email, password, rol);

            response.sendRedirect("panelAdministrador.jsp");
            return; // Evita que el código continúe ejecutándose

        } else if (boton != null && boton.equals("eliminarUsuario")) {
            int id = Integer.parseInt(request.getParameter("idUsuario"));

            // Crear un objeto AdministradorDAO para insertar el usuario
            AdministradorDAO administradorDAO = new AdministradorDAO();
            administradorDAO.eliminarUsuario(conexion, id);

            response.sendRedirect("panelAdministrador.jsp");
            return; // Evita que el código continúe ejecutándose
        }

        if (boton != null && boton.equals("anadirMaquina")) {
            String modelo = request.getParameter("modelo");
            String tipo = request.getParameter("tipo");
            int capacidad = Integer.parseInt(request.getParameter("capacidad"));
            int anho = Integer.parseInt(request.getParameter("anho"));
            boolean estado = Boolean.parseBoolean(request.getParameter("estado"));

            // Crear una instancia de la clase Maquina con los valores obtenidos
            Maquina nuevaMaquina = new Maquina(0, modelo, tipo, capacidad, anho, estado);

            maquinaDAO.insertarMaquina(conexion, nuevaMaquina);

        } else if (boton != null && boton.equals("eliminarMaquina")) {
            // Obtener el id de la maquina
            String id = request.getParameter("id");
            int idMaquina = Integer.parseInt(id); // Convertir a entero dado que getparameter devuelve string


            // Eliminar la parcela por catastro
            maquinaDAO.eliminarMaquina(conexion, idMaquina);

            // Redirigir de vuelta al panel
            response.sendRedirect("panelAdministrador.jsp");
            return; // Evita que el código continúe ejecutándose

        }

        if (boton != null && boton.equals("anadirParcela")) {
            // Obtener los datos del formulario
            String catastro = request.getParameter("catastro");
            int idUsuario = Integer.parseInt(request.getParameter("idUsuario"));
            int superficie = Integer.parseInt(request.getParameter("superficie"));

            Parcela parcela = new Parcela(catastro, idUsuario, superficie);

            // Añadir la parcela a la base de datos
            parcelaDAO.insertarParcela(conexion, parcela);

            // Redirigir de vuelta al panel
            response.sendRedirect("panelAdministrador.jsp");
            return; // Evita que el código continúe ejecutándose

        } else if (boton != null && boton.equals("eliminarParcela")) {
            // Obtener el catastro del formulario
            String catastro = request.getParameter("catastro");

            // Eliminar la parcela por catastro
            parcelaDAO.eliminarParcela(conexion, catastro);

            // Redirigir de vuelta al panel
            response.sendRedirect("panelAdministrador.jsp");
            return; // Evita que el código continúe ejecutándose
        }

        // Redirigir a la página JSP
        response.sendRedirect("panelAdministrador.jsp");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Servlet para gestionar usuarios del administrador";
    }
}
