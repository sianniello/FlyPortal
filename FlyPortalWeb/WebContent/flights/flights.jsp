<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@page import="flights.FlightsBean"%>
<%@page import="flights.FlightBeanLocal"%>
<%@page import="flights.DeleteFlightBeanLocal"%>
<%@page import="flight.Flight"%>
<%@page import="java.util.*"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String auth = (String) request.getSession().getAttribute("auth");
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<link
	href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css"
	rel="stylesheet" type="text/css" />

<script src="http://code.jquery.com/jquery-latest.min.js"></script>

<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>

<script>
	$(document)
			.ready(
					function() {
						$(".btn.btn-warning")
								.click(
										function() {
											var f = $(this).attr('id');
											if (confirm("Are you sure you want to book a seat on flight "
													+ f + "?"))
												CartServletCall(f);
										});
					});
	function CartServletCall(f) {
		$.post("../CartServlet", {
			operation : "add",
			flight : f
		}, function(data) {
			alert(data);
			$("#flightsTable").load(window.location + " #flightsTable");
		});
	};
</script>

<title>Flights table <%=auth%></title>
</head>
<body>
	<jsp:useBean id="showFlightsBean" class="flights.FlightsBean"
		scope="request" />
	<jsp:useBean id="delFlightsBean" class="flights.DeleteFlightBean"
		scope="request" />
	<%
		LinkedList<Flight> list = new LinkedList<Flight>();
		showFlightsBean = new FlightsBean();
		list = showFlightsBean.getFlights();
		pageContext.setAttribute("list", list);
	%>
	<nav class="navbar navbar-default">
		<div class="container-fluid">
			<div class="navbar-header">
				<a class="navbar-brand" href="../login.html">Fly Portal</a>
			</div>
			<ul class="nav navbar-nav">
				<li class="active"><a href="flights.jsp">Flights table</a></li>

				<%
					if (request.getSession().getAttribute("auth").equals("admin")) {
								out.println("<li><a href='add_flight.jsp'>Add flight</a></li>");
								out.println("<li><a href='#'>Transactions</a></li>");
							}
				%>

			</ul>
			<ul class="nav navbar-nav navbar-right">
				<%
					if (auth.equals("user"))
								out.println("<li><a href='../cart/cart.jsp'><span class='glyphicon glyphicon-shopping-cart' aria-hidden='true'>Shopping cart</span></a></li>");
							if (auth.equals("admin") || auth.equals("user"))
								out.println("<li class='bg-danger'><a href='../LogoutServlet'>logout</a></li>");
				%>
			</ul>
		</div>
	</nav>
	<div class="container">
		<div class="panel panel-primary pt-2">
			<div class="panel-heading">
				<h3 class="panel-title">Flight Table</h3>
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
							<th>Price &euro;</th>
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
								<td>${item.getPrice()}</td>
								<td><button
										class="btn btn-warning<%if(auth != "user")out.println(" hidden"); %>"
										id="${item.getFlight()}">
										<span class='glyphicon glyphicon-shopping-cart'> </span>
									</button></td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</div>
	</div>
</body>
</html>