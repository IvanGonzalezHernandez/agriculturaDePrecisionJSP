<%@page import="modelos.Trabajo"%>
<%@page import="modelos.Parcela"%>
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    int rolComprobacion = (int) session.getAttribute("rol");

    if (rolComprobacion == 2) {
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Panel Agricultor</title>
        <link rel="stylesheet" href="./css/style.css">
    </head>
    <body>
        <h1>Panel Agricultor</h1>

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



        <h2>Parcelas del Agricultor</h2>

        <table>
            <thead>
                <tr>
                    <th>Catastro</th>
                    <th>Superficie</th>
                </tr>
            </thead>
            <tbody>
                <%
                    ArrayList<Parcela> parcelas = (ArrayList<Parcela>) application.getAttribute("parcelas");
                    for (Parcela parcela : parcelas) {
                %>
                <tr>
                    <td><%= parcela.getCatastro()%></td>
                    <td><%= parcela.getSuperficie()%></td>
                </tr>
                <% }%>
            </tbody>
        </table>


        <h2>Añadir una nueva Parcela</h2>

        <form action="agricultor" method="POST">

            <label for="catastro">Catastro:</label>
            <input type="text" id="catastro" name="catastro" required/>


            <label for="superficie">Superficie (m²):</label>
            <input type="number" id="superficie" name="superficie" required/>


            <input type="hidden" name="idUsuario" value="<%= session.getAttribute("id")%>" />


            <button name="boton" value="anadirParcela" type="submit">Añadir Parcela</button>

        </form>

        <!-- Formulario para eliminar una parcela -->
        <form action="administrador" method="POST">
            <label for="catastro">Catastro de la parcela:</label>
            <input type="text" id="catastro" name="catastro" required><br><br>

            <button name="boton" value="eliminarParcela" type="submit">Eliminar Parcela</button>
        </form>


        <h2>Solicitar un Trabajo:</h2>

        <form action="agricultor" method="POST">
            <!-- ID Agricultor como campo oculto -->
            <input type="hidden" name="idAgricultor" value="<%= session.getAttribute("id")%>">

            <label for="tipo">Tipo de Trabajo:</label>
            <select name="tipo" required>
                <option value="arado">Arado</option>
                <option value="siembra">Siembra</option>
                <option value="riego">Riego</option>
                <option value="cosecha">Cosecha</option>
            </select>

            <label for="idParcela">ID Parcela:</label>
            <input type="number" name="idParcela" required>

            <button type="submit" name="boton" value="crearTrabajo">Crear Trabajo</button>
        </form>





        <%
            // Obtener la lista de trabajos sin máquina del atributo de solicitud
            ArrayList<Trabajo> trabajosFinalizadosAgricultor = (ArrayList<Trabajo>) application.getAttribute("trabajosFinalizadosAgricultor");
        %>

        <h2>Trabajos Finalizados para el agricultor:</h2>

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
                    for (Trabajo trabajo : trabajosFinalizadosAgricultor) {
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
