<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@page import="flights.FlightsBean"%>
<%@page import="flights.DeleteFlightBeanLocal"%>
<%@page import="flight.Flight"%>
<%@page import="java.util.*"%>
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
		var input = document.querySelectorAll('input');
		for(i=0; i<input.length; i++){
		    input[i].setAttribute('size',input[i].getAttribute('value').length);
		}
		$(".btn.btn-danger").click(function() {
			var f = $(this).attr('id');
			if (confirm("Are you sure you want to delete flight " + f + "?"))
				removeServletCall(f);
		});
		$(".btn.btn-success").click(function() {
			var f = $(this).attr('id');
			var fm = $("#" + f + "mod").val();
			var da = $("#" + f + "dep_air").val();
			var aa = $("#" + f + "arr_air").val();
			var dt = $("#" + f + "dep_time").val();
			var co = $("#" + f + "comp").val();
			var st = $("#" + f + "state").val();
			var fs = $("#" + f + "fseat").val();
			var pr = $("#" + f + "prc").val();
			if (confirm("Confirm flight " + f + " changes?")) {
				editServletCall(f, da, aa, dt, co, st, fs, pr, fm);
			}
		});
	});
	function removeServletCall(f) {
		$.post("../DeleteFlightServlet", {
			flight : f
		}, function(data) {
			alert(data);
			$("#flightsTable").load(window.location + " #flightsTable");
		});
	};
	function editServletCall(f, da, aa, dt, co, st, fs, pr, fm) {
		$.post("../EditFlightServlet", {
			flight : f + "#" + da + "#" + aa + "#" + dt + "#" + co + "#" + st
					+ "#" + fs + "#" + pr + "#" + fm
		}, function(data) {
			alert(data);
			location.reload();
		});
	};
</script>

<title>Flights table <%=auth%></title>
</head>
<body>
	<%
	if(auth == null){
        session.setAttribute("message", "Please Login");
         response.sendRedirect(response.encodeRedirectURL("../errors" + "/" + "session_timeout.html"));
    }
	
		LinkedList<Flight> list = new LinkedList<Flight>();
		list = showDataBean.getFlights();
		pageContext.setAttribute("list", list);
	%>
	<nav class="navbar navbar-default">
		<div class="container-fluid">
			<div class="navbar-header">
				<a class="navbar-brand" href="../login.html">Fly Portal</a>
			</div>
			<ul class="nav navbar-nav">
				<li class="active"><a href="add_flight.jsp"><span
						class="glyphicon glyphicon-plane" aria-hidden="true"></span> Add
						flight</a></li>
			</ul>
			<ul class="nav navbar-nav navbar-right">
				<li class="bg-danger"><a href='../LogoutServlet'>logout</a></li>
			</ul>
		</div>
	</nav>
	<div class="container col-lg-12">
		<div class="panel panel-primary pt-2">
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
						<c:forEach items="${list}" var="item">
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
