<%-- 
    Document   : panelAdministrador
    Created on : 1 mar 2025, 9:23:47
    Author     : ivang
--%>

<%@page import="modelos.Trabajo"%>
<%@page import="modelos.Maquina"%>
<%@page import="modelos.Parcela"%>
<%@page import="java.util.ArrayList"%>
<%@page import="modelos.Usuario"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link rel="stylesheet" href="./css/style.css">
    </head>
    <body>
        <h1>Panel Administrador</h1>

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




        <h2>Usuarios:</h2>

        <!-- Tabla para mostrar los usuarios -->
        <table>
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Nombre</th>
                    <th>Email</th>
                    <th>Rol</th>
                </tr>
            </thead>
            <tbody>
                <%
                    // Recuperar la lista de usuarios desde el contexto de la aplicación
                    ArrayList<Usuario> usuarios = (ArrayList<Usuario>) application.getAttribute("usuarios");

                    // Verificar que la lista no sea null antes de iterar
                    if (usuarios != null) {
                        // Iterar sobre la lista de usuarios
                        for (Usuario user : usuarios) {
                %>
                <tr>
                    <td><%= user.getId()%></td>
                    <td><%= user.getUsuario()%></td>
                    <td><%= user.getEmail()%></td>
                    <td>
                        <%
                            // Mostrar el rol basado en el valor
                            if (user.getRol() == 1) {
                                out.print("Administrador");
                            } else if (user.getRol() == 2) {
                                out.print("Agricultor");
                            } else if (user.getRol() == 3) {
                                out.print("Maquinista");
                            }
                        %>
                    </td>
                </tr>
                <%
                        }
                    }
                %>
            </tbody>
        </table>


        <!-- Formulario para agregar un usuario -->
        <form action="administrador" method="POST">
            <label for="usuario">Usuario:</label>
            <input type="text" id="usuario" name="usuario" required><br><br>

            <label for="email">Email:</label>
            <input type="email" id="email" name="email" required><br><br>

            <label for="password">Contraseña:</label>
            <input type="password" id="password" name="password" required><br><br>

            <label for="rol">Rol:</label>
            <select id="rol" name="rol" required>
                <option value="1">Administrador</option>
                <option value="2">Agricultor</option>
                <option value="3">Maquinista</option>
            </select><br><br>

            <button name="boton" value="anadirUsuario" type="submit">Añadir Usuario</button>
        </form>


        <!-- Formulario para eliminar un usuario -->
        <form action="administrador" method="POST">
            <label for="idUsuario">ID del Usuario a Eliminar:</label>
            <input type="number" id="idUsuario" name="idUsuario" required><br><br>

            <button name="boton" value="eliminarUsuario" type="submit">Eliminar Usuario</button>
        </form>












        <h2>Máquinas:</h2>

        <%
            // Recuperamos la lista de máquinas del request
            ArrayList<Maquina> maquinas = (ArrayList<Maquina>) application.getAttribute("maquinas");
        %>
        <table>
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Modelo</th>
                    <th>Tipo</th>
                    <th>Capacidad</th>
                    <th>Año</th>
                    <th>Estado</th>
                </tr>
            </thead>
            <tbody>
                <%
                    // Iteramos sobre la lista de máquinas y mostramos sus datos
                    for (Maquina maquina : maquinas) {
                %>
                <tr>
                    <td><%= maquina.getId()%></td>
                    <td><%= maquina.getModelo()%></td>
                    <td><%= maquina.getTipo()%></td>
                    <td><%= maquina.getCapacidad()%></td>
                    <td><%= maquina.getAnho()%></td>
                    <td><%= maquina.isEstado() ? "Activo" : "Inactivo"%></td>
                </tr>
                <%
                    }
                %>
            </tbody>
        </table>










        <form action="administrador" method="POST">
            <label for="modelo">Modelo:</label>
            <input type="text" id="modelo" name="modelo" required><br><br>

            <label for="tipo">Tipo:</label>
            <select id="tipo" name="tipo">
                <option value="arado">Arado</option>
                <option value="siembra">Siembra</option>
                <option value="riego">Riego</option>
                <option value="cosecha">Cosecha</option>
            </select><br><br>

            <label for="capacidad">Capacidad:</label>
            <input type="number" id="capacidad" name="capacidad" required><br><br>

            <label for="anho">Año:</label>
            <input type="number" id="anho" name="anho" required><br><br>

            <label for="estado">Estado:</label>
            <select id="estado" name="estado">
                <option value="true">Activo</option>
                <option value="false">Inactivo</option>
            </select><br><br>

            <button type="submit" name="boton" value="anadirMaquina">Añadir Máquina</button>
        </form>


        <form action="administrador" method="POST">
            <label for="id">ID de la Máquina a eliminar:</label>
            <input type="number" id="id" name="id" required><br><br>
            <button type="submit" name="boton" value="eliminarMaquina">Eliminar Máquina</button>
        </form>














































        <%
            ArrayList<Parcela> parcelas = (ArrayList<Parcela>) application.getAttribute("parcelas");
        %>
        <h2>Listado de Parcelas:</h2>
        <table>
            <tr>
                <th>Catastro</th>
                <th>ID Agricultor</th>
                <th>Superficie</th>
            </tr>

            <% for (Parcela parcela : parcelas) {%>
            <tr>
                <td><%= parcela.getCatastro()%></td>
                <td><%= parcela.getIdUsuario()%></td>
                <td><%= parcela.getSuperficie()%></td>
            </tr>
            <% }%>
        </table>


        <!-- Formulario para añadir una parcela -->
        <form action="administrador" method="POST">
            <label for="catastro">Catastro de la parcela:</label>
            <input type="text" id="catastro" name="catastro" required><br><br>

            <label for="idUsuario">ID del Agricultor:</label>
            <input type="number" id="idUsuario" name="idUsuario" required><br><br>

            <label for="superficie">Superficie de la parcela:</label>
            <input type="number" id="superficie" name="superficie" required><br><br>

            <button name="boton" value="anadirParcela" type="submit">Añadir Parcela</button>
        </form>



        <!-- Formulario para eliminar una parcela -->
        <form action="administrador" method="POST">
            <label for="catastro">Catastro de la parcela:</label>
            <input type="text" id="catastro" name="catastro" required><br><br>

            <button name="boton" value="eliminarParcela" type="submit">Eliminar Parcela</button>
        </form>








        <%
            // Obtener la lista de trabajos sin máquina del atributo de solicitud
            ArrayList<Trabajo> trabajosSinMaquina = (ArrayList<Trabajo>) application.getAttribute("trabajosSinMaquina");
        %>
        <h2>Trabajos Sin Máquina Asignada:</h2>

        <%-- Comprobamos si hay trabajos sin máquina --%>

        <table>
            <thead>
                <tr>
                    <th>ID Trabajo</th>
                    <th>ID Agricultor</th>
                    <th>Tipo</th>
                    <th>Fecha Inicio</th>
                    <th>Fecha Fin</th>
                    <th>ID Maquinista</th>
                    <th>ID Parcela</th>
                    <th>Estado</th>
                </tr>
            </thead>
            <tbody>
                <%
                    // Iteramos sobre la lista de trabajos y mostramos los datos en las filas de la tabla
                    for (Trabajo trabajo : trabajosSinMaquina) {
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



        <h2>Crear Nuevo Trabajo:</h2>
        <form action="administrador" method="POST">
            <label for="idAgricultor">ID Agricultor:</label>
            <input type="number" name="idAgricultor" required>

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





        <h2>Asignar Máquina a Trabajo:</h2>

        <form action="administrador" method="POST">
            <label for="idTrabajo">Seleccionar Trabajo sin Máquina:</label>
            <select name="idTrabajo" id="idTrabajo" required>
                <%
                    for (Trabajo trabajo : trabajosSinMaquina) {
                %>
                <option value="<%= trabajo.getIdTrabajo()%>">
                    Trabajo ID <%= trabajo.getIdTrabajo()%> - Tipo: <%= trabajo.getTipo()%>
                </option>
                <% }%>
            </select>

            <label for="idMaquina">ID de la Máquina a Asignar:</label>
            <input type="number" name="idMaquina" id="idMaquina" required>

            <button type="submit" name="boton" value="asignarMaquina">Asignar Máquina</button>
        </form>














    </body>
</html>
