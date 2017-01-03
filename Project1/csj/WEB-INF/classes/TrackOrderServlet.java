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
import java.util.*;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.Date;
import java.util.Random;
import java.util.Calendar;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.text.*; 

public class TrackOrderServlet extends HttpServlet {
	
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
			int userOrderID = Integer.parseInt(request.getParameter("trackOrderID"));
			String expDelvDate = "";
			//Get the product selected
			int searchParameter = 0;
			if (userOrderID > 0){
			searchParameter = userOrderID;
			}
			
			// if database doesn't exists, MongoDB will create it for you
			DB db = mongo.getDB("CSP595Tutorial");
			
			DBCollection myOrders = db.getCollection("myOrders");
			
			// Find and display 
			BasicDBObject searchQuery = new BasicDBObject();
			searchQuery.put(searchField, searchParameter);

			DBCursor cursor = myOrders.find(searchQuery);
			
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
				out.println("<h1> Order Details :"+ searchParameter+ "</h1></article>");
				if(cursor.count() == 0){
					out.println("No Order Found. Please Enter Correct OrderID.");
				}else{
				
					out.println("<table>");
					
					String orderID = "";
					String firstName = "";
					String lastName = "";
					String orderDate = "";
					String delvDate =  "";
					
					while (cursor.hasNext()) {
						//out.println(cursor.next());
						BasicDBObject obj = (BasicDBObject) cursor.next();				
						
						orderID = obj.getString("OrderID");
						firstName = obj.getString("FirstName");
						lastName = obj.getString("LastName");
						orderDate = obj.getString("OrderDate");
						expDelvDate = obj.getString("DelvDate");

						out.println("<tr>");
						out.println("<td> OrderID: </td>");
						out.println("<td>" +orderID+ "</td>");
						out.println("</tr>");

						out.println("<tr>");
						out.println("<td> Name: </td>");
						out.println("<td>" +firstName+" "+lastName+"</td>");
						out.println("</tr>");
						
						out.println("<tr>");
						out.println("<td> Order Date: </td>");
						out.println("<td>" +orderDate+ "</td>");
						out.println("</tr>");
					}
				}

				expDelvDate = expDelvDate.replaceAll("/","-"); 	//Delivery Date
				DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		   		//get current date time with Date()
		   		Date date = new Date();
		   		String currDate = dateFormat.format(date); 		//Current Date

		   		Date beginDate = dateFormat.parse(currDate);
				Date endDate = dateFormat.parse(expDelvDate);

				Calendar beginCalendar = Calendar.getInstance();
				beginCalendar.setTime(beginDate);

				Calendar endCalendar = Calendar.getInstance();
				endCalendar.setTime(endDate);
				int minusDays = 0;
				while (true) {
				  minusDays++;

				  // Day increasing by 1
				  beginCalendar.add(Calendar.DAY_OF_MONTH, 1);

				  if (dateFormat.format(beginCalendar.getTime()).equals(dateFormat.format(endCalendar.getTime()))) {
				  	break;
				  }
				}
			int dateDiff = minusDays + 1;
			out.println("</table>");
            if(dateDiff == 0)
            {
            	out.println("<br><br>"+
            	"<b>Order has been Delivered.</b><br>"+
            	"<br>");
            }
            else if(dateDiff < 5)
            {
            	out.println("<br><br>"+
            	"<b>Item has been shipped and will be reach soon.</b><br>"+
            	"<span style='font-size:'6'; color:'red''>Note: Order cannot be cancelled as the estimated delivery date is less than 5 more days</span>"+
            	"<br>");
            }
            else
            {
            	out.println("<br><br>"+
            	"<b>Item will be shipped within "+dateDiff+" Days.</b><br>"+
            	"<span style='font-size:'6'; color:'red''>Note: Order can be cancelled only until 5 days before estimated delivery date</span></br>"+
            	"<form class = 'submit-button' method = 'get' action = '/csj/CancelOrder'>"+
            	"<input type = 'hidden' name = 'hiddenOrderID' value = '"+userOrderID+"'>"+
	            "<input class = 'submit-button' type = 'submit' name = 'cancelOrder' value = 'Cancel Order'>"+
	            "</form>"+
            	"<br>");
            }
            out.println("</section>"+
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
			
		} catch (MongoException | ParseException e) {
				e.printStackTrace();
		}
	}

	public void destroy(){
      // do nothing.
	}
}