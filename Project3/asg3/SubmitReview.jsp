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
	int productID = Integer.parseInt(request.getParameter("hiddenProdID"));
	String productName = request.getParameter("hiddenProdName");
	String userName = request.getParameter("userName");
	int reviewRating = Integer.parseInt(request.getParameter("reviewRating"));
	int userAge = Integer.parseInt(request.getParameter("userAge"));
	String userGender = request.getParameter("userGender");	
	String reviewDate = request.getParameter("reviewDate");
	String reviewText = request.getParameter("reviewText");

	Double prodPrice = Double.valueOf(request.getParameter("hiddenProdPrice"));
	String prodCategory = request.getParameter("hiddenProdCateg");
	String prodManfName = request.getParameter("hiddenManfName");
	String userCity = request.getParameter("userCity");
	String userZip = request.getParameter("userZip");
	String userState = request.getParameter("userState");
	String retailerName = "GameCenter";
	String retailerZip = request.getParameter("hiddenZip");
	String retailerCity = request.getParameter("hiddenCity");
	String retailerState = request.getParameter("hiddenState");
	String prodOnSale = request.getParameter("productOnSale");

	MongoClient mongo = new MongoClient("localhost", 27017);
	DB db = mongo.getDB("CSP595Tutorial");

	DBCollection custDetailReviews = db.getCollection("custDetailReviews");
	BasicDBObject doc = new BasicDBObject("title", "custDetailReviews").
	append("productID", productID).
	append("productName", productName).
	append("userName", userName).
	append("userAge", userAge).
	append("userGender", userGender).
	append("reviewRating", reviewRating).
	append("reviewDate", reviewDate).
	append("reviewText", reviewText).
	append("productPrice", prodPrice).
	append("productCategory", prodCategory).
	append("productOnSale", prodOnSale).
	append("manfName", prodManfName).
	append("retailerName", retailerName).
	append("retailerCity", userCity).
	append("retailerZip", userZip).
	append("retailerState", userState);
	custDetailReviews.insert(doc);
%>

<h2> Review submitted successfully.</h2></br>
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