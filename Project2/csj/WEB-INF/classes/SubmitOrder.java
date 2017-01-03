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

public class SubmitOrder extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	MongoClient mongo;
	
	public void init() throws ServletException{
      	// Connect to Mongo DB
		mongo = new MongoClient("localhost", 27017);
	}
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
				
		try{
			//Get the values from the form
			String prodIDs = request.getParameter("hiddenProductIDs");
			String prodTotal = request.getParameter("hiddenOrderTotal");
			String firstName = request.getParameter("firstName");
			String lastname = request.getParameter("lastName");
			String address = request.getParameter("address");
			String phoneNumber = request.getParameter("phoneNumber");
			String creditCard = request.getParameter("creditcard");

			DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
	   		//get current date time with Date()
	   		Date date = new Date();
	   		String currDate = dateFormat.format(date);

	   		Calendar cal = Calendar.getInstance();
			cal.setTime(new Date());

	   		cal.add(Calendar.DATE, 14);  // add 14 days
	   		String expDelvDate = dateFormat.format(cal.getTime());

	   		Random r = new Random();
			int low = 10000;
			int high = 99999;
			int orderID = r.nextInt(high-low) + low;
										
			// If database doesn't exists, MongoDB will create it for you
			DB db = mongo.getDB("CSP595Tutorial");
				
			// If the collection does not exists, MongoDB will create it for you
			DBCollection myOrders = db.getCollection("myOrders");
			System.out.println("Collection myOrders selected successfully");
				
			BasicDBObject doc = new BasicDBObject("title", "myOrders").
				append("OrderID", orderID).
				append("ProductID", prodIDs).
				append("FirstName", firstName).
				append("LastName", lastname).
				append("Address", address).
				append("PhoneNumber", phoneNumber).
				append("CreditCard", creditCard).
				append("OrderDate", currDate).
				append("DelvDate", expDelvDate);
			myOrders.insert(doc);
				
			System.out.println("Document inserted successfully");
			
			//Send the response back to the JSP
			PrintWriter out = response.getWriter();
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
                    "<h1><a href='http://localhost/csj/index.html'>Game<span>Center</span></a></h1>"+
                    "<h2>Online Store for PC Gamers</h2>"+
                    "</header>"+
                    "<h1>Order Placed Successfully</h1>"+                         
                    "<fieldset>"+
                    "<legend>Order Details:</legend>"+
                    "<table>"+
                    "<tr>"+
                    "<td>OrderID</td>"+
                    "<td>"+orderID+"</td>"+
                	"</tr>"+
                	"<tr>"+
                    "<td>FirstName</td>"+
                    "<td>"+firstName+"</td>"+
                	"</tr>"+
                	"<tr>"+
                    "<td>LastName</td>"+
                    "<td>"+lastname+"</td>"+
                	"</tr>"+
                	"<tr>"+
                   "<td>Address</td>"+
                    "<td>"+address+"</td>"+
                	"</tr>"+
                	"<tr>"+
                    "<td>Phone Number</td>"+
                    "<td>"+phoneNumber+"</td>"+
                	"</tr>"+
                	"<tr>"+
                    "<td>Order Date</td>"+
                    "<td>"+currDate+"</td>"+
                	"</tr>"+
                	"<tr>"+
                    "<td>Expected Delivery Date</td>"+
                    "<td>"+expDelvDate+"</td>"+
                	"</tr>"+
                	"</table>"+
                	"</fieldset>"+
                	"<a href='index.html'>Go Back To Home Page</a>"+
		            "<footer>"+
		            "<div class='footer-bottom'>"+
		            "<p>CSP 595 - Enterprise Web Application - Assignment 1</p>"+
		            "</div>"+
		            "</footer>"+
		            "</div>"+
		            "</body>"+
		            "</html>"
                );
		
		} catch (MongoException e) {
			e.printStackTrace();
		}
	}
	
	public void destroy()	{
      // do nothing.
	}
	
}