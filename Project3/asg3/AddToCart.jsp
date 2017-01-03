<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">
<html>
<head>
<meta http-equiv='Content-Type' content='text/html; charset=utf-8' />
<title>Game Center</title>
<link rel='stylesheet' href='styles.css' type='text/css' />
</head>
<%
String sessUsername = (String)session.getAttribute("username");
%>
<body>
<div id='container'>
<header>
<h1><a href='http://localhost/csj/index.jsp'>Game<span>Center</span></a></h1>
<h2>Cart Page</h2>
</header>
<h3>Welcome :<b> <%= sessUsername %></b></h3>
<table>
<tr>
<th>ProductID</th>
<th>Product Name</th>
<th>Price</th>
<th>Category</th>
<th></th>
</tr>
<%@ page import="com.cart,com.ProdDS, com.DataManipulation, java.io.*, java.util.HashMap ,java.util.Map, java.util.*, com.mongodb.* , javax.servlet.* , javax.servlet.http.*" %>

<%
	String prodID = request.getParameter("hiddenProdID");
    String prodName = request.getParameter("hiddenProdName");
    String prodDesc = request.getParameter("hiddenProdDesc");
    String prodPrice = request.getParameter("hiddenProdPrice");
    String prodCateg = request.getParameter("hiddenProdCateg");
    boolean allowCheckout = false;


    		List<String> valSetOne = new ArrayList<String>();
        	valSetOne.add(prodID);
        	valSetOne.add(prodName);
        	valSetOne.add(prodPrice);
        	valSetOne.add(prodCateg);


        cart shoppingCart;
        shoppingCart = (cart) session.getAttribute("cart");
        if(shoppingCart == null){
          shoppingCart = new cart();
          session.setAttribute("cart", shoppingCart);
        }

        
        shoppingCart.addToCart(prodID, valSetOne);
        session.setAttribute("cart", shoppingCart);

        HashMap<String, List<String>> items = shoppingCart.getCartItems();

        for (HashMap.Entry<String, List<String>> entry : items.entrySet()) {
        if(entry.getKey() != null){
        	allowCheckout = true;
        	String key = entry.getKey();
        	List<String> values = entry.getValue();
%>
		<tr>
			<td><%= values.get(0)%></td>
			<td><%= values.get(1)%></td>
			<td><%= values.get(2)%></td>
			<td><%= values.get(3)%></td>
			<td>
				<form method = 'post' action = '/asg3/DeleteCartItem.jsp'>
					<input type='hidden' name='hiddenDelProdID' value='<%= key %>'>
					<input class = 'submit-button' type = 'submit' name = 'deleteButton' value = 'Delete'>
				</form>
			</td>
		</tr>
<%
			}
		}
%>

</table>
<a href='/asg3/index.jsp'>Click here to add more Products to Cart</a>
</br></br>
<%
	if(allowCheckout){
%>
<form class = 'submit-button' method = 'post' action = '/asg3/Checkout.jsp'>
	<input class = 'submit-button' type = 'submit' name = 'XBox_Original' value = 'Proceed To Checkout'>
</form>
<%
	}
%>
<footer>
<div class='footer-bottom'>
<p>CSP 595 - Enterprise Web Application - Assignment 3</p>
</div>
</footer>
</div>
</body>
</html>