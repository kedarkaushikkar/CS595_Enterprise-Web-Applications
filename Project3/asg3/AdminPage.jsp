<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\>;
<html>
<head>
<meta http-equiv='Content-Type' content='text/html; charset=utf-8' />
<title>Game Center - Admin Page</title>
<link rel='stylesheet' href='styles.css' type='text/css' />
</head>
<body>
<div id='container'>
<header>
<h1><a href='index.jsp'>Game<span>Center</span> Admin Panel</a></h1>
<h2>Online Store for PC Gamers</h2>
</header>
<table>
<tr>
<th>ProductID</th>
<th>Product Name</th>
<th>Description</th>
<th>Category</th>
<th>Manufacter Name</th>
<th>Price</th>
<th>Manufacter Rebate</th>
<th>Retailer Discount</th>
</tr>
<%@ page import="java.io.*, java.util.HashMap,java.util.Map,java.util.*, com.mongodb.* , javax.servlet.* , javax.servlet.http.*" %>

<%
	ServletContext sc = request.getServletContext();
      BufferedReader buffReader = new BufferedReader(new FileReader(sc.getRealPath("productdetails.txt")));
      HashMap<String,String> product = new HashMap<String,String>();
      String currentLine;
            
%>
            <%  while((currentLine =buffReader.readLine())!=null) {
            	String [] prods = currentLine.split("=");
                  String [] prodVars = prods[1].split(","); 
            %>
            	<tr>
            		<td><%= prods[0] %></td>
            		<td><%= prodVars[0] %></td>
            		<td><%= prodVars[1] %></td>
            		<td><%= prodVars[2] %></td>
            		<td><%= prodVars[3] %></td>
            		<td><%= prodVars[4] %></td>
            		<td><%= prodVars[5] %></td>
            		<td><%= prodVars[6] %></td>
           <% } %>


</table>
<a href='AddProductForm.jsp'>Click here to add more Products</a>
</br></br>
<fieldset>
<legend>Update Product</legend>
<form id='updateProduct' action='UpdateProductPage.jsp' method='post'>
<p>Enter the ProductID to be Updated</p>
<input type='text' name='prodID' id='prodID' maxlength='2'/>
<label for='updateColumnName' >Update Field:</label>
<select name='updateColumnName'>
<option value='Name'>Name</option>
<option value='Description'>Description</option>
<option value='Category'>Category</option>
<option value='ManfName'>Manufacter Name</option>
<option value='Price'>Price</option>
<option value='ManfRebate'>Manufacter Rebate</option>
<option value='RetDiscount'>Retailer Discount</option>
</select>
<input type='text' name='updateValue' id='updateValue' maxlength='30'/>
<input type='submit' name='Submit' value='Submit' />
</form>
</fieldset>
</br></br>
<fieldset>
<legend>Delete Product</legend>
<form id='deleteProduct' action='DeleteProductPage.jsp' method='post'>
<p>Enter the ProductID to be Deleted</p>
<input type='text' name='prodID' id='prodID' maxlength='2' />
<input type='submit' name='Submit' value='Submit' />
</form>
</br></br>
<a href='login.jsp'>Sign Out</a>
</fieldset>
<footer>
<div class='footer-bottom'>
<p>CSP 595 - Enterprise Web Application - Assignment 3</p>
</div>
</footer>
</div>
</body>
</html>