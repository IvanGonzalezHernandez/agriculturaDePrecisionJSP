<%-- 
    Document   : index
    Created on : 1 mar 2025, 9:06:42
    Author     : ivang
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link rel="stylesheet" href="./css/index.css"/>
    </head>
    <body>


        <form action="login" method="POST">
            <img src="./logo/agrarium_logo.png" alt="alt"/>
            <label for="email">Correo electrónico:</label>
            <input type="text" id="email" name="email" required>
            <br><br>

            <label for="password">Contraseña:</label>
            <input type="password" id="password" name="password" required>
            <br><br>

            <button name="botonEntrar" type="submit">Iniciar sesión</button>

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
        </form>


    </body>
</html>