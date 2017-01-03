<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">
<html>
<head>
<meta http-equiv='Content-Type' content='text/html; charset=utf-8' />
<title>Game Center - Login</title>
<link rel='stylesheet' href='styles.css' type='text/css' />
</head>
<body>
<div id='container'>
<header>
<h1><a href='http://localhost/csj/index.html'>CSP 595<span>Assignment 3</span></a></h1>
<h2>This is Assignment 3 for CSP 595</h2>
</header>

<%@ page import="com.ProdDS, com.DataManipulation, java.io.*, java.util.HashMap ,java.util.Map, java.util.*, com.mongodb.* , javax.servlet.* , javax.servlet.http.*" %>

<%
      String userProdID = request.getParameter("prodID");
	    if(userProdID != null && userProdID.length() != 0) 
	    {
	        userProdID = userProdID.trim();
	    }

	    ServletContext sc = request.getServletContext();
        BufferedReader buffReader = new BufferedReader(new FileReader(sc.getRealPath("productdetails.txt")));

        ArrayList<ProdDS> elementList = new ArrayList<ProdDS>();
        String readInput;
        while((readInput = buffReader.readLine()) != null)
        {
            ProdDS pds = new ProdDS();
            DataManipulation.setData(readInput,pds);
            elementList.add(pds);
        }
        buffReader.close();

        ProdDS ds = new ProdDS() ;

        for(ProdDS element : elementList)
        {
            if(element.getID() == Integer.valueOf(userProdID))
            {
                ds = element ;
            }
        }

        elementList.remove(ds);
        PrintWriter writer = new PrintWriter(sc.getRealPath("productdetails.txt"));

        for(ProdDS pds : elementList){
            String line = convertDS2String(pds);
            writer.print(line);
            writer.print("\n");
        }
        writer.close();

%>

<%!
        public static String convertDS2String(ProdDS pds)
	    {
	        StringBuffer sb = new StringBuffer();
	        sb.append(pds.getID());
	        sb.append("=");
	        sb.append(pds.getProdName());
	        sb.append(",");
	        sb.append(pds.getProdDesc());
	        sb.append(",");
	        sb.append(pds.getProdCat());
	        sb.append(",");
	        sb.append(pds.getProdManfName());
	        sb.append(",");
	        sb.append(pds.getProdPrice());
	        sb.append(",");
	        sb.append(pds.getProdManfReb());
	        sb.append(",");
	        sb.append(pds.getProdRetDisc());
	        return sb.toString();
	    }
 %>

<h2>Product Deleted successfully</h2>
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