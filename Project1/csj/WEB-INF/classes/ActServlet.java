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

public class ActServlet extends HttpServlet {

    protected void processPage(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, java.io.IOException {
         response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        // create your filewriter and bufferedreader
        ServletContext sc = request.getSession().getServletContext();
        BufferedReader buffReader = new BufferedReader(new FileReader(sc.getRealPath("productdetails.txt")));

        ArrayList<ProdDS> listx = new ArrayList<ProdDS>();
        String readInput;
        while((readInput = buffReader.readLine()) != null)
        {
            ProdDS pds = new ProdDS();
            DataManipulation.setData(readInput,pds);
            listx.add(pds);
        }
        

        String docType = 
        "<!doctype html>\n";
        out.println(docType +
                "<html>"+
                "<head>"+
                "<meta http-equiv='Content-Type' content='text/html; charset=utf-8' />"+
                "<title>Game Center</title>"+
                "<link rel='stylesheet' href='styles.css' type='text/css' />"+
                "</head>"+
                "<body>"+
                "<div id='container'>"+
                "<header>"+
                "<h1><a href='/'>Game<span>Center</span></a></h1>"+
                "<h2>Assignment 1</h2>"+
                "</header>"+
                "<nav>"+
                "<ul>"+
                "<li class=''><a href='index.html'>Home</a></li>"+
                "<li class='start selected'><a href='#'>Activision</a></li>"+
                "</ul>"+
                "</nav>"+
                "<img class='header-image' src='images/actlogo.png' width = '100%' height = '100%' alt='X Box' />"+
                "<div id='body'>"+
                "<section id='content'>"+
                "<article>"+
                "<h2>X Box</h2>"+
                "<p>Activision Products and Games available.</p>"+
                "<p>Click on any item to purchase or check the reviews.</p>"+
                "</article>"+
                "<article class='expanded'>"+
                "<h2>EA Products</h2>"+
                "<table>");
        for(ProdDS element : listx)
        {
            if(element.getProdManfName().equals("Activision"))
            {
                String manfRebate = element.getProdManfReb();
                String retDiscount = element.getProdRetDisc();
                double originalPrice = element.getProdPrice();

                double manfDisc = Double.valueOf(manfRebate.replaceAll("%",""));
                double retDisc = Double.valueOf(retDiscount.replaceAll("%",""));
               // double origPrice = Double.valueOf(originalPrice);
                double newPrice = (((100.00 - manfDisc)/100.00) * originalPrice) * ((100.00 - retDisc)/100.00); 
                double manDispric = (((100.00 - manfDisc)/100.00) * originalPrice) ;
                double disMan = (manfDisc/100.00) * originalPrice ; // amount price after manufacturing discount
                double disRetaAm = ((100.00 - manfDisc)/100.00) * originalPrice *(retDisc/100.00) ; // amount price after retail discount
                double newDisc = disMan + disRetaAm ; // total discounted price
                newPrice = Math.round(newPrice*100)/100; //Total price after discount

                double discPercent = ((newDisc/originalPrice) * 100); //Actual Discount Percentage
                int roundedDicPercent = (int) Math.round(discPercent); //Rounded of discounted percentage;

                out.println("<tr>");
                out.println("<td>");
                out.println("<img src = 'images/actprod.png' width = '200' height = '200' alt = 'Activision'>");
                out.println("</td>");
                out.println("<td>");
                out.println("<p> Specifications </p>");
                out.println("<b>Product Name: </b>"+element.getProdName()+"");
                out.println("<b>Description: </b>"+element.getProdDesc()+"</br>");
                out.println("<b>Price: </b>"+element.getProdPrice()+"</br>");
                out.println("<b>Manufacturer: </b>"+element.getProdManfName()+"");
                out.println("<b>Discount: </b><span style='color:green'>"+roundedDicPercent+"% Off</span></br>");
                out.println("<b>Discounted Price: </b>"+newPrice+"</br>");
                out.println("</td>");
                out.println("<td>");
                out.println("<form class = 'submit-button' method = 'get' action = '/csj/AddToCartServlet'>");
                out.println("<input type = 'hidden' name = 'hiddenProdID' value = '"+element.getID()+"'>");
                out.println("<input type = 'hidden' name = 'hiddenProdName' value = '"+element.getProdName()+"'>");
                out.println("<input type = 'hidden' name = 'hiddenProdDesc' value = '"+element.getProdDesc()+"'>");
                out.println("<input type = 'hidden' name = 'hiddenProdPrice' value = '"+newPrice+"'>");
                out.println("<input type = 'hidden' name = 'hiddenProdCateg' value = '"+element.getProdCat()+"'>");
                out.println("<input class = 'submit-button' type = 'submit' name = 'actProds' value = 'Add to Cart'>");
                out.println("</form>");
                out.println("<form id='writeReview' class = 'submit-button' method = 'get' action = '/csj/WriteReviews'>");
                out.println("<input type = 'hidden' name = 'hiddenProdID' value = '"+element.getID()+"'>");
                out.println("<input type = 'hidden' name = 'hiddenProdName' value = '"+element.getProdName()+"'>");
                out.println("<input type = 'hidden' name = 'hiddenProdDesc' value = '"+element.getProdDesc()+"'>");
                out.println("<input type = 'hidden' name = 'hiddenProdPrice' value = '"+newPrice+"'>");
                out.println("<input type = 'hidden' name = 'hiddenProdCateg' value = '"+element.getProdCat()+"'>");
                out.println("<input type = 'hidden' name = 'hiddenZip' value = '60616'>");
                out.println("<input type = 'hidden' name = 'hiddenState' value = 'IL'>");
                out.println("<input type = 'hidden' name = 'hiddenCity' value = 'Chicago'>");
                out.println("<input type = 'hidden' name = 'hiddenManfName' value = '"+element.getProdManfName()+"'>");
                out.println("<input type = 'hidden' name = 'hiddenManfRebate' value = 'yes'>");
                out.println("<input class = 'submit-button' type = 'submit' name = 'actProds' value = 'Write Review'>");
                out.println("</form>");
                out.println("<form class = 'submit-button' method = 'get' action = '/csj/ViewReviews'>");
                out.println("<input type = 'hidden' name = 'hiddenProdID' value = '"+element.getID()+"'>");
                out.println("<input class = 'submit-button' type = 'submit' name = 'actProds' value = 'View Reviews'>");
                out.println("</form>");
                out.println("</td>");
                out.println("</tr>");
            }
        }

        out.println(
            "</table>"+
            "</article>"+
            "</section>"+
            "<div class='clear'></div>"+
            "</div>"+
            "<footer>"+
            "<div class='footer-content'>"+
            "<ul>"+
            "<li><h4>Quick Links</h4></li>"+
            "<li><a href='index.html'>Homepage</a></li>"+
            "</ul>"+
            "<div class='clear'></div>"+
            "</div>"+
            "<div class='footer-bottom'>"+
            "<p>CSP 595 - Enterprise Web Application - Assignment 1</p>"+
            "</div>"+
            "</footer>"+
            "</div>"+
            "</body>"+
            "</html>");
    buffReader.close();
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
