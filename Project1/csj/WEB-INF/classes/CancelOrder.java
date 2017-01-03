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

public class CancelOrder extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	MongoClient mongo;
	
	public void init() throws ServletException{
      	// Connect to Mongo DB
		mongo = new MongoClient("localhost", 27017);
	}
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try{

			//Get the values from the form
			String searchField = "OrderID";
			int cancelOrderID = Integer.parseInt(request.getParameter("hiddenOrderID"));
			String orderFound = "";

			//Get the product selected
			int searchParameter = 0;
			if (cancelOrderID > 0){
				searchParameter = cancelOrderID;
			}
										
			// if database doesn't exists, MongoDB will create it for you
			DB db = mongo.getDB("CSP595Tutorial");
			
			DBCollection myOrders = db.getCollection("myOrders");
			
			// Find and display 
			BasicDBObject searchQuery = new BasicDBObject();
			searchQuery.put(searchField, searchParameter);

			DBCursor cursor = myOrders.find(searchQuery);
			
			PrintWriter out = response.getWriter();
			if(cursor.count() == 0)
			{
				orderFound = "NotFound";
			}
			else
			{
				BasicDBObject doc = new BasicDBObject();
				doc.append(searchField,searchParameter);
				myOrders.remove(doc);
				orderFound = "Found";
			}

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
		    "<article>");
				if(orderFound.equals("Found")){
					out.println("<h2> Order ID : "+ searchParameter+ " has been cancelled.</h2></br>"+
						"<a href='trackOrder.html'>Track your Order</a>");
				}else{
					out.println("<h2>No Order Found. Please Enter Correct OrderID.</h2><br>"+
						"<a href='trackOrder.html'>Track your Order</a>"+
						 "<a href='index.html'>Go back to home page</a>");
				}
			out.println("</article>"+
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

		} catch (MongoException e) {
			e.printStackTrace();
		}
	}
	
	public void destroy()	{
      // do nothing.
	}
	
}