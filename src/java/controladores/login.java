/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controladores;

import DAO.ConectorBD;
import DAO.UsuarioDAO;
import java.io.IOException;
import java.sql.Connection;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import modelos.Usuario;

/**
 *
 * @author ivang
 */
public class login extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        // Obtener el correo y la contraseña desde el formulario
        String email = request.getParameter("email");
        String contraseña = request.getParameter("password");

        // Crear la conexión a la base de datos
        ConectorBD conector = new ConectorBD("localhost", "agriculturadeprecisionjsp", "root", "");
        Connection conexion = conector.getConexion();

        // Crear un objeto UsuarioDAO y buscar el usuario en la base de datos
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        Usuario usuario = usuarioDAO.crearUsuario(conexion, email, contraseña);

        // Recuperar la sesión actual o crear una nueva si no existe
        HttpSession session = request.getSession(true);

        // Verificar si el usuario existe
        if (usuario != null) {
            // Si el usuario es válido, guardamos su información en la sesión
            session.setAttribute("id", usuario.getId());
            session.setAttribute("usuario", usuario.getUsuario());
            session.setAttribute("email", usuario.getEmail());
            session.setAttribute("rol", usuario.getRol());

            switch (usuario.getRol()) {
                case 1 -> response.sendRedirect("panelAdministrador.jsp");
                case 2 -> response.sendRedirect("panelAgricultor.jsp");
                case 3 -> response.sendRedirect("panelMaquinista.jsp");
            }

        } else {
            // Si no es válido, redirigir al login con un mensaje de error
            response.sendRedirect("index.jsp?error=true");
        }

    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}

