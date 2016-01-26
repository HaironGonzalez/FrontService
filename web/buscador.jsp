<%-- 
    Document   : buscador
    Created on : 07-12-2015, 03:54:48 PM
    Author     : Rudolfaraya
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="Cliente.Cliente"%>

<!DOCTYPE html>
<%
    
    String palabraBuscada = null;
    
    if(request.getParameter("Palabra") != null)  {
        palabraBuscada = request.getParameter("Palabra");        
        //out.println(palabraBuscada);
    }
    
    Cliente cliente = new Cliente(palabraBuscada);
    
%>


<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title></title>
    </head>
    <body>
        <h1>Solicitud Enviada correctamente al Servidor!</h1>
    </body>
</html>
