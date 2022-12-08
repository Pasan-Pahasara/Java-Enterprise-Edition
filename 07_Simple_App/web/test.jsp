<%@ page import="java.util.ArrayList" %><%--
  Created by IntelliJ IDEA.
  User: ShEnUx
  Date: 12/3/2022
  Time: 11:25 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Testing jsp</title>
</head>
<body>
<%--Scriplet--%>
<% %>
<%
    System.out.println("Hello there");
    String name="Institute of Software Engineering";
    int age=16;
    ArrayList allColors= new ArrayList();
%>

<h1>Hello there</h1>
<h2><%=name%></h2>
<%--Expression--%>
<%=name%>

<%--Declaration--%>
<%!String address="Galle";%>
</body>
</html>
