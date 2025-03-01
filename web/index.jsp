<%-- 
    Document   : index
    Created on : 1 mar 2025, 9:06:42
    Author     : ivang
--%>
<%
    //Invalido la sesión
    session.invalidate();
%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Login</h1>

        <form action="login" method="POST">
            <label for="email">Correo electrónico:</label>
            <input type="text" id="email" name="email" required>
            <br><br>

            <label for="password">Contraseña:</label>
            <input type="password" id="password" name="password" required>
            <br><br>

            <button name="botonEntrar" type="submit">Iniciar sesión</button>
        </form>

        <%
            String error = request.getParameter("error");
            if ("true".equals(error)) {
        %>
        <div style="color: red; font-weight: bold;">
            <p>¡Error de autenticación! Verifica tus credenciales.</p>
        </div>
        <%
            }
        %>

    </body>
</html>