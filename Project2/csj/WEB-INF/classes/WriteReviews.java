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

public class WriteReviews extends HttpServlet {

    protected void processPage(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, java.io.IOException {
         response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        String prodID = request.getParameter("hiddenProdID");
        String prodName = request.getParameter("hiddenProdName");
        String prodDesc = request.getParameter("hiddenProdDesc");
        String prodPrice = request.getParameter("hiddenProdPrice");
        String prodCateg = request.getParameter("hiddenProdCateg");
        String prodZip = request.getParameter("hiddenZip");
        String prodState = request.getParameter("hiddenState");
        String prodCity = request.getParameter("hiddenCity");
        String prodManfName = request.getParameter("hiddenManfName");
        String prodmanfRebate = request.getParameter("hiddenManfRebate");

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
        "<h2>Assignment 2</h2>"+
        "</header>"+
        "<nav>"+
        "<ul>"+
        "<li class='start selected'><a href='index.html'>Home</a></li>"+
        "</ul>"+
        "</nav>"+
        "<div id='body'>"+
        "<section id='content'>"+
        "<article>"+
        "<h1>Product Reviews</h1>"+
        "</article>"+
        "<form method='get' action='/csj/SubmitReview'>"+
        "<input type = 'hidden' name = 'hiddenProdID' value = '"+prodID+"'>"+
        "<input type = 'hidden' name = 'hiddenProdName' value = '"+prodName+"'>"+
        "<input type = 'hidden' name = 'hiddenProdDesc' value = '"+prodDesc+"'>"+
        "<input type = 'hidden' name = 'hiddenProdPrice' value = '"+prodPrice+"'>"+
        "<input type = 'hidden' name = 'hiddenProdCateg' value = '"+prodCateg+"'>"+
        "<input type = 'hidden' name = 'hiddenZip' value = '"+prodZip+"'>"+
        "<input type = 'hidden' name = 'hiddenCity' value = '"+prodCity+"'>"+
        "<input type = 'hidden' name = 'hiddenState' value = '"+prodState+"'>"+
        "<input type = 'hidden' name = 'hiddenManfName' value = '"+prodManfName+"'>"+
        "<input type = 'hidden' name = 'hiddenManfRebate' value = 'yes'>"+
        "<fieldset>"+
        "<legend>Product information:</legend>"+
        "<img src = 'images/GameCenterApp.jpg' width = '200' height = '200' alt = 'X Box Orginal'>"+
        "<table>"+
        "<tr>"+
        "<td> Product Model Name: </td>"+
        "<td>"+prodName+"</td>"+
        "</tr>"+
        "<tr>"+
            "<td> Product Category: </td>"+
            "<td>"+prodCateg+"</td>"+
        "</tr>"+
        "<tr>"+
            "<td> Product Price: </td>"+
            "<td>"+prodPrice+"</td>"+
        "</tr>"+
        "<tr>"+
            "<td> Retailer Name: </td>"+
            "<td>Game Center</td>"+
        "</tr>"+
        "<tr>"+
            "<td> Manufacterer Name: </td>"+
            "<td>"+prodManfName+"</td>"+
        "</tr>"+

        "<tr>"+
            "<td> Retailer City: </td>"+
            "<td> <select name='userCity'>"+
                  "<option value='Chicago'>Chicago</option>"+
                  "<option value='Boston'>Boston</option>"+
                  "<option value='Houston'>Houston</option>"+
                  "<option value='Orlando'>Orlando</option>"+
                  "</select>"+
            "</td>"+
        "</tr>"+

        "<tr>"+
            "<td> Retailer State: </td>"+
            "<td> <input type='text' name='userState'> </td>"+
        "</tr>"+

        "<tr>"+
            "<td> Retailer Zip code: </td>"+
            "<td> <input type='text' name='userZip'> </td>"+
        "</tr>"+

        "<tr>"+
            "<td> Product On Sale: </td>"+
            "<td>"+
                "<input type='radio' name='productOnSale' value='Yes' /> Yes"+
                "<input type='radio' name='productOnSale' value='No' /> No</td>"+
        "</tr>"+

        "</table>"+
        "</fieldset>"+
        "<fieldset>"+
            "<legend>Reviews:</legend>"+
            "<table>"+
                "<tr>"+
                    "<td> User Name: </td>"+
                    "<td> <input type='text' name='userName'> </td>"+
                "</tr>"+
                "<tr>"+
                    "<td> User Age: </td>"+
                    "<td> <input type='text' name='userAge'> </td>"+
                "</tr>"+
                "<tr>"+
                    "<td> User Gender: </td>"+
                    "<td> <select name='userGender'>"+
                          "<option value='Male'>Male</option>"+
                          "<option value='Female'>Female</option>"+
                          "</select>"+
                    "</td>"+
                "</tr>"+
                "<tr>"+
                    "<td> Review Rating: </td>"+
                    "<td>"+
                        "<select name='reviewRating'>"+
                        "<option value='1' selected>1</option>"+
                        "<option value='2'>2</option>"+
                        "<option value='3'>3</option>"+
                        "<option value='4'>4</option>"+
                        "<option value='5'>5</option>"+  
                    "</td>"+
                "</tr>"+
                "<tr>"+
                    "<td> Review Date: </td>"+
                    "<td> <input type='date' name='reviewDate' size=10> </td>"+
                "</tr>"+
                "<tr>"+
                    "<td> Review Text: </td>"+
                    "<td><textarea name='reviewText' rows='4' cols='50'> </textarea></td>"+
                "</tr>"+
            "</table>"+
            "<br><br>"+
            "<input type='submit' value='Submit Review'>"+
        "</fieldset>"+
        "</form>"+
        "</section>"+
            "<div class='clear'></div>"+
            "</div>"+
            "<footer>"+
            "<div class='footer-content'>"+
            "<ul>"+
            "<li><h4>Dummy Link Section 1</h4></li>"+
            "<li><a href='#'>Dummy Link 1</a></li>"+
            "<li><a href='#'>Dummy Link 2</a></li>"+
            "<li><a href='#'>Dummy Link  3</a></li>"+
            "</ul>"+
            "<div class='clear'></div>"+
            "</div>"+
            "<div class='footer-bottom'>"+
            "<p>CSP 595 - Enterprise Web Application - Assignment1</p>"+
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
