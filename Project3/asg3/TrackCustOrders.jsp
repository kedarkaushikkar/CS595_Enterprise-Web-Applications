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
String sessUsername = (String)session.getAttribute("username");
String message = "";
boolean hasOrders = false;

if(sessUsername == "")
{%>
<h3>Please Login again to see the customer orders.</h3>
<%	
}
else{
	MongoClient mongo = new MongoClient("localhost", 27017);
	DB db = mongo.getDB("CSP595Tutorial");
    DBCollection myOrders = db.getCollection("myOrders");
    BasicDBObject query = new BasicDBObject();
	query.put("UserID", sessUsername);

	DBCursor cursor = myOrders.find(query);
	if(cursor.count() == 0){
        message = "No Customer Orders Yet.";
    }else{
    	 hasOrders = true;
    }

    if(hasOrders)
    {
%>
		<table>
             <tr>
                    <th>OrderID</th>
                    <th>Customer Name</th>
                    <th>OrderDate</th>
                    <th>Delivery Date</th>
                    <th>Modify Order</th>
                    </tr>
<%
		while(cursor.hasNext()) {
         BasicDBObject obj = (BasicDBObject) cursor.next();
%>
		<tr>
			<td><%= obj.getString("OrderID") %></td>
            <td><%= obj.getString("FirstName") %> <%= obj.getString("LastName") %></td>
            <td><%= obj.getString("OrderDate") %></td>
            <td><%= obj.getString("DelvDate") %></td>
            <td>
            	<form class = 'submit-button' method = 'post' action = '/asg3/TrackOrder.jsp'>
	                <input type = 'hidden' name = 'hiddenOrderID' value = '<%= obj.getString("OrderID")%>'>
	                <input class = 'submit-button' type = 'submit' name = 'trackOrder' value = 'Track Order'>
                </form>
			</td>
        </tr>
<%
		}
%>
		</table>
<% 	}
	else{
%>
	<h3> <%= message %> </h3>
<%
	}
} 
%>

</article>
</section>
<a href='/asg3/index.jsp'>Create New Order</a>
<div class='clear'></div>
</div>
	<footer>
<div class='footer-content'>
<ul>
<li><h4>Quick Links</h4></li>
<li><a href='/asg3/Logout.jsp'>Logout</a></li>
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