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
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.DBCursor;
import com.mongodb.ServerAddress;
import com.mongodb.AggregationOutput;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.Date;
import java.util.ArrayList;
import java.util.Iterator;

public class TrendingServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	MongoClient mongo;
	
	public void init() throws ServletException{
      	// Connect to Mongo DB
		mongo = new MongoClient("localhost", 27017);
		
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		response.setContentType("text/html");
		
		PrintWriter output = response.getWriter();
					
		DB db = mongo.getDB("CSP595Tutorial");
		
		// If the collection does not exists, MongoDB will create it for you
		DBCollection myReviews = db.getCollection("custDetailReviews");
		
		BasicDBObject query = new BasicDBObject();
		boolean groupBy	= false;
		boolean countOnly	= false;
		try {
			
			DBCursor dbCursor = null;
			AggregationOutput aggregateData = null;

			DBObject match = null;
			DBObject groupFields = null;
			DBObject group = null;
			DBObject projectFields = null;
			DBObject projectField2 = null;
			DBObject project = null;
			DBObject project2 = null;
			DBObject newGroupFields = null;
			DBObject group2 = null;
			AggregationOutput aggregate = null;



			//Construct the top of the page
			constructPageTop(output);
			
			DBObject fields = new BasicDBObject( "City", "$retailerCity" );
			fields.put( "ProductName", "$productName" );
			
			groupFields = new BasicDBObject( "_id", fields );
			groupFields.put( "count", new BasicDBObject( "$sum", 1 ) );
			groupFields.put("city", new BasicDBObject("$first", "$retailerCity"));
			groupFields.put("product", new BasicDBObject("$first", "$productName"));
			groupFields.put("rating", new BasicDBObject("$first", "$reviewRating"));
			groupFields.put("price", new BasicDBObject("$first", "$productPrice"));
			groupFields.put("category", new BasicDBObject("$first", "$productCategory"));
			groupFields.put("manufacterName", new BasicDBObject("$first", "$manfName"));
			groupFields.put("retailerName", new BasicDBObject("$first", "$retailerName"));

			group = new BasicDBObject( "$group", groupFields );


			match = new BasicDBObject("$match", new BasicDBObject("reviewRating",new BasicDBObject("$eq", 5)));
			
			projectFields = new BasicDBObject("_id", 0);
			projectFields.put("Grouped", "$_id");
			projectFields.put("Review Count", "$count");
			projectFields.put("City", "$city");
			projectFields.put("Product", "$product");
			projectFields.put("Rating", "$rating");
			projectFields.put("Price", "$price");
			projectFields.put("Category", "$category");
			projectFields.put("ManFactName", "$manufacterName");
			projectFields.put("RetailerName", "$retailerName");
			project = new BasicDBObject("$project", projectFields);


			// Count the documents that match
			newGroupFields = new BasicDBObject("_id","$city");
			newGroupFields.put("product", new BasicDBObject("$last", "$product"));
			newGroupFields.put("maxcount", new BasicDBObject("$max", "$count"));
			newGroupFields.put("prodprice", new BasicDBObject("$last", "$price"));
			newGroupFields.put("prodcat", new BasicDBObject("$last", "$category"));
			newGroupFields.put("prodmanName", new BasicDBObject("$last", "$manufacterName"));
			newGroupFields.put("prodretName", new BasicDBObject("$last", "$retailerName"));
			group2 = new BasicDBObject( "$group", newGroupFields );


			projectField2 = new BasicDBObject("_id", 0);
			projectField2.put("Citys", "$_id");
			projectField2.put("prodname", "$product");
			projectField2.put("maximum", "$maxcount");
			projectField2.put("pprice", "$prodprice");
			projectField2.put("pcat", "$prodcat");
			projectField2.put("pManName", "$prodmanName");
			projectField2.put("pRetName", "$prodretName");
			project2 = new BasicDBObject("$project", projectField2);
			// Run aggregation
			//AggregationOutput output = collection.aggregate( group, match, group2 );

			/*groupFields.put("productName", new BasicDBObject("$push", "$productName"));
			groupFields.put("productprice", new BasicDBObject("$push", "$productPrice"));
			groupFields.put("review", new BasicDBObject("$push", "$reviewText"));
			groupFields.put("rating", new BasicDBObject("$push", "$reviewRating"));
			/*
			groupFields.put("productName", new BasicDBObject("$push", "$productName"));
			groupFields.put("productprice", new BasicDBObject("$push", "$productPrice"));
			groupFields.put("review", new BasicDBObject("$push", "$reviewText"));
			groupFields.put("rating", new BasicDBObject("$push", "$reviewRating"));
			*/

			/*DBObject sortFields = new BasicDBObject("Review Count", -1);
    		DBObject sort = new BasicDBObject("$sort", sortFields);*/


    		aggregate = myReviews.aggregate(match,group,group2,project2);
    		//aggregate = myReviews.aggregate(match,group,project2);
    		//aggregate = myReviews.aggregate(match,group,project);
    		constructGroupByCityContent(aggregate, output, countOnly);

    		constructPageBottom(output);
			
		} catch (MongoException e) {
			e.printStackTrace();
		}

	}
	
	public void constructPageTop(PrintWriter output){
		String pageHeading = "Query Result";
		String myPageTop = "<!DOCTYPE html>" + "<html lang=\"en\">"
					+ "<head>	<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />"
					+ "<title>Game Speed</title>"
					+ "<link rel=\"stylesheet\" href=\"styles.css\" type=\"text/css\" />"
					+ "</head>"
					+ "<body>"
					+ "<div id=\"container\">"
					+ "<header>"
					+ "<h1><a href=\"/\">GameSpeed<span></span></a></h1><h2>Tutorial 3</h2>"
					+ "</header>"
					+ "<nav>"
					+ "<ul>"
					+ "<li class=\"\"><a href=\"index.html\">Home</a></li>"
					+ "<li class = \"\"><a href=\"/csj/DataAnalytic\">Data Analytics</a></li>"
					+ "</ul>"
					+ "</nav>"
					+ "<div id=\"body\">"
					+ "<section id=\"review-content\">"
					+ "<article>"
					+ "<h2 style=\"color:#DE2D3A;font-weight:700;\">" +pageHeading + "</h2>";
		
		output.println(myPageTop);		
	}
	
	public void constructPageBottom(PrintWriter output){
		String myPageBottom = "</article>"
					+ "</section>"
                    + "<div class=\"clear\"></div>"
					+ "</div>"
					+ "<footer>"
					+ "<div class=\"footer-content\">"
					+ "<ul>"
                    + "<li>"
                    + "<h4>Dummy Link Section 1</h4>"
                    + "</li>"
                    + "<li><a href=\"#\">Dummy Link 1</a>"
                    + "</li>"
                    + "<li><a href=\"#\">Dummy Link 2</a>"
                    + "</li>"
                    + "<li><a href=\"#\">Dummy Link  3</a>"
                    + "</li>"
					+ "</ul>"
					+ "<div class=\"clear\"></div>"
					+ "</div>"
					+ "<div class=\"footer-bottom\">"
					+ "<p>CSP 595 - Enterprise Web Application - Assignment#3</p>"
					+ "</div>"
					+ "</footer>"
					+ "</div>"
					+ "</body>"
					+ "</html>";
		
		output.println(myPageBottom);
	}
	
	
	
	public void constructGroupByCityContent(AggregationOutput aggregate, PrintWriter output, boolean countOnly){
		int rowCount = 0;
		int productCount = 0;
		String tableData = " ";
		String pageContent = " ";
		
		output.println("<h1> Most Trending Product of Each City </h1>");	
		//output.println(aggregate.results());
		for (DBObject result : aggregate.results()) {
				BasicDBObject bobj = (BasicDBObject) result;

				rowCount++;
				tableData += "<tr>"
						+	"<b>City: "+bobj.getString("Citys")+"</b></br>"
						+	"Product Name: "+bobj.getString("prodname")+"</br>"
						+	"Number of 5 Star Rating: "+bobj.getString("maximum")+"</br>"
						+	"Product Price: "+bobj.getString("pprice")+"</br>"
						+	"Product Category: "+bobj.getString("pcat")+"</br>"
						+	"Manufacture Name: "+bobj.getString("pManName")+"</br>"
						+	"Retailer Name: "+bobj.getString("pRetName")+"</tr></br></br></br>";
				//Reset product count
				productCount =0;
		}	
		pageContent = "<table class = \"query-table\">"+tableData+"</table>";		
	    output.println(pageContent);	
		
		//No data found
		if(rowCount == 0){
			pageContent = "<h1>No Data Found</h1>";
			output.println(pageContent);
		}
		
	}
}