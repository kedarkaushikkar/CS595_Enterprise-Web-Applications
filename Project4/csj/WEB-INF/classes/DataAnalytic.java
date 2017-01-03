/*
 * XBOX.java
 *
 */
 
import java.io.*;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.*;

public class DataAnalytic extends HttpServlet {

    protected void processPage(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, java.io.IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        // create your filewriter and bufferedreader
        ServletContext sc = request.getSession().getServletContext();
        BufferedReader buffReader = new BufferedReader(new FileReader(sc.getRealPath("productdetails.txt")));

        ArrayList<ProdDS> listx = new ArrayList<ProdDS>();
        ArrayList<String> productNames = new ArrayList<String>();
        ArrayList<String> productCateg = new ArrayList<String>();
        ArrayList<String> productManfNames = new ArrayList<String>();
        String readInput;
        while((readInput = buffReader.readLine()) != null)
        {
            ProdDS pds = new ProdDS();
            DataManipulation.setData(readInput,pds);
            listx.add(pds);
        }

        for(ProdDS element : listx)
        {
            productNames.add(element.getProdName());
            productManfNames.add(element.getProdManfName());
            productCateg.add(element.getProdCat());
        }
        HashSet<String> uniqueProdNames = new HashSet<>(productNames);
        HashSet<String> uniqueProdCat = new HashSet<>(productCateg);
        HashSet<String> uniqueProdManf = new HashSet<>(productManfNames);

        String docType = 
        "<!DOCTYPE html><html lang='en'>\n";
        out.println(docType +
        "<html>"+
        "<head>"+
"<meta http-equiv='Content-Type' content='text/html; charset=utf-8' />"+
"<title>Data Analytics</title>"+
"<link rel='stylesheet' href='styles.css' type='text/css' />"+
"</head>"+
"<body>"+
"<div id='container'>"+
"<header>"+
"<h1><a href='/'>CSP 595<span>Assignment 2</span></a></h1>"+
"<h2>This is Assignment 2 for CSP 595</h2>"+
"</header>"+        
"<nav>"+
"<ul>"+
"<li class=''><a href='index.html'>Home</a></li>"+
"<li class='start selected'><a href='#'>Data Analytics</a></li>"+
"</ul>"+
"</nav>"+
 "<div id='body'>"+
"<section id='review-content'>"+
"<article>"+
"<h3> Find Data </h3>"+
                    "<form method='post' class='searchform' action='/csj/FindReviews'>"+
                        "<table class = 'query-table'>"+
                            "<tr>"+
                                "<td colspan = '4'> <b> Simple Search </b> </td>"+
                            "</tr>"+
                            "<tr>"+
                                "<td> <input type='checkbox' name='queryCheckBox' value='productName' checked> Select </td>"+
                                "<td> Product Name: </td>"+
                                "<td>"+
                                    "<select name='productName'>"+
                                        "<option value='ALL_PRODUCTS'>All Products</option>");
                                         for (String prodNames : uniqueProdNames) {
                                            out.println("<option value='"+prodNames+"'>"+prodNames+"</option>");
                                        }

                                       out.println(
                                "</td>"+
                                "<td> </td>"+
                            "</tr>"+

                            "<tr>"+
                                "<td> <input type='checkbox' name='queryCheckBox' value='productCateg'> Select </td>"+
                                "<td> Product Category: </td>"+
                                "<td>"+
                                    "<select name='productCat'>"+
                                        "<option value='ALL_PRODUCTS'>All Products</option>");
                                         for (String prodCat : uniqueProdCat) {
                                            out.println("<option value='"+prodCat+"'>"+prodCat+"</option>");
                                        }

                                       out.println(
                                "</td>"+
                                "<td> </td>"+
                            "</tr>"+

                            "<tr>"+
                                "<td> <input type='checkbox' name='queryCheckBox' value='productManf'> Select </td>"+
                                "<td> Manufacturer Names: </td>"+
                                "<td>"+
                                    "<select name='productManf'>"+
                                        "<option value='ALL_PRODUCTS'>All Products</option>");
                                         for (String prodManf : uniqueProdManf) {
                                            out.println("<option value='"+prodManf+"'>"+prodManf+"</option>");
                                        }

                                       out.println(
                                "</td>"+
                                "<td> </td>"+
                            "</tr>"+


                            "<tr>"+
                                "<td> <input type='checkbox' name='queryCheckBox' value='productPrice'> Select </td>"+
                                "<td> Product Price: </td>"+
                                "<td>"+
                                    "<input type='number' name='productPrice' value = '0' size='10' class='s' /> </td>"+
                                "<td>"+
                                    "<input type='radio' name='comparePrice' value='EQUALS_TO' checked> Equals <br>"+
                                    "<input type='radio' name='comparePrice' value='GREATER_THAN'> Greater Than <br>"+
                                    "<input type='radio' name='comparePrice' value='LESS_THAN'> Less Than <br>"+
                                    "<input type='radio' name='comparePrice' value='MAX'> Max (Group With) <br>"+
                                "</td>"+
                            "</tr>"+

                            "<tr>"+
                                "<td></td>"+
                                "<td> Retailer Name: </td>"+
                                "<td>"+
                                    "<input type='text' name='retailerName' value = 'GameCenter' readonly/> </td>"+
                                "<td> </td>"+
                            "</tr>"+

                            "<tr>"+
                                "<td> <input type='checkbox' name='queryCheckBox' value='retailerZipcode'> Select </td>"+
                                "<td> Retailer Zip code: </td>"+
                                "<td>"+
                                    "<input type='number' name='retailerZipcode' value = '0' size=10/> </td>"+
                                "<td> </td>"+
                            "</tr>"+

                            "<tr>"+
                                "<td> <input type='checkbox' name='queryCheckBox' value='retailerCity'> Select </td>"+
                                "<td> Retailer City: </td>"+
                                "<td>"+
                                    "<select name='retailerCity'>"+
                                        "<option value='ALL_PRODUCTS' selected>All</option>"+
                                        "<option value='Chicago'>Chicago</option>"+
                                          "<option value='Boston'>Boston</option>"+
                                          "<option value='Houston'>Houston</option>"+
                                          "<option value='Orlando'>Orlando</option>"+
                                "</td>"+
                                "<td> </td>"+
                            "</tr>"+

                            "<tr>"+
                                "<td> <input type='checkbox' name='queryCheckBox' value='retailerState'> Select </td>"+
                                "<td> Retailer State: </td>"+
                                "<td>"+
                                    "<input type='text' name='retailerState' size='10' maxlength='2'/> </td>"+
                                "<td> </td>"+
                            "</tr>"+

                            "<tr>"+
                                "<td> <input type='checkbox' name='queryCheckBox' value='reviewRating'> Select </td>"+
                                "<td> Review Rating: </td>"+
                                "<td>"+
                                    "<select name='reviewRating'>"+
                                        "<option value='1' selected>1</option>"+
                                        "<option value='2'>2</option>"+
                                        "<option value='3'>3</option>"+
                                        "<option value='4'>4</option>"+
                                        "<option value='5'>5</option>"+
                                "</td>"+
                                "<td>"+
                                    "<input type='radio' name='compareRating' value='EQUALS_TO' checked> Equals <br>"+
                                    "<input type='radio' name='compareRating' value='GREATER_THAN'> Greater Than"+
                                "</td>"+
                            "</tr>"+

                            "<tr>"+
                                "<td> <input type='checkbox' name='queryCheckBox' value='userName'> Select </td>"+
                                "<td> Search by UserName: </td>"+
                                "<td>"+
                                    "<input type='text' name='userNameField' size='10' maxlength='12'/> </td>"+
                                "<td> </td>"+
                            "</tr>"+

                            "<tr>"+
                                "<td> <input type='checkbox' name='queryCheckBox' value='userAge'> Select </td>"+
                                "<td> Search by User Age: </td>"+
                                "<td>"+
                                    "<input type='number' name='userAgeField' value = '0' size='10' maxlength='2'/> </td>"+
                                "<td>"+
                                    "<input type='radio' name='compareAge' value='EQUALS_TO' checked> Equals <br>"+
                                    "<input type='radio' name='compareAge' value='GREATER_THAN'> Greater Than <br>"+
                                    "<input type='radio' name='compareAge' value='LESS_THAN'> Less Than"+
                                "</td>"+
                            "</tr>"+

                            "<tr>"+
                                "<td> <input type='checkbox' name='queryCheckBox' value='userReviews'> Select </td>"+
                                "<td> Review Keyword: </td>"+
                                "<td>"+
                                    "<input type='text' name='userReviews' size='10' maxlength='50'/> </td>"+
                                "<td> </td>"+
                            "</tr>"+

                            "<tr>"+
                                "<td>"+
                                    "Return:"+
                                "</td>"+
                                "<td colspan = '4'>"+
                                    "<select name='returnValue'>"+
                                        "<option value='ALL' selected>All</option>"+
                                        "<option value='TOP_5'>Top 5 </option>"+
                                        "<option value='TOP_10'>Top 10 </option>"+
                                        "<option value='LATEST_5'>Latest 5 </option>"+
                                        "<option value='LATEST_10'>Latest 10 </option>"+
                                "</td>"+
                            "</tr>"+

                            "<tr>"+
                                "<td colspan = '4'> <b> Grouping </b> </td>"+
                            "</tr>"+
                            "<tr>"+
                                "<td>"+
                                "<input type='checkbox' name='extraSettings' value='GROUP_BY'> Group By"+
                                "</td>"+
                                "<td colspan = '3'>"+
                                "<select name='groupByDropdown'>"+
                                        "<option value='GROUP_BY_CITY' selected>City</option>"+
                                        "<option value='GROUP_BY_ZIPCODE'>Zip Code</option>"+
                                        "<option value='GROUP_BY_PRODUCT'>Product Name</option>"+
                                        "<option value='GROUP_BY_RETNAME'>Retailer Name</option>"+
                                        "<option value='GROUP_BY_MANFNAME'>Manufacter Name</option>"+
                                "</td>"+
                            "</tr>"+

                            "<tr>"+
                                "<td colspan = '4'> <b> MOST Liked/Disliked PRODUCTS (use group by) </b> </td>"+
                            "</tr>"+
                            "<tr>"+
                                "<td>"+
                                "<input type='checkbox' name='otherExtraSettings' value='GROUP_BY_OTHERS'> Highlights"+
                                "</td>"+
                                "<td colspan = '3'>"+
                                "<select name='topListingDropdown'>"+
                                        "<option value='TOP_5_LIKED' selected>Top 5 Liked </option>"+
                                        "<option value='TOP_5_DISLIKED'>Top 5 Disliked</option>"+
                                        "<option value='TOP_5_LIKED_EXPENSIVE'>Top 5 Liked-Expensive</option>"+
                                        "<option value='MEDIAN'>Median</option>"+
                                "</td>"+
                            "</tr>"+

                            "<tr>"+
                                "<td colspan = '4'> <input type='submit' value='Find Data' class='formbutton' /> </td>"+
                            "</tr>"+
                        "</table>"+
                    "</form>"+
                    "<form method='post' class='searchform' action='/csj/TrendingServlet'>"+
                        "<table class = 'query-table'>"+
                            "<tr>"+
                                "<td colspan = '4'> <input type='submit' value='Trending' class='formbutton' /> </td>"+
                            "</tr>"+
                        "</table>"+
                    "</form>"+
                "</article>"+
            "</section>"+
            "<div class='clear'></div>"+
        "</div>"+
        "<footer>"+
            "<div class='footer-content'>"+
                "<ul>"+
                    "<li>"+
                        "<h4>Dummy Link Section 1</h4>"+
                    "</li>"+
                    "<li><a href='#'>Dummy Link 1</a></li>"+
                    "<li><a href='#'>Dummy Link 2</a></li>"+
                    "<li><a href='#'>Dummy Link 3</a></li>"+
                "</ul>"+
                "<div class='clear'></div>"+
            "</div>"+
            "<div class='footer-bottom'>"+
                "<p>CSP 595 - Enterprise Web Application - Assignment#1</p>"+
            "</div>"+
        "</footer>"+
    "</div>"+
"</body>"+
"</html>");
    } 
    
    /** Handles the HTTP <code>GET</code> method.
    * @param request servlet request
    * @param response servlet response
    */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, java.io.IOException {
        processPage(request, response);
    } 

    /** Handles the HTTP <code>POST</code> method.
    * @param request servlet request
    * @param response servlet response
    */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, java.io.IOException {
        processPage(request, response);
    }
}
