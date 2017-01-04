<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title></title>
</head>
<body>
	<p>login success!</p>
	<%
		if (request.getSession().getAttribute("auth") == "admin")
			out.println("<a href='flights/admin_flights.jsp'>Flights table</a>");
		else
			out.println("<a href='flights/flights.jsp'>Flights table</a>");
	%>
</body>
</html>