<html>

<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>CSP 595 - Assignment 3</title>
	<link rel="stylesheet" href="styles.css" type="text/css" />
</head>
<body>
<div id='container'>
<header>
<h1><a href='index.jsp'>CSP 595<span>Assignment 3</span></a></h1>
<h2>This is Assignment 3 for CSP 595</h2>
</header>
<%@ page import="java.util.*, java.io.*, com.mongodb.*" %>
<%
	session.invalidate();
	response.sendRedirect("/asg3/login.jsp");
%>   
<footer>
<div class='footer-bottom'>
<p>CSP 595 - Enterprise Web Application - Assignment 3</p>
</div>
</footer>
</div>
</body>
</html>"