<!doctype html>
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
<h1><a href='/'>Game<span>Center</span></a></h1>
<h2>Assignment 3</h2>
</header>
<h3>Welcome :<b> <%= sessUsername %></b></h3>
<nav>
<ul>
<li class=''><a href='/asg3/index.jsp'>Home</a></li>
<li class='start selected'><a href='/asg3/GamesPage.jsp'>Games</a></li>
<li class="end"><a href="/asg3/Logout.jsp">Sign Out</a></li>
</ul>
</nav>
<img class='header-image' src='images/actlogo.png' width = '100%' height = '100%' alt='X Box' />
<div id='body'>
<section id='content'>
<article>
<h2>Games</h2>
<p>Following are the different Games for sale.</p>
<p>Click on a product to purchase or check the reviews.</p>
</article>
<article class='expanded'>
<h2>Games</h2>
<table>

<%@ page import="com.ProdDS, com.DataManipulation, java.io.*, java.util.HashMap ,java.util.Map, java.util.*, com.mongodb.* , javax.servlet.* , javax.servlet.http.*" %>
<%
	 ServletContext sc = request.getServletContext();
     BufferedReader buffReader = new BufferedReader(new FileReader(sc.getRealPath("productdetails.txt")));

     ArrayList<ProdDS> listx = new ArrayList<ProdDS>();
        String readInput;
        while((readInput = buffReader.readLine()) != null)
        {
            ProdDS pds = new ProdDS();
            DataManipulation.setData(readInput,pds);
            listx.add(pds);
        }

      for(ProdDS element : listx)
        {
            if(element.getProdCat().equals("Games"))
            {
                String manfRebate = element.getProdManfReb();
                String retDiscount = element.getProdRetDisc();
                double originalPrice = element.getProdPrice();

                double manfDisc = Double.valueOf(manfRebate.replaceAll("%",""));
                double retDisc = Double.valueOf(retDiscount.replaceAll("%",""));
                double newPrice = (((100.00 - manfDisc)/100.00) * originalPrice) * ((100.00 - retDisc)/100.00); 
                double manDispric = (((100.00 - manfDisc)/100.00) * originalPrice) ;
                double disMan = (manfDisc/100.00) * originalPrice ; 
                double disRetaAm = ((100.00 - manfDisc)/100.00) * originalPrice *(retDisc/100.00) ; 
                double newDisc = disMan + disRetaAm ; 
                newPrice = Math.round(newPrice*100)/100; 

                double discPercent = ((newDisc/originalPrice) * 100); 
                int roundedDicPercent = (int) Math.round(discPercent);


%> 
				<tr>
				<td>
					<img src = 'images/eaprod.png' width = '200' height = '200' alt = 'X Box Orginal'>
				</td>
				<td>
					<p> Specifications </p>
					<b>Product Name: </b> <%= element.getProdName() %><br>
					<b>Description: </b><%= element.getProdDesc() %></br>
					<b>Price: </b><%= element.getProdPrice() %></br>
					<b>Manufacturer: </b><%= element.getProdManfName() %></br>
					<b>Discount: </b><span style='color:green'><%= roundedDicPercent %>% Off</span></br>
					<b>Discounted Price: </b><%= newPrice %></br>
				</td>
				<td>
					<form class = 'submit-button' method = 'post' action = '/asg3/AddToCart.jsp'>
					<input type = 'hidden' name = 'hiddenProdID' value = '<%= element.getID() %>'>
					<input type = 'hidden' name = 'hiddenProdName' value = '<%= element.getProdName() %>'>
					<input type = 'hidden' name = 'hiddenProdDesc' value = '<%= element.getProdDesc() %>'>
					<input type = 'hidden' name = 'hiddenProdPrice' value = '<%= newPrice %>'>
					<input type = 'hidden' name = 'hiddenProdCateg' value = '<%= element.getProdCat() %>'>
					<input class = 'submit-button' type = 'submit' name = 'XBox_Original' value = 'Add to Cart'>
					</form>
					<form id='writeReview' class = 'submit-button' method = 'post' action = '/asg3/WriteReviews.jsp'>
					<input type = 'hidden' name = 'hiddenProdID' value = '<%= element.getID() %>'>
					<input type = 'hidden' name = 'hiddenProdName' value = '<%= element.getProdName() %>'>
					<input type = 'hidden' name = 'hiddenProdName' value = '<%= element.getProdName() %>'>
					<input type = 'hidden' name = 'hiddenProdDesc' value = '<%= element.getProdDesc() %>'>
					<input type = 'hidden' name = 'hiddenProdPrice' value = '<%= newPrice %>'>
					<input type = 'hidden' name = 'hiddenProdCateg' value = '<%= element.getProdCat() %>'>
					<input type = 'hidden' name = 'hiddenZip' value = '60616'>
					<input type = 'hidden' name = 'hiddenState' value = 'IL'>
					<input type = 'hidden' name = 'hiddenCity' value = 'Chicago'>
					<input type = 'hidden' name = 'hiddenManfName' value = '<%= element.getProdManfName() %>'>
					<input type = 'hidden' name = 'hiddenManfRebate' value = 'yes'>
					<input class = 'submit-button' type = 'submit' name = 'XBox_Original' value = 'Write Review'>
					</form>
					<form class = 'submit-button' method = 'post' action = '/asg3/ViewReviews.jsp'>
					<input type = 'hidden' name = 'hiddenProdID' value = '<%= element.getID() %>'>
					<input class = 'submit-button' type = 'submit' name = 'XBox_Original' value = 'View Reviews'>
					</form>
				</td>
				</tr>
<%
            }
         }
         buffReader.close();
%>
</table>
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
