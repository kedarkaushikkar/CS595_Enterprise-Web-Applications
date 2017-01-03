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
	int userOrderID = Integer.parseInt(request.getParameter("hiddenOrderID"));
	String expDelvDate = "";
	//Get the product selected
	int searchParameter = 0;
	if (userOrderID > 0){
	searchParameter = userOrderID;
	}

	MongoClient mongo = new MongoClient("localhost", 27017);
	DB db = mongo.getDB("CSP595Tutorial");
    DBCollection myOrders = db.getCollection("myOrders");
			
	BasicDBObject searchQuery = new BasicDBObject();
	searchQuery.put(searchField, searchParameter);

	DBCursor cursor = myOrders.find(searchQuery);
%>
<h1> Order Details :<%= searchParameter %></h1></article>
<%
	if(cursor.count() == 0){
%>
	<h3>No Order Found. Please Enter Correct OrderID</h3>
<%
	}
	else
	{
	String orderID = "";
	String firstName = "";
	String lastName = "";
	String orderDate = "";
	String delvDate =  "";
%>
	<table>
<%
	while (cursor.hasNext()) {
		//out.println(cursor.next());
		BasicDBObject obj = (BasicDBObject) cursor.next();				
		
		orderID = obj.getString("OrderID");
		firstName = obj.getString("FirstName");
		lastName = obj.getString("LastName");
		orderDate = obj.getString("OrderDate");
		delvDate = obj.getString("DelvDate");
		expDelvDate = obj.getString("DelvDate");
%>
		<tr>
		<td> OrderID: </td>
		<td><%= orderID %></td>
		</tr>

		<tr>
		<td> Name: </td>
		<td><%= firstName %> <%= lastName %></td>
		</tr>
		
		<tr>
		<td> Order Date: </td>
		<td><%= orderDate %></td>
		</tr>

		<tr>
		<td> Delivery Date: </td>
		<td><%= delvDate %></td>
		</tr>
<%
		}
%>
	</table>
<%

	expDelvDate = expDelvDate.replaceAll("/","-");
	DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

	Date date = new Date();
	String currDate = dateFormat.format(date);

	Date beginDate = dateFormat.parse(currDate);
	Date endDate = dateFormat.parse(expDelvDate);

	Calendar beginCalendar = Calendar.getInstance();
	beginCalendar.setTime(beginDate);

	Calendar endCalendar = Calendar.getInstance();
	endCalendar.setTime(endDate);
	int minusDays = 0;
	while (true) {
	  minusDays++;

	  beginCalendar.add(Calendar.DAY_OF_MONTH, 1);

	  if (dateFormat.format(beginCalendar.getTime()).equals(dateFormat.format(endCalendar.getTime()))) {
	  	break;
	  }
	}
	int dateDiff = minusDays + 1;
	if(dateDiff == 15)
		dateDiff = 14;

	if(dateDiff == 0)
	{
%>
	<br><br>
	<b>Order has been Delivered.</b><br>
    <br>
<%
	}
	else if(dateDiff < 5)
    {
%>
	<br><br>
	<b>Item has been shipped and will be reach soon.Approximately within <%= dateDiff %> days</b><br>
	<span style='font-size:'6'; color:'red''>Note: Order cannot be cancelled as the estimated delivery date is less than 5 more days</span>
	<br>
<%
	}
	else{
%>
	<br><br>
	<b>Item will be shipped within <%= dateDiff %> Days.</b><br>
	<span style='font-size:'6'; color:'red''>Note: Order can be cancelled only until 5 days before estimated delivery date</span></br>
	<form class = 'submit-button' method = 'get' action = '/asg3/CancelOrder.jsp'>
	<input type = 'hidden' name = 'hiddenOrderID' value = '<%= userOrderID %>'>
    <input class = 'submit-button' type = 'submit' name = 'cancelOrder' value = 'Cancel Order'>
    </form>
	<br>
<%
	}
%>
<%
}
%>
</section>
<div class='clear'></div>
</div>
<footer>
<div class='footer-content'>
<ul>
<li><h4>Quick Links</h4></li>
<li><a href='/asg3/index.jsp'>Homepage</a></li>
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