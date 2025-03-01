<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Panel Agricultor</title>
        <link rel="stylesheet" href="./css/style.css">
    </head>
    <body>
        <h1>Panel Agricultor</h1>

        <%-- Accedemos a los atributos de sesión --%>
        <%
            String id = (String) session.getAttribute("id");
            String usuario = (String) session.getAttribute("usuario");
            String email = (String) session.getAttribute("email");
            Integer rol = (Integer) session.getAttribute("rol");
        %>

        <%-- Verificamos si el usuario está autenticado y mostramos la tabla --%>
        <% if (id != null && usuario != null && email != null) { %>
        <table>
            <tr>
                <th>ID</th>
                <th>Usuario</th>
                <th>Email</th>
            </tr>
            <tr>
                <td><%= id %></td>
                <td><%= usuario %></td>
                <td><%= email %></td>
            </tr>
        </table>
        <% } %>

    </body>
</html>
