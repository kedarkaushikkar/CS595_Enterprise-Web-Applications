<!doctype html>

<!-- INDEX -->
<html>

<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>Game Center</title>
	<link rel="stylesheet" href="styles.css" type="text/css" />
</head>
<%
String sessUsername = (String)session.getAttribute("username");
%>
<body>
<div id="container">
    <header>
    	<h1><a href="/">Game<span>Center</span></a></h1>
        <h2>All in one online game store</h2>
    </header>
    <h3>Welcome :<b> <%= sessUsername %></b></h3>
    <nav>
    	<ul>
        	<li class="start selected"><a href="/asg3/index.jsp">Home</a></li>
            <li class=""><a href="/asg3/Microsoft.jsp">Microsoft</a></li>
            <li class=""><a href="/asg3/Sony.jsp">Sony</a></li>
            <li class=""><a href="/asg3/Nitendo.jsp">Nitendo</a></li>
            <li class=""><a href="/asg3/Warranty.jsp">Buy Warranty Cards</a></li>
            <li class=""><a href="/asg3/TrackCustOrders.jsp">Track Your Order</a></li>
            <li class="end"><a href="/asg3/Logout.jsp">Sign Out</a></li>
        </ul>
    </nav>

	<img class="header-image" src="images/img_index1.jpg" width = "100%" height = "100%" alt="Index Page Image" />

    <div id="body">		

	<section id="content">

	    <article>
			
			<h2>Welcome to Game Center</h2>
			
            <p>Guaranteed 1-day delivery. 100% purchase protection. Easy returns &amp; </p>	
            
            <p>Beautiful Things, Updated Daily.</p>
			
		</article>
    </section>
        
    <aside class="sidebar">
	
            <ul>	
               <li>
                    <h4>Shop By Product Categories</h4>
                    <ul>
                        <li><a href="/asg3/GameConsolesPage.jsp">Game Consoles</a></li>
                        <li><a href="/asg3/GamesPage.jsp">Games</a></li>
                        <li><a href="/asg3/Accessories.jsp">Accessories</a></li>
                        <li><a href="/asg3/Warranty.jsp">Warranty Cards</a></li>
                    </ul>
                </li>
                
                <li>
                    <h4>About us</h4>
                    <ul>
                        <li class="text">
                        	<p style="margin: 0;">This is a sample website created to demonstrate a standard enterprise web page.</p>
                        </li>
                    </ul>
                </li>
                
                <li>
                    <h4>About US</h4>
                    <ul>
                        <li><a href="http://my.iit.edu" title="premium templates">About US</a></li>
                        <li><a href="http://my.iit.edu" title="web hosting">Contact US</a></li>
                    </ul>
                </li>
            </ul>
    </aside>
	<div class="clear"></div>
	</div>
	<footer>
        <div class="footer-content">
            <ul>
            	<li><h4>See more Products</h4></li>
                <li><a href="/asg3/Microsoft.jsp">Microsoft</a></li>
                <li><a href="/asg3/Sony.jsp">Sony</a></li>
                <li><a href="/asg3/Nitendo.jsp">Nitendo</a></li>
			</ul>
        <div class="clear"></div>
        </div>
		
        <div class="footer-bottom">
            <p>CSP 595 - Enterprise Web Application - Assignment 1</p>
        </div>
		
    </footer>
</div>

</body>

</html>