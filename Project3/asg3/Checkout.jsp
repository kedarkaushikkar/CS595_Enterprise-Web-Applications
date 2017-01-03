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
<h1>Place Order</h1>
<form method='post' action='/asg3/SubmitOrder.jsp'>
<fieldset>
<legend>Product information:</legend>
<table>
<tr>
<th>ProductID</th>
<th>Product Name</th>
<th>Price</th>
</tr>
<%@ page import="com.cart,com.ProdDS, com.DataManipulation, java.io.*, java.util.HashMap ,java.util.Map, java.util.*, com.mongodb.* , javax.servlet.* , javax.servlet.http.*" %>

<%
String sessUsername = (String)session.getAttribute("username");
%>
<%


	cart shoppingCart;
    shoppingCart = (cart) session.getAttribute("cart");
    session.setAttribute("cart", shoppingCart);
    shoppingCart = (cart) session.getAttribute("cart");
    List<String> productIDs = new ArrayList<String>();
    HashMap<String, List<String>> items = shoppingCart.getCartItems();
    Double total = 0.00;

    for (HashMap.Entry<String, List<String>> entry : items.entrySet()) 
    {
        String key = entry.getKey();
        List<String> values = entry.getValue();
%>
	<tr>
		<td>ProductID: <input type='text' name='productID' value= '<%=values.get(0) %>' readonly> </td>
		<td>Product Name: <input type='text' name='productName' value= '<%=values.get(1) %>' readonly> </td>
		<td>Price: <input type='text' name='productPrice' value= '<%=values.get(2) %>' readonly> </td>
	</tr>
	
<%
	total = total + Double.parseDouble(values.get(2));
    productIDs.add(values.get(0));
	}
	 String idList = productIDs.toString();
     String prodIDS = idList.substring(1, idList.length() - 1).replace(", ", ",");
%>
<tr>
<td colspan ='3'>Total : <%= total %></td>
</tr>
</table>
</fieldset>
<fieldset>
<legend>Personal information:</legend>
<table>
<tr>
<td> UserID: </td>
<td> <input type='text' name='UserID' value='<%= sessUsername %>' readonly> </td>
</tr>
<tr>
<td> First name: </td>
<td> <input type='text' name='firstName'> </td>
</tr>
<tr>
<td> Last name: </td>
<td> <input type='text' name='lastName'> </td>
</tr>
</tr>
<tr>
<td> Address: </td>
<td> <input type='text' name='address'> </td>
</tr>
<tr>
<td> Phone: </td>
<td> <input type='text' name='phoneNumber'> </td>
</tr>
<tr>
<td> CreditCard: </td>
<td> <input type='password' maxlength='16' name='creditcard'> </td>
</tr>
</table>
<br><br>
<input type='hidden' name='hiddenOrderTotal' value='<%= total %>'>
<input type='hidden' name='hiddenProductIDs' value='<%= prodIDS %>'>
<input class = 'submit-button' type = 'submit' name = 'orderButton' value = 'Place Order'>
</fieldset>
</form>
<footer>
<div class='footer-bottom'>
<p>CSP 595 - Enterprise Web Application - Assignment 3</p>
</div>
</footer>
</div>
</body>
</html>
