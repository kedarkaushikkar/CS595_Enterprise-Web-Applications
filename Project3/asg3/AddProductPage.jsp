<html>

<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>CSP 595 - Assignment 3</title>
	<link rel="stylesheet" href="styles.css" type="text/css" />
</head>

<body>
<div id="container">
    <header>
    <h1><a href='login.jsp'>CSP 595<span>Assignment 3</span></a></h1>
    <h2>This is Assignment 3 for CSP 595</h2>
    </header>

    <%@ page import="java.io.*, java.util.HashMap,java.util.Map,java.util.*, com.mongodb.* , javax.servlet.http.* , javax.servlet.*" %>

    <jsp:useBean id="productdetails" class="com.ProductDetails" scope="request"></jsp:useBean> 
    <jsp:setProperty property="*" name="productdetails"/>
    <%
        int s1 = productdetails.getId();
        String s2 = productdetails.getProdname();
        String s3 = productdetails.getProddesc();
        String s4 = productdetails.getProdcat();
        String s5 = productdetails.getManfname();
        String s6 = productdetails.getProdprice();
        s6 = s6 + ".0";
        String s7 = productdetails.getManfreb();
        String s8 = productdetails.getRetdisc();
        boolean isValidEntry = false;
        String message = "";

        if(s2 != null && s2.length() != 0) {
            s2 = s2.trim();
        }
        if(s3 != null && s3.length() != 0) {
            s3 = s3.trim();
        }
         if(s4 != null && s4.length() != 0) {
            s4 = s4.trim();
        }
         if(s5 != null && s5.length() != 0) {
            s5 = s5.trim();
        }
         if(s6 != null && s6.length() != 0) {
            s6 = s6.trim();
        }
        if(s7 != null && s7.length() != 0) {
            s7 = s7.trim();
        }
        if(s8 != null && s8.length() != 0) {
            s8 = s8.trim();
        }

        ServletContext sc = request.getServletContext();
        File filename = new File(sc.getRealPath("productdetails.txt"));
        FileWriter filestream = new FileWriter(filename,true);
        BufferedWriter buffWriter = new BufferedWriter(filestream);

        
		buffWriter.write(s1+"="+s2+","+s3+","+s4+","+s5+","+s6+","+s7+","+s8);
        buffWriter.close();
        filestream.close();
        message = "Product added successfully";
		
     %>

     <h2><%= message %></h2>
    <br>
    <a href='/asg3/AdminPage.jsp'><h2>Click here to see the products<h2></a>
    <footer>
    <div class='footer-bottom'>
   <p>CSP 595 - Enterprise Web Application - Assignment 3</p>
   </div>
   </footer>
   </div>
   </body>
   </html>