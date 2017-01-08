<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@page import="java.util.*"%>
<%@page import="flight.Flight"%>
<jsp:useBean id="showDataBean" class="flights.FlightsBean"
	scope="request" />
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%
	Double total = 0.0;
	String auth = (String) session.getAttribute("auth");
	TreeMap<String, Integer> cart = null;
if(session.getAttribute("cart") != null) {
	cart = (TreeMap<String, Integer>) session.getAttribute("cart");
	
	LinkedList<Flight> cartList = showDataBean.getFlights(cart.keySet());
	pageContext.setAttribute("cartList", cartList);
	
	for(Flight f : cartList)
		total += (f.getPrice() * cart.get(f.getFlight()));
}
%>
<link
	href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css"
	rel="stylesheet" type="text/css" />

<script src="http://code.jquery.com/jquery-latest.min.js"></script>

<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>

<script>
	$(document).ready(function() {
		$(".btn.btn-danger").click(function() {
			var f = $(this).attr('id');
			if (confirm("Are you sure you want to remove your booking from your shopping chart?"))
				removeCartCall(f);
		});
		function removeCartCall(f) {
			$.post("../CartServlet", {
				operation : "remove",
				flight : f
			}, function(data) {
				alert("Flight " + data);
				location.reload();
			});
		};
		
		$("#buyBtn").click(function() {
			var cart = "${sessionScope.cart}";
			alert("Your cart: " + cart);
			$.post("../BookingServlet", null, function(data) {
				alert(data);
				$("#cartTable > tbody").html("");
				$("#buyBtn").hide();
				if(data == "Congratulations your order has been confirmed!") {
					var ws = new WebSocket("ws://localhost:8080/FlyPortalWebWS/echoWS");
					ws.onopen = function(evt) { ws.send(data); };
				}
			});
		});
		
	});
		</script>
<title>Cart</title>
</head>
<body>
	<nav class="navbar navbar-default">
		<div class="navbar-header">
			<a class="navbar-brand" href="../login.html">Fly Portal</a>
		</div>
		<ul class="nav navbar-nav">
			<li class="active"><a href="../flights/flights.jsp">Flights
					table</a></li>
		</ul>
		<ul class="nav navbar-nav navbar-right">
			<%if (auth.equals("admin") || auth.equals("user"))
				out.println("<li class='bg-danger'><a href='../LogoutServlet'>logout</a></li>");
				%>
		</ul>
	</nav>
	<div class="container">
		<div class="panel panel-primary">
			<div class="panel-heading">
				<h3 class="panel-title">Cart</h3>
			</div>
			<div class="panel-body">
				<table class="table table-striped" id="cartTable">
					<thead>
						<tr>
							<th>Flight</th>
							<th>Departure airport</th>
							<th>Arrival airport</th>
							<th>Departure time</th>
							<th>Company</th>
							<th>State</th>
							<th>Qty</th>
							<th id="price">Price &euro;</th>
							<th></th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${cartList}" var="item">
							<c:set var="qty" value="${cart.get(item.getFlight())}" />
							<tr>
								<td>${item.getFlight()}</td>
								<td>${item.getDepAirport()}</td>
								<td>${item.getArrAirport()}</td>
								<td>${item.getDepTime()}</td>
								<td>${item.getCompany()}</td>
								<td>${item.getState()}</td>
								<td>${qty}</td>
								<td>${item.getPrice()*qty}</td>
								<td><button
										class="btn btn-danger<%if(auth != "user")out.println(" hidden"); %>"
										id="${item.getFlight()}">
										<span class='glyphicon glyphicon-remove'> </span>
									</button></td>

							</tr>
						</c:forEach>
						<tr>
							<td colspan="6"></td>
							<td class="bg-warning"><h4>Total</h4></td>
							<td class="bg-warning" colspan="2"><h4><%=total%></h4></td>
						</tr>
					</tbody>
				</table>
				<div class="row">
					<div class="col-md-4"></div>
					<div class="col-md-4 col-md-offset-4 text-right">
						<button type="button" class="btn btn-warning btn-lg <%out.println((session.getAttribute("cart")==null)?"hidden":"");%>"
							data-toggle="Buy!" id="buyBtn">
							<span class="glyphicon glyphicon-shopping-cart"></span> Buy
						</button>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>