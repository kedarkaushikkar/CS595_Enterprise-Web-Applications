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
			String searchField = "OrderID";
			int cancelOrderID = Integer.parseInt(request.getParameter("hiddenOrderID"));
			String orderFound = "";

			int searchParameter = 0;
			if (cancelOrderID > 0){
				searchParameter = cancelOrderID;
			}

			MongoClient mongo = new MongoClient("localhost", 27017);
			DB db = mongo.getDB("CSP595Tutorial");
            
            DBCollection myOrders = db.getCollection("myOrders");

            BasicDBObject searchQuery = new BasicDBObject();
            searchQuery.put(searchField, searchParameter);
            DBCursor cursor = myOrders.find(searchQuery);
            
            if(cursor.count() == 0)
			{
				orderFound = "NotFound";
			}
			else
			{
				BasicDBObject doc = new BasicDBObject();
				doc.append(searchField,searchParameter);
				myOrders.remove(doc);
				orderFound = "Found";
			}
			if(orderFound.equals("Found")){
%>
			<h2> Order ID : <%= searchParameter %> has been cancelled.</h2></br>
			<a href='/asg3/index.jsp'>Go back to home page</a>
<%
			}else{
%>
			<h2>No Order Found. Please Enter Correct OrderID.</h2><br>
			<a href='/asg3/index.jsp'>Go back to home page</a>
<%
			}
%>
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