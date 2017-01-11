<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@page import="flights.DeleteFlightBeanRemote"%>
<%@page import="flight.Flight"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String auth = "";
	if (request.getSession() != null) 
auth = (String) request.getSession().getAttribute("auth");
	else request.getRequestDispatcher("login.html").forward(request, response);
%>
<jsp:useBean id="showDataBean" class="flights.FlightsBean"
	scope="request" />
<jsp:useBean id="delDataBean" class="flights.DeleteFlightBean"
	scope="request" />
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<link
	href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css"
	rel="stylesheet" type="text/css" />

<script src="http://code.jquery.com/jquery-latest.min.js"></script>

<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>


<script>
$(document).ready(function() {
    var a = document.querySelectorAll("input");
    for (i = 0; i < a.length; i++) a[i].setAttribute("size", a[i].getAttribute("value").length);
    $(".btn.btn-danger").click(function() {
        var a = $(this).attr("id");
        if (confirm("Are you sure you want to delete flight " + a + "?")) removeServletCall(a);
    });
    $(".btn.btn-success").click(function() {
        var a = $(this).attr("id");
        var b = $("#" + a + "mod").val();
        var c = $("#" + a + "dep_air").val();
        var d = $("#" + a + "arr_air").val();
        var e = $("#" + a + "dep_time").val();
        var f = $("#" + a + "comp").val();
        var g = $("#" + a + "state").val();
        var h = $("#" + a + "fseat").val();
        var i = $("#" + a + "prc").val();
        if (confirm("Confirm flight " + a + " changes?")) editServletCall(a, c, d, e, f, g, h, i, b);
    });
});

function removeServletCall(a) {
    $.post("../DeleteFlightServlet", {
        flight: a
    }, function(a) {
        alert(a);
        var b = new WebSocket("ws://localhost:8080/FlyPortalWebWS/FlyPortalWS");
        b.onopen = function(c) {
            b.send(a);
            b.close();
        };
        $("#flightsTable").load(window.location + " #flightsTable");
    });
}

function editServletCall(a, b, c, d, e, f, g, h, i) {
    $.post("../EditFlightServlet", {
        flight: a + "#" + b + "#" + c + "#" + d + "#" + e + "#" + f + "#" + g + "#" + h + "#" + i
    }, function(a) {
        var b = new WebSocket("ws://localhost:8080/FlyPortalWebWS/FlyPortalWS");
        b.onopen = function(c) {
            b.send(a);
            b.close();
        };
        location.reload();
    });
}
</script>

<title>Flights table <%=auth%></title>
</head>
<body>
	<%
		if(auth == null){
	        session.setAttribute("message", "Please Login");
	         response.sendRedirect(response.encodeRedirectURL("../errors" + "/" + "session_timeout.html"));
	    }
	%>
	<nav class="navbar navbar-default">
		<div class="container-fluid">
			<div class="navbar-header">
				<a class="navbar-brand" href="../login.html">Fly Portal</a>
			</div>
			<ul class="nav navbar-nav">
				<li class="active"><a href="add_flight.jsp"><span
						class="glyphicon glyphicon-plus" aria-hidden="true"></span> Add
						flight</a></li>
			</ul>
			<ul class="nav navbar-nav">
				<li class="active"><a href="../transactions/transactions.jsp"><span
						class="glyphicon glyphicon-piggy-bank" aria-hidden="true"></span>
						Transactions</a></li>
			</ul>
			<ul class="nav navbar-nav navbar-right">
				<li class="bg-danger"><a href='../LogoutServlet'>logout</a></li>
			</ul>
		</div>
	</nav>
	<div class="container col-lg-12">
		<div class="panel panel-warning pt-4">
			<div class="panel-heading">
				<h3 class="panel-title">Flight Table</h3>
			</div>
			<div class="panel-body">
				<table class="table table-striped" id="flightsTable">
					<thead>
						<tr>
							<th>Flight</th>
							<th>Departure airport</th>
							<th>Arrival airport</th>
							<th>Departure time</th>
							<th>Company</th>
							<th>State</th>
							<th>Free seats</th>
							<th>Price per seat</th>
							<th></th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${flights}" var="item">
							<tr>
								<td><input id="${item.getFlight()}mod" type="text"
									value="${item.getFlight()}" /></td>

								<td><input id="${item.getFlight()}dep_air" type="text"
									value="${item.getDepAirport()}" /></td>

								<td><input id="${item.getFlight()}arr_air" type="text"
									value="${item.getArrAirport()}" /></td>

								<td><input id="${item.getFlight()}dep_time" type="text"
									value="${item.getDepTime()}" /></td>

								<td><input id="${item.getFlight()}comp" type="text"
									value="${item.getCompany()}" /></td>

								<td><input id="${item.getFlight()}state" type="text"
									value="${item.getState()}" /></td>

								<td><input id="${item.getFlight()}fseat" type="text"
									value="${item.getFreeSeats()}" /></td>

								<td><input id="${item.getFlight()}prc" type="text"
									value="${item.getPrice()}" />&euro;</td>

								<td>
									<div class="btn-group" role="group">
										<button class='btn btn-success' id="${item.getFlight()}">
											<span class='glyphicon glyphicon-ok'></span>
										</button>
										<button class='btn btn-danger' id="${item.getFlight()}">
											<span class='glyphicon glyphicon-remove'></span>
										</button>
									</div>
								</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</div>
	</div>
</body>
</html>
