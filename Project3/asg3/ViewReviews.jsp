<!doctype html>
<html>
<head>
<meta http-equiv='Content-Type' content='text/html; charset=utf-8' />
<title>Game Center</title>
<link rel='stylesheet' href='styles.css' type='text/css' />
</head>
<body>
<div id='container'>
<header>
<h1><a href='/'>Game<span>Center</span></a></h1>
<h2>Assignment 3</h2>
</header>
<nav>
<ul>
<li class='start selected'><a href='/asg3/index.jsp'>Home</a></li>
</ul>
</nav>
<div id='body'>
<section id='content'>
<article>

<%@ page import="java.io.*, java.util.HashMap,java.util.Map,java.util.*, com.mongodb.* , javax.servlet.* , javax.servlet.http.*,java.util.Arrays,java.util.List, java.util.Set,java.util.Date,java.util.Random,java.util.Calendar,java.text.DateFormat,java.text.SimpleDateFormat" %>

<%
	String searchField = "productID";
	int productID = Integer.parseInt(request.getParameter("hiddenProdID"));

	int searchParameter = 0;
	if (productID > 0){
	searchParameter = productID;
	}

	MongoClient mongo = new MongoClient("localhost", 27017);
	DB db = mongo.getDB("CSP595Tutorial");

	DBCollection custDetailReviews = db.getCollection("custDetailReviews");

	BasicDBObject searchQuery = new BasicDBObject();
	searchQuery.put(searchField, searchParameter);

	DBCursor cursor = custDetailReviews.find(searchQuery);
	if(cursor.count() == 0){
%>
	<h3>There are no reviews for this product.</h3>
<%
	}
	else
	{
%>
<%
		while (cursor.hasNext()) {
			BasicDBObject obj = (BasicDBObject) cursor.next();
%>
		<br>
		<table>
			<tr>
				<td> Item Number: </td>
				<td><%= obj.getString("productID") %></td>
			</tr>
			<tr>
				<td> Product Name: </td>
				<td><%= obj.getString("productName") %></td>
			</tr>
			<tr>
				<td> User Name: </td>
				<td><%= obj.getString("userName") %></td>
			</tr>
			<tr>
				<td> Review Rating: </td>
				<td><%= obj.getString("reviewRating") %></td>
			</tr>
			<tr>
				<td> Review Date: </td>
				<td><%= obj.getString("reviewDate") %></td>
			</tr>
			<tr>
				<td> Review Text: </td>
				<td><%= obj.getString("reviewText") %></td>
			</tr>
		</table>
<%
		}
%>

<%
	}
%>
<br>
<br>
<a href='/asg3/index.jsp'>Go back to home page</a>
</article>
</section>
<div class='clear'></div>
</div>
<footer>
<div class='footer-content'>
<ul>
<li><h4>Quick Links</h4></li>
<li><a href='/asg3/index.jsp'>Homepage</a></li>
</ul>
<div class='clear'></div>
</div>
<div class='footer-bottom'>
<p>CSP 595 - Enterprise Web Application - Assignment 3</p>
</div>
</footer>
</div>
</body>
</html>