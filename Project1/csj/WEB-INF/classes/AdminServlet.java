/*
 * LoginServlet.java
 *
 */
 
import java.io.*;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.*;

public class AdminServlet extends HttpServlet {

    protected void processPage(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, java.io.IOException {
         response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        // create your filewriter and bufferedreader
        ServletContext sc = request.getSession().getServletContext();
        BufferedReader buffReader = new BufferedReader(new FileReader(sc.getRealPath("productdetails.txt")));
        HashMap<String,String> product = new HashMap<String,String>();
        String currentLine;
        String docType = 
        "<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 "+
        "Transitional//EN\">\n";
        out.println(docType + "<html>"+
            "<head>"+
            "<meta http-equiv='Content-Type' content='text/html; charset=utf-8' />"+
            "<title>Game Center - Admin Page</title>"+
            "<link rel='stylesheet' href='styles.css' type='text/css' />"+
            "</head>"+
            "<body>"+
            "<div id='container'>"+
            "<header>"+
            "<h1><a href='http://localhost/csj/index.html'>Game<span>Center</span> Admin Panel</a></h1>"+
            "<h2>Online Store for PC Gamers</h2>"+
            "</header>"+
            "<table>"+
            "<tr>"+
            "<th>ProductID</th>"+
            "<th>Product Name</th>"+
            "<th>Description</th>"+
            "<th>Category</th>"+
            "<th>Manufacter Name</th>"+
            "<th>Price</th>"+
            "<th>Manufacter Rebate</th>"+
            "<th>Retailer Discount</th>"+
            "</tr>");
        while((currentLine =buffReader.readLine())!=null)
        {
            String [] prods = currentLine.split("=");
            String [] prodVars = prods[1].split(",");
            out.println("<tr>");
            out.println("<td>"+prods[0]+"</td>");
            out.println("<td>"+prodVars[0]+"</td>");
            out.println("<td>"+prodVars[1]+"</td>");
            out.println("<td>"+prodVars[2]+"</td>");
            out.println("<td>"+prodVars[3]+"</td>");
            out.println("<td>"+prodVars[4]+"</td>");
            out.println("<td>"+prodVars[5]+"</td>");
            out.println("<td>"+prodVars[6]+"</td>");
            out.println("</tr>");
        }

        buffReader.close();
        out.println(
            "</table>"+
            "<a href='/csj/AddProduct.html'>Click here to add more Products</a>"+
            "</br></br>"+
            "<fieldset>"+
            "<legend>Update Product</legend>"+
            "<form method='post' action='/csj/UpdateProductServlet'>"+
            "<form id='updateProduct' action='/csj/UpdateProductServlet' method='post'>"+
            "<p>Enter the ProductID to be Updated</p>"+
            "<input type='text' name='prodID' id='prodID' maxlength='2'/>"+
            "<label for='updateColumnName' >Update Field:</label>"+
            "<select name='updateColumnName'>"+
            "<option value='Name'>Name</option>"+
            "<option value='Description'>Description</option>"+
            "<option value='Category'>Category</option>"+
            "<option value='ManfName'>Manufacter Name</option>"+
            "<option value='Price'>Price</option>"+
            "<option value='ManfRebate'>Manufacter Rebate</option>"+
            "<option value='RetDiscount'>Retailer Discount</option>"+
            "</select>"+
            "<input type='text' name='updateValue' id='updateValue' maxlength='30'/>"+
            "<input type='submit' name='Submit' value='Submit' />"+
            "</form>"+
            "</fieldset>"+
            "</br></br>"+
            "<fieldset>"+
            "<legend>Delete Product</legend>"+
            "<form id='deleteProduct' action='/csj/DeleteProductServlet' method='post'>"+
            "<p>Enter the ProductID to be Deleted</p>"+
            "<input type='text' name='prodID' id='prodID' maxlength='2' />"+
            "<input type='submit' name='Submit' value='Submit' />"+
            "</form>"+
            "</br></br>"+
            "<a href='/csj/login.html'>Sign Out</a>"+
            "</fieldset>"+
            "<footer>"+
            "<div class='footer-bottom'>"+
            "<p>CSP 595 - Enterprise Web Application - Assignment 1</p>"+
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
