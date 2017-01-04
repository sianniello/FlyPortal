<?xml version="1.0" encoding="ISO-8859-1" ?>

<jsp:useBean id="transactionBean" class="transaction.TransactionBean"
	scope="request" />
<%@page import="java.util.*"%>
<%@page import="transaction.*"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%
	String auth = "";
		if (request.getSession() != null)
	auth = (String) request.getSession().getAttribute("auth");
		else
	request.getRequestDispatcher("login.html").forward(request,
	response);
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
	$(document).ready(function() {
		$(".clickable-row").click(function() {
			var oid = this.id;
			$.post("../OrderServlet", {
				order : oid
			}, function(data) {
				$('#ordersTable tbody').html("");
				var order = data.split("!");
				for(i = 0; i < order.length - 1; i++) {
					var x = order[i].split("#");
					$('#ordersTable tbody').append('<tr><td>'+x[1]+'</td><td>'+x[2]+'</td><td>'+x[3]+'</td></tr>');
				}
			});
			$('#myModalLabel').text('Order ' + oid + ' details');
			$('#myModal').modal('show');
		});
		$('.btn btn-secondary').click(function() {
			aler("ciao");
			$('#ordersTable > tbody').html("");
		});
	});
</script>

<title>Transactions</title>
</head>
<body>
	<%
		if (auth == null) {
			response.sendRedirect(response.encodeRedirectURL("../errors"
			+ "/" + "session_timeout.html"));
		}
			LinkedList<Transaction> list = new LinkedList<Transaction>();
			list = transactionBean.getTransactions();
			pageContext.setAttribute("list", list);
	%>
	<nav class="navbar navbar-default">
		<div class="container-fluid">
			<div class="navbar-header">
				<a class="navbar-brand" href="../login.html">Fly Portal</a>
			</div>
			<div class="navbar-header">
				<a class="navbar-brand" href="../flights/admin_flights.html">Flights
					Table</a>
			</div>
			<ul class="nav navbar-nav navbar-right">
				<li class="bg-danger"><a href='../LogoutServlet'>logout</a></li>
			</ul>
		</div>
	</nav>
	<div class="container">
		<div class="panel panel-primary">
			<div class="panel-heading">
				<h3 class="panel-title">Transactions</h3>
			</div>
			<div class="panel-body">
				<table class="table table-hover" id="transactionsTable">
					<thead>
						<tr>
							<th>Order ID</th>
							<th>User</th>
							<th>Amount</th>
							<th>Status</th>
							<th>Timestamp</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${list}" var="item">
							<tr class="clickable-row" id="${item.getOrderId()}">
								<td>${item.getOrderId()}</td>
								<td>${item.getUsername()}</td>
								<td>${item.getAmount()}</td>
								<td>${item.getTimestamp()}</td>
								<td>${item.getStatus()}</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</div>
	</div>

	<!-- Modal -->
	<div class="modal fade" id="myModal" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title" id="myModalLabel"></h4>
				</div>
				<div class="modal-body">
					<table class="table table-hover" id="ordersTable">
						<thead>
							<tr>
								<th>Flight</th>
								<th>Seats</th>
								<th>Price</th>
							</tr>
						</thead>
						<tbody>
						</tbody>
					</table>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-secondary"
						data-dismiss="modal">Close</button>
				</div>
			</div>
		</div>
	</div>
</body>
</html>
