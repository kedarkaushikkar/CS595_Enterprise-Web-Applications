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

public class SubmitReview extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	MongoClient mongo;
	
	public void init() throws ServletException{
      	// Connect to Mongo DB
		mongo = new MongoClient("localhost", 27017);
	}
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
				
		try{
			//Get the values from the form
			int productID = Integer.parseInt(request.getParameter("hiddenProdID"));
			String productName = request.getParameter("hiddenProdName");
			String userName = request.getParameter("userName");
			int reviewRating = Integer.parseInt(request.getParameter("reviewRating"));
			int userAge = Integer.parseInt(request.getParameter("userAge"));
			String userGender = request.getParameter("userGender");	
			String reviewDate = request.getParameter("reviewDate");
			String reviewText = request.getParameter("reviewText");

			Double prodPrice = Double.valueOf(request.getParameter("hiddenProdPrice"));
			String prodCategory = request.getParameter("hiddenProdCateg");
			String prodManfName = request.getParameter("hiddenManfName");
			String userCity = request.getParameter("userCity");
			String userZip = request.getParameter("userZip");
			String userState = request.getParameter("userState");
			String retailerName = "GameCenter";
			String retailerZip = request.getParameter("hiddenZip");
			String retailerCity = request.getParameter("hiddenCity");
			String retailerState = request.getParameter("hiddenState");
			String prodOnSale = request.getParameter("productOnSale");
										
			// If database doesn't exists, MongoDB will create it for you
			DB db = mongo.getDB("CSP595Tutorial");
				
			// If the collection does not exists, MongoDB will create it for you
			DBCollection custDetailReviews = db.getCollection("custDetailReviews");
			System.out.println("Collection custDetailReviews selected successfully");
				
			BasicDBObject doc = new BasicDBObject("title", "custDetailReviews").
				append("productID", productID).
				append("productName", productName).
				append("userName", userName).
				append("userAge", userAge).
				append("userGender", userGender).
				append("reviewRating", reviewRating).
				append("reviewDate", reviewDate).
				append("reviewText", reviewText).
				append("productPrice", prodPrice).
				append("productCategory", prodCategory).
				append("productOnSale", prodOnSale).
				append("manfName", prodManfName).
				append("retailerName", retailerName).
				append("retailerCity", userCity).
				append("retailerZip", userZip).
				append("retailerState", userState);
			custDetailReviews.insert(doc);
				
			System.out.println("Document inserted successfully");
			
			//Send the response back to the JSP
			PrintWriter out = response.getWriter();
						
			out.println("<html>");
			out.println("<head> </head>");
			out.println("<body>");
			out.println("<h1> Review submitted for:"+ productName + "</h1>");
			
			out.println("<table>");
			
			out.println("<tr>");
			out.println("<td>");
			out.println("<a href='index.html'> Index </a>");
			out.println("</td>");
			out.println("</tr>");
			
			out.println("<tr>");
			out.println("<td>");
			out.println("<a href='/csj/XboxServlet'> Microsoft </a>");
			out.println("</td>");
			out.println("</tr>");
			
			out.println("<tr>");
			out.println("<td>");
			out.println("<a href='/csj/SonyServlet'> Sony </a>");
			out.println("</td>");
			out.println("</tr>");
			
			out.println("</table>");
			
			out.println("</body>");
			out.println("</html>");
		
		} catch (MongoException e) {
			e.printStackTrace();
		}
	}
	
	public void destroy()	{
      // do nothing.
	}
	
}