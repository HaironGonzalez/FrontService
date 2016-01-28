<%--
    Document   : buscador
    Created on : 07-12-2015, 03:54:48 PM
    Author     : Rudolfaraya
--%>

<%@page import="java.util.Set"%>
<%@page import="org.json.simple.JSONObject"%>
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
    JSONObject Resultados = new JSONObject();
    Resultados =cliente.getJson();
    
    Set<String > keys =Resultados.keySet();
    
%>


<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title></title>
    </head>
    <body>
      //  <%
        //for(int i=0;Resultados.size()<3;i++){%>
        
        <a href="Pagina.jsp" id=3>  <%="ID: "+keys.toArray()[0]     %></a>
       <br><br>
       <label for="female"><%=Resultados.get(keys.toArray()[0] )%></label>
       <br><br>
        <%
        
      //  }
        %>
    
    
    
    </body>
</html>
