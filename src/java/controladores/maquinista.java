
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controladores;

import DAO.ConectorBD;
import DAO.TrabajoDAO;
import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import modelos.Trabajo;

/**
 *
 * @author ivang
 */
public class maquinista extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
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

        TrabajoDAO trabajoDAO = new TrabajoDAO();

        // Obtener la lista de trabajos sin maquinas
        ArrayList<Trabajo> trabajosSinFechaFin = trabajoDAO.obtenerTrabajosSinFechaFin(conexion);

        // Almacenar la lista de parcelas en el contexto de la aplicación
        getServletContext().setAttribute("trabajosSinFechaFin", trabajosSinFechaFin);


        // Obtener la lista de trabajos sin maquinas
        ArrayList<Trabajo> trabajosFinalizadosMaquinista = trabajoDAO.obtenerTrabajosFinalizadosMaquinista(conexion, id);

        // Almacenar la lista de parcelas en el contexto de la aplicación
        getServletContext().setAttribute("trabajosFinalizadosMaquinista", trabajosFinalizadosMaquinista);

        String boton = request.getParameter("boton");

        if (boton != null && boton.equals("comenzarTrabajo")) {
            int idTrabajo = Integer.parseInt(request.getParameter("idTrabajo"));
            trabajoDAO.comenzarTrabajo(conexion, idTrabajo, id);
            response.sendRedirect("maquinista");
            return;
        } else if (boton != null && boton.equals("finalizarTrabajo")) {
            int idTrabajo = Integer.parseInt(request.getParameter("idTrabajo"));
            String fechaStr = request.getParameter("fechaFin");

            java.sql.Date fechaFin = java.sql.Date.valueOf(fechaStr);
            trabajoDAO.finalizarTrabajo(conexion, idTrabajo, fechaFin);

            response.sendRedirect("maquinista");
            return;
        }

        // Redirigir a la página JSP
        response.sendRedirect("panelMaquinista.jsp");
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
