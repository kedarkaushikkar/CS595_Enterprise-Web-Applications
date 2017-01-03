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

public class DeleteCartItemServlet extends HttpServlet {

    protected void processPage(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, java.io.IOException {
         response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        String prodID = request.getParameter("hiddenDelProdID");

        HttpSession session = request.getSession();
        cart shoppingCart;
        shoppingCart = (cart) session.getAttribute("cart");
        shoppingCart.deleteFromCart(prodID);
        session.setAttribute("cart", shoppingCart);
        shoppingCart = (cart) session.getAttribute("cart");


        HashMap<String, List<String>> items = shoppingCart.getCartItems();
        String docType = 
        "<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 "+
        "Transitional//EN\">\n";
        out.println(docType + "<html>"+
            "<head>"+
            "<meta http-equiv='Content-Type' content='text/html; charset=utf-8' />"+
            "<title>Game Center</title>"+
            "<link rel='stylesheet' href='styles.css' type='text/css' />"+
            "</head>"+
            "<body>"+
            "<div id='container'>"+
            "<header>"+
            "<h1><a href='http://localhost/csj/index.html'>Game<span>Center</span></a></h1>"+
            "<h2>Cart Page</h2>"+
            "</header>"+
            "<table>"+
            "<tr>"+
            "<th>ProductID</th>"+
            "<th>Product Name</th>"+
            "<th>Price</th>"+
            "<th>Category</th>"+
            "<th></th>"+
            "</tr>");
            for (HashMap.Entry<String, List<String>> entry : items.entrySet()) {
            String key = entry.getKey();
            List<String> values = entry.getValue();
            out.println("<tr>");
            out.println("<td>"+values.get(0)+"</td>");
            out.println("<td>"+values.get(1)+"</td>");
            out.println("<td>"+values.get(2)+"</td>");
            out.println("<td>"+values.get(3)+"</td>");
            out.println("<td><form method = 'get' action = '/csj/DeleteCartItemServlet'><input type='hidden' name='hiddenDelProdID' value='"+key+"'><input class = 'submit-button' type = 'submit' name = 'deleteButton' value = 'Delete'></form></td>");
            out.println("</tr>");
            }
             out.println(
            "</table>"+
            "<a href='index.html'>Click here to add more Products to Cart</a>"+
            "</br></br>"+
            "<form class = 'submit-button' method = 'get' action = '/csj/Checkout'>"+
            "<input class = 'submit-button' type = 'submit' name = 'XBox_Original' value = 'Proceed To Checkout'>"+
            "</form>"+
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
