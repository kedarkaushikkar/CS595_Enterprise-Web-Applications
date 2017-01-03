<html>

<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>CSP 595 - Assignment 3</title>
	<link rel="stylesheet" href="styles.css" type="text/css" />
</head>

<body>
<div id="container">
    <header>
    	<h1><a href="/">CSP595 <span>GameSpeed Retailer</span></a></h1>
        <h2>Assignment 3 for CSP 595</h2>
    </header>
    <hr>
	<%@ page import="java.io.*, java.util.HashMap,java.util.Map,java.util.*, com.mongodb.*" %>

	<%
		PrintWriter output = response.getWriter();
	   Map users = new HashMap();
	    users.put("admin", "admin");
        users.put("salesman", "pass");
        String message = "";
	   
	   	String userid = request.getParameter("userid");
        String password = request.getParameter("password");
        int isValidEntry = 0;
        if(userid != null && userid.length() != 0) {
            userid = userid.trim();
        }
        if(password != null && password.length() != 0) {
            password = password.trim();
        }

        if(userid != null &&
            password != null) 
        {
                String realpassword = (String)users.get(userid);
                if(realpassword != null && realpassword.equals(password)) {
                    //showPage(response, "Login Success!");
                    if(realpassword.equals("admin"))
                        response.sendRedirect("AdminPage.jsp");
                    else if(realpassword.equals("pass"))
                        response.sendRedirect("SalesmanPage.jsp");
                    else
                        message = "Admins Failure! Username or password is incorrect";
                } 
                else 
                {
                    MongoClient mongo = new MongoClient("localhost", 27017);
					DB db = mongo.getDB("CSP595Tutorial");

					DBCollection usrlogins = db.getCollection("LoginDetails");
					BasicDBObject query = new BasicDBObject();
					query.put("username", userid);
					query.put("password", password);

					DBCursor cursor = usrlogins.find(query);
					if(cursor.count() == 0){
						isValidEntry = 0;
					}
					else
					{
						isValidEntry = 1;
					}

                    if(isValidEntry == 1){
                    	session.setAttribute("username",userid);
                        response.sendRedirect("index.jsp");
                    }else{
                        message = "Failure! Username or password is incorrect";
                    }
                }
        }
        else
        {
            message = "Login Failure!  You must supply a username and password";
        }
	%>

	<h3><%= message %> </h3>
</div>
<footer>
<div class='footer-bottom'>
<p>CSP 595 - Enterprise Web Application - Assignment 3</p>
</div>
</footer>
</div>
</body>
</html>"