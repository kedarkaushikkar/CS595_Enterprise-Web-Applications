<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">
<html>
<head>
<meta http-equiv='Content-Type' content='text/html; charset=utf-8' />
<title>Game Center - Admin Page</title>
<link rel='stylesheet' href='styles.css' type='text/css' />
</head>
<body>
<div id='container'>
<header>
<h1><a href='http://localhost/asg3/index.jsp'>Game<span>Center</span></a></h1>
<h2>Online Store for PC Gamers</h2>
</header>
<h1>Congratulations !! Order Placed Successfully</h1>
<fieldset>
<legend>Order Details:</legend>
<%@ page import="com.cart,com.ProdDS, com.DataManipulation, java.io.*, java.util.HashMap ,java.util.Map, java.util.*, com.mongodb.* , javax.servlet.* , javax.servlet.http.* , java.util.Arrays,java.util.List,java.util.Set , java.util.Date , java.util.Random , java.util.Calendar , java.text.DateFormat , java.text.SimpleDateFormat" %>

<%
	String userID = request.getParameter("UserID").trim();
	String prodIDs = request.getParameter("hiddenProductIDs");
	String prodTotal = request.getParameter("hiddenOrderTotal");
	String firstName = request.getParameter("firstName");
	String lastname = request.getParameter("lastName");
	String address = request.getParameter("address");
	String phoneNumber = request.getParameter("phoneNumber");
	String creditCard = request.getParameter("creditcard");

	DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
	Date date = new Date();
	String currDate = dateFormat.format(date);
	
	Calendar cal = Calendar.getInstance();
	cal.setTime(new Date());

	cal.add(Calendar.DATE, 14);
	String expDelvDate = dateFormat.format(cal.getTime());

	Random r = new Random();
	int low = 10000;
	int high = 99999;
	int orderID = r.nextInt(high-low) + low;
	
	MongoClient mongo = new MongoClient("localhost", 27017);
	DB db = mongo.getDB("CSP595Tutorial");
	DBCollection myOrders = db.getCollection("myOrders");

	BasicDBObject doc = new BasicDBObject("title", "myOrders").
	append("UserID", userID).
	append("OrderID", orderID).
	append("ProductID", prodIDs).
	append("FirstName", firstName).
	append("LastName", lastname).
	append("Address", address).
	append("PhoneNumber", phoneNumber).
	append("CreditCard", creditCard).
	append("OrderDate", currDate).
	append("DelvDate", expDelvDate);
	myOrders.insert(doc);
%>

<table>
<tr>
<td>OrderID</td>
<td><%= orderID %></td>
</tr>
<tr>
<td>FirstName</td>
<td><%= firstName %></td>
</tr>
<tr>
<td>LastName</td>
<td><%= lastname %></td>
</tr>
<tr>
<td>Address</td>
<td><%= address %></td>
</tr>
<tr>
<td>Phone Number</td>
<td><%= phoneNumber %></td>
</tr>
<tr>
<td>Order Date</td>
<td><%= currDate %></td>
</tr>
<tr>
<td>Expected Delivery Date</td>
<td><%= expDelvDate %></td>
</tr>
</table>
</fieldset>
<a href='/asg3/index.jsp'>Go Back To Home Page</a>
<footer>
<div class='footer-bottom'>
<p>CSP 595 - Enterprise Web Application - Assignment 3</p>
</div>
</footer>
</div>
</body>
</html>