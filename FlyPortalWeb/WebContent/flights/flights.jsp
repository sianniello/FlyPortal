<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@page import="flights.FlightsBean"%>
<%@page import="flight.Flight"%>
<%@page import="java.util.*"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%String auth = (String) request.getSession().getAttribute("auth"); %>
<jsp:useBean id="showDataBean" class="flights.FlightsBean"
	scope="request" />
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<link
	href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css"
	rel="stylesheet" type="text/css" />
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<title>Flights table <%= auth %></title>
</head>
<body>
	<%
		LinkedList<Flight> list = new LinkedList<Flight>();
			list = showDataBean.getFlights();
			pageContext.setAttribute("list", list);
	%>
	<div class="container">
		<div class="panel panel-primary pt-2">
			<div class="panel-heading">
				<h3 class="panel-title">Panel title</h3>
			</div>
			<div class="panel-body">
				<table class="table table-striped">
					<thead>
						<tr>
							<th>Flight</th>
							<th>Departure airport</th>
							<th>Arrival airport</th>
							<th>Departure time</th>
							<th>Company</th>
							<th>State</th>
							<th>Free seats</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${list}" var="item">
							<tr>
								<td>${item.getFlight()}</td>
								<td>${item.getDepAirport()}</td>
								<td>${item.getArrAirport()}</td>
								<td>${item.getDepTime()}</td>
								<td>${item.getCompany()}</td>
								<td>${item.getState()}</td>
								<td>${item.getFreeSeats()}</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</div>
	</div>
</body>
</html>
