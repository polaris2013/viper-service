<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%
   response.setHeader("progma","no-cache");  
   response.setHeader("Cache-Control","no-cache");  
   response.setDateHeader("Expires",0);  

   String graphString=(String)session.getAttribute("xmlGraph");
   session.invalidate();
   
   
   
   out.print(graphString);

%>

