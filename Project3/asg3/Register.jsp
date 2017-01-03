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
<%@ page import="java.util.*, java.io.*, com.mongodb.* , javax.servlet.http.* , javax.servlet.*" %>

<jsp:useBean id="userlogininfo" class="com.RegisterDetails" scope="request"></jsp:useBean> 
<jsp:setProperty property="*" name="userlogininfo"/>
<%
	   String name = userlogininfo.getName();
	   String username = userlogininfo.getUsername();
	   String password = userlogininfo.getPassword();
	   String message = "";
	   String usrMsg = "";
	   boolean isValidReg = false;
	   if (username != "" && username.length() != 0){
				username = username.trim();
		}
			
		if (password != "" && password.length() != 0){
				password = password.trim();
		}
	   
%>   
<%
		MongoClient mongo = new MongoClient("localhost", 27017);
		DB db = mongo.getDB("CSP595Tutorial");
		if(username != "" && password != ""){
			
				
				DBCollection usrlogins = db.getCollection("LoginDetails");
				BasicDBObject query = new BasicDBObject("username",username);
				DBCursor cursor = usrlogins.find(query);

				if(cursor.count() == 0){
					BasicDBObject user = new BasicDBObject("name",name).
						append("username",username).
						append("password",password);
						usrlogins.insert(user);
				
					message = "Thank you for registration.";
					usrMsg = "Success";		
				}else{

					message = "Username already exists.";
					usrMsg = "Failed";
					isValidReg = true;

				}
		}else{
				message = "Registeration failed";
				usrMsg = "Failed";
				isValidReg = true;
		}

	%>

	<h1><%= message %></h2>

<% if(!isValidReg){ %>
	<a href='registration.jsp'><h2>Sign In<h2></a>
	<br>
<%	}else {	%>
	<h2> Hello <%= name %></h2>
	<br>
	<a href='login.html'><h2>Sign In<h2></a>	
	<h2></h2>
<%	}	%>

<footer>
<div class='footer-bottom'>
<p>CSP 595 - Enterprise Web Application - Assignment 3</p>
</div>
</footer>
</div>
</body>
</html>"