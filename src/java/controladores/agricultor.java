/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controladores;

import DAO.ConectorBD;
import DAO.ParcelaDAO;
import DAO.TrabajoDAO;
import DAO.UsuarioDAO;
import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import modelos.Parcela;

/**
 *
 * @author ivang
 */
public class agricultor extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        // Recuperar la sesión actual o crear una nueva si no existe
        HttpSession session = request.getSession(true);

        // Crear la conexión a la base de datos
        ConectorBD conector = new ConectorBD("localhost", "agriculturadeprecisionjsp", "root", "");
        Connection conexion = conector.getConexion();

        //Recuperamos de la sesion el id del agricultor
        int id = (Integer) session.getAttribute("id");

        //Crear objeto parcelaDAO
        ParcelaDAO parcelaDAO = new ParcelaDAO();

        // Obtener la lista de parcelas
        ArrayList<Parcela> parcelas = parcelaDAO.getParcelasPorAgricultor(conexion, id);

        // Almacenar la lista de parcelas en el contexto de la aplicación
        getServletContext().setAttribute("parcelas", parcelas);

        // Obtener el valor del botón
        String boton = request.getParameter("boton");

        if (boton != null && boton.equals("anadirParcela")) {
            // Obtener los datos del formulario
            String catastro = request.getParameter("catastro");
            int idUsuario = Integer.parseInt(request.getParameter("idUsuario"));
            int superficie = Integer.parseInt(request.getParameter("superficie"));

            Parcela parcela = new Parcela(catastro, idUsuario, superficie);

            // Añadir la parcela a la base de datos
            parcelaDAO.insertarParcela(conexion, parcela);

            // Redirigir de vuelta al panel
            response.sendRedirect("panelAgricultor.jsp");
            return; // Evita que el código continúe ejecutándose
        } else if (boton != null && boton.equals("eliminarParcela")) {
            // Obtener el catastro del formulario
            String catastro = request.getParameter("catastro");

            // Eliminar la parcela por catastro
            parcelaDAO.eliminarParcela(conexion, catastro);

            // Redirigir de vuelta al panel
            response.sendRedirect("panelAgricultor.jsp");
            return; // Evita que el código continúe ejecutándose
        }

        if (boton != null && boton.equals("crearTrabajo")) {
            int idAgricultor = Integer.parseInt(request.getParameter("idAgricultor"));
            String tipo = request.getParameter("tipo");
            int idParcela = Integer.parseInt(request.getParameter("idParcela"));
            
            TrabajoDAO trabajoDAO = new TrabajoDAO();

            trabajoDAO.crearTrabajo(conexion, idAgricultor, tipo, idParcela);

            // Redirigir de vuelta al panel
            response.sendRedirect("panelAgricultor.jsp");
            return; // Evita que el código continúe ejecutándose
        }

        // Redirigir a la página JSP
        response.sendRedirect("panelAgricultor.jsp");

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
