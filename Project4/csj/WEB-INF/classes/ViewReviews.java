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

public class ViewReviews extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	MongoClient mongo;
	
	public void init() throws ServletException{
      	// Connect to Mongo DB
		mongo = new MongoClient("localhost", 27017);
	}
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try{
			//Get the values from the form
			String searchField = "productID";
			int productID = Integer.parseInt(request.getParameter("hiddenProdID"));

			//Get the product selected
			int searchParameter = 0;
			if (productID > 0){
			searchParameter = productID;
			}
			
			// if database doesn't exists, MongoDB will create it for you
			DB db = mongo.getDB("CSP595Tutorial");
			
			DBCollection myReviews = db.getCollection("custDetailReviews");
			
			// Find and display 
			BasicDBObject searchQuery = new BasicDBObject();
			searchQuery.put(searchField, searchParameter);

			DBCursor cursor = myReviews.find(searchQuery);
			
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
			out.println("<h1> Reviews For ProductID :"+ searchParameter+ "</h1></article>");
			if(cursor.count() == 0){
				out.println("There are no reviews for this product.");
			}else{
			
				out.println("<table>");
				
				String prodID = "";
				String productName = "";
				String userName = "";
				String reviewRating = "";
				String reviewDate =  "";
				String reviewText = "";
				
				while (cursor.hasNext()) {
					//out.println(cursor.next());
					BasicDBObject obj = (BasicDBObject) cursor.next();				
					
					out.println("<tr>");
					out.println("<td> Item Number: </td>");
					prodID = obj.getString("productID");
					out.println("<td>" +prodID+ "</td>");
					out.println("</tr>");

					out.println("<tr>");
					out.println("<td> Product Name: </td>");
					productName = obj.getString("productName");
					out.println("<td>" +productName+ "</td>");
					out.println("</tr>");
					
					out.println("<tr>");
					out.println("<td> User Name: </td>");
					userName = obj.getString("userName");
					out.println("<td>" +userName+ "</td>");
					out.println("</tr>");
					
					out.println("<tr>");
					out.println("<td> Review Rating: </td>");
					reviewRating = obj.getString("reviewRating").toString();
					out.println("<td>" +reviewRating+ "</td>");
					out.println("</tr>");
					
					out.println("<tr>");
					out.println("<td> Review Date: </td>");
					reviewDate = obj.getString("reviewDate");
					out.println("<td>" +reviewDate+ "</td>");
					out.println("</tr>");
					
					out.println("<tr>");
					out.println("<td> Review Text: </td>");
					reviewText = obj.getString("reviewText");
					out.println("<td>" +reviewText+ "</td>");
					out.println("</tr>");
					
				}
			}	
			out.println("</table>"+
            "<br><br>"+
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
			
		} catch (MongoException e) {
				e.printStackTrace();
		}
	}

	
	public void destroy(){
      // do nothing.
	}
}