<%-- 
    Document   : panelMaquinista
    Created on : 1 mar 2025, 9:24:09
    Author     : ivang
--%>

<%@page import="modelos.Trabajo"%>
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    int rolComprobacion = (int) session.getAttribute("rol");

    if (rolComprobacion == 3) {
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link rel="stylesheet" href="./css/style.css">
    </head>
    <body>
        <h1>Panel Maquinista</h1>

        <h2>Tu usuario:</h2>

        <%-- Accedemos a los atributos de sesión --%>
        <%
            int id = (Integer) session.getAttribute("id");
            String usuario = (String) session.getAttribute("usuario");
            String email = (String) session.getAttribute("email");
            Integer rol = (Integer) session.getAttribute("rol");
        %>

        <%-- Verificamos si el usuario está autenticado y mostramos la tabla --%>
        <% if (email != null) {%>
        <table>
            <tr>
                <th>ID</th>
                <th>Usuario</th>
                <th>Email</th>
            </tr>
            <tr>
                <td><%= id%></td>
                <td><%= usuario%></td>
                <td><%= email%></td>
            </tr>
        </table>
        <% }%>







        <h2>Trabajos en Curso (Con Máquina Asignada y Sin Fecha de Finalización)</h2>

        <%
            // Obtener la lista de trabajos en curso desde el contexto de la aplicación
            ArrayList<Trabajo> trabajosEnCurso = (ArrayList<Trabajo>) application.getAttribute("trabajosSinFechaFin");
        %>

        <table>
            <thead>
                <tr>
                    <th>ID Trabajo</th>
                    <th>ID Agricultor</th>
                    <th>Tipo</th>
                    <th>Fecha Inicio</th>
                    <th>Fecha Fin</th>
                    <th>ID Máquina</th>
                    <th>ID Maquinista</th>
                    <th>ID Parcela</th>
                    <th>Estado</th>
                </tr>
            </thead>
            <tbody>
                <% for (Trabajo trabajo : trabajosEnCurso) {%>
                <tr>
                    <td><%= trabajo.getIdTrabajo()%></td>
                    <td><%= trabajo.getIdAgricultor()%></td>
                    <td><%= trabajo.getTipo()%></td>
                    <td><%= trabajo.getFechaInicio()%></td>
                    <td><%= trabajo.getFechaFin()%></td>
                    <td><%= trabajo.getIdMaquina()%></td>
                    <td><%= trabajo.getIdMaquinista()%></td>
                    <td><%= trabajo.getIdParcela()%></td>
                    <td><%= trabajo.getEstado()%></td>
                </tr>
                <% } %>
            </tbody>
        </table>


        <h2>Gestionar Trabajos:</h2>

        <!-- Formulario para comenzar trabajo -->
        <form action="maquinista" method="POST">
            <label for="idTrabajo">Seleccionar Trabajo:</label>
            <select name="idTrabajo" required>
                <option value="">-- Seleccionar --</option>
                <% if (trabajosEnCurso != null) {
                        for (Trabajo trabajo : trabajosEnCurso) {%>
                <option value="<%= trabajo.getIdTrabajo()%>">
                    <%= "ID: " + trabajo.getIdTrabajo() + " | " + trabajo.getTipo() + " - " + trabajo.getEstado()%>
                </option>
                <% }
                    } %>
            </select>
            <br><br>
            <button type="submit" name="boton" value="comenzarTrabajo">Comenzar Trabajo</button>
        </form>

        <br>

        <!-- Formulario para finalizar trabajo -->
        <form action="maquinista" method="POST">
            <label for="idTrabajo">Seleccionar Trabajo:</label>
            <select name="idTrabajo" required>
                <option value="">-- Seleccionar --</option>
                <% if (trabajosEnCurso != null) {
                        for (Trabajo trabajo : trabajosEnCurso) {%>
                <option value="<%= trabajo.getIdTrabajo()%>">
                    <%= "ID: " + trabajo.getIdTrabajo() + " | " + trabajo.getTipo() + " - " + trabajo.getEstado()%>
                </option>
                <% }
                    }%>
            </select>
            <br><br>
            <label for="fechaFin">Seleccionar Fecha de Finalización:</label>
            <input type="date" name="fechaFin" required>
            <br><br>
            <button type="submit" name="boton" value="finalizarTrabajo">Finalizar Trabajo</button>
        </form>





        <%
            // Obtener la lista de trabajos sin máquina del atributo de solicitud
            ArrayList<Trabajo> trabajosFinalizadosMaquinista = (ArrayList<Trabajo>) application.getAttribute("trabajosFinalizadosMaquinista");
        %>

        <h2>Trabajos Finalizados para el Maquinista:</h2>

        <%-- Comprobamos si hay trabajos finalizdos --%>

        <table>
            <thead>
                <tr>
                    <th>ID Trabajo</th>
                    <th>ID Agricultor</th>
                    <th>Tipo</th>
                    <th>Fecha Inicio</th>
                    <th>Fecha Fin</th>
                    <th>ID Maquinista</th>
                    <th>Catastro Parcela</th>
                    <th>Estado</th>
                </tr>
            </thead>
            <tbody>
                <%
                    // Iteramos sobre la lista de trabajos y mostramos los datos en las filas de la tabla
                    for (Trabajo trabajo : trabajosFinalizadosMaquinista) {
                %>
                <tr>
                    <td><%= trabajo.getIdTrabajo()%></td>
                    <td><%= trabajo.getIdAgricultor()%></td>
                    <td><%= trabajo.getTipo()%></td>
                    <td><%= trabajo.getFechaInicio()%></td>
                    <td><%= trabajo.getFechaFin()%></td>
                    <td><%= trabajo.getIdMaquinista()%></td>
                    <td><%= trabajo.getIdParcela()%></td>
                    <td><%= trabajo.getEstado()%></td>
                </tr>
                <%
                    }
                %>
            </tbody>
        </table>


















        <form action="login"> 
            <button type="submit" name="boton" value="cerrarSesion" style="background-color: black;">Cerrar sesión</button>
        </form>

        <%
            } else {
                response.sendRedirect("index.jsp?error=true"); // Redirigir al index
            }
        %>


    </body>
</html>
