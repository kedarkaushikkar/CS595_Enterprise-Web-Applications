import java.io.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.mongodb.MongoClient;
import com.mongodb.MongoException;
import com.mongodb.WriteConcern;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.DBCursor;
import com.mongodb.ServerAddress;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.Date;
import java.util.Random;
import java.util.Calendar;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class SalesmanServlet extends HttpServlet {
    
    private static final long serialVersionUID = 1L;
    MongoClient mongo;
    
    public void init() throws ServletException{
        // Connect to Mongo DB
        mongo = new MongoClient("localhost", 27017);
    }
    
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        try{

            //Get the values from the form
                                        
            // if database doesn't exists, MongoDB will create it for you
            DB db = mongo.getDB("CSP595Tutorial");
            
            DBCollection myOrders = db.getCollection("myOrders");
            DBCursor cursor = myOrders.find();
            
            PrintWriter out = response.getWriter();

            //out.println(cursor);
            String docType = "<!doctype html>\n";
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
            "<h1><a href='/'>Game<span>Center</span> Salesman Panel</a></h1>"+
            "<h2>Assignment 2</h2>"+
            "</header>"+
            "<nav>"+
            "<ul>"+
            "<li class='start selected'><a href='login.html'>Sign Out</a></li>"+
            "</ul>"+
            "</nav>"+
            "<div id='body'>"+
            "<section id='content'>"+
            "<article>");
                if(cursor.count() == 0){
                    out.println("<h2> No Customer Orders in the Database.</h2></br>");
                }else{
                    out.println("<table>"+
                        "<tr>"+
                        "<th>OrderID</th>"+
                        "<th>Customer Name</th>"+
                        "<th>OrderDate</th>"+
                        "<th>Delivery Date</th>"+
                        "<th>Modify Order</th>"+
                        "</tr>");
                    while(cursor.hasNext()) {
                        BasicDBObject obj = (BasicDBObject) cursor.next();
                        out.println("<tr>"+
                        "<td>"+obj.getString("OrderID")+"</td>"+
                        "<td>"+obj.getString("FirstName")+" "+obj.getString("LastName")+"</td>"+
                        "<td>"+obj.getString("OrderDate")+"</td>"+
                        "<td>"+obj.getString("DelvDate")+"</td>"+
                        "<td>"+
                        "<form class = 'submit-button' method = 'get' action = '/csj/CancelOrder'>"+
                        "<input type = 'hidden' name = 'hiddenOrderID' value = '"+obj.getString("OrderID")+"'>"+
                        "<input class = 'submit-button' type = 'submit' name = 'cancelOrder' value = 'Cancel Order'>"+
                        "</form>"+
                        "</td>"+
                        "</tr>");
                    }
                    out.println("</table>");
                }
            out.println("</article>"+
                "</section>"+
                "<a href='/csj/registration.html'>Create New Customers</a>"+
            "</br></br>"+
                "<a href='/csj/index.html'>Create New Order</a>"+
            "<div class='clear'></div>"+
            "</div>"+
           "<footer>"+
            "<div class='footer-content'>"+
            "<ul>"+
            "<li><h4>Quick Links</h4></li>"+
            "<li><a href='login.html'>Login</a></li>"+
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

        } catch (MongoException e) {
            e.printStackTrace();
        }
    }
    
    public void destroy()   {
      // do nothing.
    }
    
}