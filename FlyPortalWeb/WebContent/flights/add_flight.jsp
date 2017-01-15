<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@page import="javax.xml.rpc.Service"%>
<%@page import="javax.xml.rpc.Call"%>
<%@page import="com.sun.xml.rpc.client.dii.webservice.WebService"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page import="java.util.*"%>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<link
	href="<c:url value="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css"/>"
	rel="stylesheet" />
<link
	href="<c:url value="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datetimepicker/4.17.43/css/bootstrap-datetimepicker.min.css"/>"
	rel="stylesheet" />

<link href="<c:url value="components/css/myCSS.css"/>" rel="stylesheet" />

<!-- JQuery -->
<script
	src="<c:url value="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.1.1/jquery.min.js"/>"></script>

<!-- Bootstrap -->
<script
	src="<c:url value="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"/>"></script>

<!-- Moment -->
<script
	src="<c:url value="https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.17.1/moment.min.js"/>"></script>

<!-- Bootstrap datetimepicker -->
<script
	src="<c:url value="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datetimepicker/4.17.43/js/bootstrap-datetimepicker.min.js"/>"></script>

<script
	src="http://cdnjs.cloudflare.com/ajax/libs/modernizr/2.8.2/modernizr.js"></script>

<script>
$(window).on('load', function() {
		$(".se-pre-con").fadeOut("slow");;
	});
</script>
<title>Add flight</title>
</head>

<body>
	<%
	
	%>
	<div class="se-pre-con"></div>
	<nav class="navbar navbar-default">
		<div class="container-fluid">
			<div class="navbar-header">
				<a class="navbar-brand">Fly Portal</a>
			</div>
			<ul class="nav navbar-nav">
				<li class="active"><a href="FlightTable">Flights table</a></li>
			</ul>
			<ul class="nav navbar-nav navbar-right">
				<%
					if(request.getSession().getAttribute("auth").equals("admin")||request.getSession().getAttribute("auth").equals("user")) 
																					out.println("<li><a href='../LogoutServlet'>logout</a></li>");
				%>
			</ul>
		</div>
	</nav>
	<div class="container">
		<div class="panel panel-default">
			<div class="panel-heading">
				<h3 class="panel-title">Add a flight</h3>
			</div>
			<div class="panel-body">
				<form action="../AddFlightServlet" method="post" id="addFlightForm">
					<div class="form-group">
						<label for="flight">Flight</label> <input type="text"
							class="form-control" id="flight" name="flight" required
							style="text-transform: uppercase" />
					</div>
					<div class="form-group">
						<label for="dep_airport">Departure airport</label> <select
							class="form-control" id="dep_airport" name="dep_airport" required>
							<option></option>
							<c:forEach items="${airportsList}" var="value">
								<option value="${value.getCity()} ${value.getName()}">${value.getCity()}
									- ${value.getName()}</option>
							</c:forEach>
						</select>
					</div>
					<div class="form-group">
						<label for="arr_airport">Arrival airport</label> <select
							class="form-control" id="arr_airport" name="arr_airport" required>
							<option></option>
							<c:forEach items="${airportsList}" var="value">
								<option value="${value.getCity()} ${value.getName()}">${value.getCity()}
									- ${value.getName()}</option>
							</c:forEach>
						</select>
					</div>
					<div class="form-group">
						<label for="datetimepicker1">Departure time</label>
						<div class='input-group date' id='datetimepicker1'>
							<input type='text' class="form-control" name="dep_time" /> <span
								class="input-group-addon"> <span
								class="glyphicon glyphicon-calendar"></span>
							</span>
						</div>
						<script type="text/javascript">
							$(function() {
								$('#datetimepicker1').datetimepicker({
									format : 'YYYY-MM-DD HH:mm:ss'
								});
							});
						</script>
					</div>
					<div class="form-group">
						<label for="company">Company</label> <select class="form-control"
							id="company" name="company">
							<c:forEach items="${airlinesList}" var="value">
								<option value="${value}">${value}</option>
							</c:forEach>
						</select>
					</div>
					<div class="form-group">
						<label for="state">State</label> <select class="form-control"
							id="state" name="state">
							<option>on time</option>
							<option>late</option>
							<option>cancelled</option>
							<option>rescheduled</option>
						</select>
					</div>
					<div class="form-group">
						<label for="free_seats">Free seats</label> <input
							class="form-control" type="number" value="0" id="free_seats"
							name="free_seats" />
					</div>
					<div class="form-group">
						<label for="free_seats">Seat's price &euro;</label> <input
							class="form-control" type="number" value="0" id="price"
							name="price" />
					</div>
					<button type="submit" class="btn btn-success">Submit</button>
					<button class="btn btn-danger" onclick="resetField()">Reset</button>
					<script>
						function resetField() {
							document.forms['addFlightForm'].reset();
						}
					</script>
					<script>
					$("#flight").keyup(function(){
						$(this).val($(this).toUpperCase());
					});
					</script>
				</form>
			</div>
		</div>
	</div>
</body>
</html>
