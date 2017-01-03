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

public class FindReviews extends HttpServlet {
	
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
				
		try {
			
			// Get the form data
			String productName = request.getParameter("productName");
			String productCat = request.getParameter("productCat");
			String productManf = request.getParameter("productManf");
			int productPrice = Integer.parseInt(request.getParameter("productPrice"));
			String retailerName = request.getParameter("retailerName");
			String retailerZipcode = request.getParameter("retailerZipcode");
			String retailerCity = request.getParameter("retailerCity");
			String retailerState = request.getParameter("retailerState");
			int reviewRating = Integer.parseInt(request.getParameter("reviewRating"));
			String strUserName = request.getParameter("userNameField");
			int strUserAge = Integer.parseInt(request.getParameter("userAgeField"));
			String userReviews = request.getParameter("userReviews").trim();
			
			
			String comparePrice = request.getParameter("comparePrice");
			String compareRating = request.getParameter("compareRating");
			String compareAge = request.getParameter("compareAge");

			String returnValueDropdown = request.getParameter("returnValue");
			String groupByDropdown = request.getParameter("groupByDropdown");
			String topListingDropdown = request.getParameter("topListingDropdown");
			
			//Boolean flags to check the filter settings
			boolean noFilter = false;
			boolean filterByProduct = false;
			boolean filterByCategory = false;
			boolean filterByManf = false;
			boolean filterByPrice = false;
			boolean filterByZip = false;
			boolean filterByCity = false;
			boolean filterByState = false;
			boolean filterByRating = false;
			boolean filterByUserName = false;
			boolean filterByUserAge = false;
			boolean filterByReviews = false;
			
			boolean groupBy = false;
			boolean groupByCity = false;
			boolean groupByProduct = false;
			boolean groupByRetailerName = false;
			boolean groupByZipCode = false;
			boolean groupByManfName = false;
			
			boolean countOnly = false;
			boolean hasMatch = false;

			boolean highlights = false;
			boolean topFiveLiked = false;
			boolean topFiveDisliked = false;
			boolean topFiveLikedExp = false;
			boolean medianPrice = false;
			
						
			//Get the filters selected
			//Filter - Simple Search
			String[] filters = request.getParameterValues("queryCheckBox");
			//Filters - Group By
			String[] extraSettings = request.getParameterValues("extraSettings");

			String[] otherExtraSettings = request.getParameterValues("otherExtraSettings");
			
			DBCursor dbCursor = null;
			AggregationOutput aggregateData = null;

			if(otherExtraSettings != null){
				highlights = true;

				for(int x = 0; x <otherExtraSettings.length; x++){
					switch (otherExtraSettings[x]){						
						case "GROUP_BY_OTHERS":	
							//Can add more grouping conditions here
							if(topListingDropdown.equals("TOP_5_LIKED")){
								topFiveLiked = true;
							}else if(topListingDropdown.equals("TOP_5_DISLIKED")){
								topFiveDisliked = true;
							}else if(topListingDropdown.equals("TOP_5_LIKED_EXPENSIVE")){
								topFiveLikedExp = true;
							}else if(topListingDropdown.equals("TOP_5_LIKED_EXPENSIVE")){
								topFiveLikedExp = true;
							}else if(topListingDropdown.equals("MEDIAN")){
								medianPrice = true;
							}

							break;
					}		
				}
			}

			//Check for extra settings(Grouping Settings)
			if(extraSettings != null){				
				//User has selected extra settings
				groupBy = true;
				
				for(int x = 0; x <extraSettings.length; x++){
					switch (extraSettings[x]){						
						case "COUNT_ONLY":
							//Not implemented functionality to return count only
							countOnly = true;				
							break;
						case "GROUP_BY":	
							//Can add more grouping conditions here
							if(groupByDropdown.equals("GROUP_BY_CITY")){
								groupByCity = true;
							}else if(groupByDropdown.equals("GROUP_BY_PRODUCT")){
								groupByProduct = true;
							}else if(groupByDropdown.equals("GROUP_BY_RETNAME")){
								groupByRetailerName = true;
							}
							else if(groupByDropdown.equals("GROUP_BY_ZIPCODE")){
								groupByZipCode = true;
							}
							else if(groupByDropdown.equals("GROUP_BY_MANFNAME")){
								groupByManfName = true;
							}
							break;
					}		
				}				
			}			
			
			//Check the main filters only if the 'groupBy' option is not selected
//if(filters != null && groupBy != true){
			if(filters != null ){
				for (int i = 0; i < filters.length; i++) {
					//Check what all filters are ON
					//Build the query accordingly
					switch (filters[i]){										
						case "productName":							
							filterByProduct = true;
							if(!productName.equals("ALL_PRODUCTS")){
								query.put("productName", productName);
							}						
							break;

						case "productCateg":							
							filterByCategory = true;
							if(!productCat.equals("ALL_PRODUCTS")){
								query.put("productCategory", productCat);
							}						
							break;

						case "productManf":							
							filterByManf = true;
							if(!productManf.equals("ALL_PRODUCTS")){
								query.put("manfName", productManf);
							}						
							break;

						case "productPrice":
							filterByPrice = true;
							if (comparePrice.equals("EQUALS_TO")) {
								query.put("productPrice", productPrice);
							}else if(comparePrice.equals("GREATER_THAN")){
								query.put("productPrice", new BasicDBObject("$gt", productPrice));
							}else if(comparePrice.equals("LESS_THAN")){
								query.put("productPrice", new BasicDBObject("$lt", productPrice));
							}
							break;
												
						case "retailerZipcode":
							filterByZip = true;
							query.put("retailerZip", retailerZipcode);
							break;
												
						case "retailerCity": 
							filterByCity = true;
							if(!retailerCity.equals("ALL_PRODUCTS") && !groupByCity){
								query.put("retailerCity", retailerCity);
							}							
							break;

						case "retailerState": 
							filterByState = true;
							query.put("retailerState", retailerState);
							break;
												
						case "reviewRating":	
							filterByRating = true;
							if (compareRating.equals("EQUALS_TO")) {
								query.put("reviewRating", reviewRating);
							}else{
								query.put("reviewRating", new BasicDBObject("$gt", reviewRating));
							}
							break;

						case "userName": 
							filterByUserName = true;
							query.put("userName", strUserName);
							break;

						case "userAge":
							filterByUserAge = true;
							if (compareAge.equals("EQUALS_TO")) {
								query.put("userAge", strUserAge);
							}else if(compareAge.equals("GREATER_THAN")){
								query.put("userAge", new BasicDBObject("$gt", strUserAge));
							}else if(compareAge.equals("LESS_THAN")){
								query.put("userAge", new BasicDBObject("$lt", strUserAge));
							}
							break;

						case "userReviews":							
							filterByReviews = true;
							if(!userReviews.equals("")){
								query.put("reviewText", new BasicDBObject("$regex", String.format(".*((?i)%s).*", userReviews)) );
							}						
							break;
													
						default:
							//Show all the reviews if nothing is selected
							noFilter = true;
							break;						
					}				
				}
			}else{
				//Show all the reviews if nothing is selected
				noFilter = true;
			}
			
						
			//Construct the top of the page
			constructPageTop(output);
						
			//Run the query 
			if(groupBy == true && highlights == false){		
				//Run the query using aggregate function
				DBObject match = null;
				DBObject groupFields = null;
				DBObject group = null;
				DBObject projectFields = null;
				DBObject project = null;
				AggregationOutput aggregate = null;
				
				if(groupByCity){

					groupFields = new BasicDBObject("_id", 0);
					groupFields.put("_id", "$retailerCity");
					if(filterByPrice == true)
					{
						if(comparePrice.equals("MAX"))
						{
							groupFields.put("price", new BasicDBObject("$max", "$productPrice"));
						}
						else
						{
							hasMatch = true;
							if(comparePrice.equals("GREATER_THAN"))
							{
								match = new BasicDBObject("$match", new BasicDBObject("productPrice",new BasicDBObject("$gt", productPrice)));
							}
							else if(comparePrice.equals("LESS_THAN"))
							{
								match = new BasicDBObject("$match", new BasicDBObject("productPrice",new BasicDBObject("$lt", productPrice)));	
							}
							else
							{
								match = new BasicDBObject("$match", new BasicDBObject("productPrice",new BasicDBObject("$eq", productPrice)));
							}
						}
					}

					if(filterByRating == true)
					{
						hasMatch = true;
						if(compareRating.equals("GREATER_THAN"))
						{
							match = new BasicDBObject("$match", new BasicDBObject("reviewRating",new BasicDBObject("$gt", reviewRating)));
						}
						else
						{
							match = new BasicDBObject("$match", new BasicDBObject("reviewRating",new BasicDBObject("$eq", reviewRating)));
						}
					}

					groupFields.put("count", new BasicDBObject("$sum", 1));
					groupFields.put("productName", new BasicDBObject("$push", "$productName"));
					groupFields.put("productprice", new BasicDBObject("$push", "$productPrice"));
					groupFields.put("review", new BasicDBObject("$push", "$reviewText"));
					groupFields.put("rating", new BasicDBObject("$push", "$reviewRating"));
					group = new BasicDBObject("$group", groupFields);

					projectFields = new BasicDBObject("_id", 0);
					projectFields.put("City", "$_id");

					if(filterByPrice == true)
					{
						if(comparePrice.equals("MAX"))
						{
							projectFields.put("Max Price","$price");
						}
					}
					projectFields.put("Review Count", "$count");
					projectFields.put("Product", "$productName");
					projectFields.put("Price", "$productprice");
					projectFields.put("User", "$userName");
					projectFields.put("Reviews", "$review");
					projectFields.put("Rating", "$rating");
					
					project = new BasicDBObject("$project", projectFields);

					/*DBObject newGroupFields = new BasicDBObject( "_id", null );
					newGroupFields.put( "count", new BasicDBOject( "$sum", 1 ) );
					newGroupFields.put("maxcount", new BasicDBObject("$max", "$count"));
					DBObject group2 = new BasicDBObject( "$group", newGroupFields );*/
					
					if(hasMatch)
						aggregate = myReviews.aggregate(match,group, project);
					else
						aggregate = myReviews.aggregate(group, project);
												
					//Construct the page content
					constructGroupByCityContent(aggregate, output, countOnly);
					
				}else if(groupByProduct){	

					groupFields = new BasicDBObject("_id", 0);
					groupFields.put("_id", "$productName");
					groupFields.put("count", new BasicDBObject("$sum", 1));
					groupFields.put("review", new BasicDBObject("$push", "$reviewText"));
					groupFields.put("rating", new BasicDBObject("$push", "$reviewRating"));
					
					group = new BasicDBObject("$group", groupFields);

					projectFields = new BasicDBObject("_id", 0);
					projectFields.put("Product", "$_id");
					projectFields.put("Review Count", "$count");
					projectFields.put("Reviews", "$review");
					projectFields.put("Rating", "$rating");
										
					project = new BasicDBObject("$project", projectFields);
					
					aggregate = myReviews.aggregate(group, project);				
							
					//Construct the page content
					constructGroupByProductContent(aggregate, output, countOnly);
					
				}else if(groupByRetailerName){	

					groupFields = new BasicDBObject("_id", 0);
					groupFields.put("_id", "$retailerName");
					groupFields.put("count", new BasicDBObject("$sum", 1));
					groupFields.put("productName", new BasicDBObject("$push", "$productName"));
					groupFields.put("review", new BasicDBObject("$push", "$reviewText"));
					groupFields.put("rating", new BasicDBObject("$push", "$reviewRating"));
					
					group = new BasicDBObject("$group", groupFields);

					projectFields = new BasicDBObject("_id", 0);
					projectFields.put("Retailer Name", "$_id");
					projectFields.put("Review Count", "$count");
					projectFields.put("Product", "$productName");
					projectFields.put("Reviews", "$review");
					projectFields.put("Rating", "$rating");
										
					project = new BasicDBObject("$project", projectFields);
					
					aggregate = myReviews.aggregate(group, project);				
							
					//Construct the page content
					constructGroupByRetailerContent(aggregate, output, countOnly);
					
				}else if(groupByManfName){	

					groupFields = new BasicDBObject("_id", 0);
					groupFields.put("_id", "$manfName");
					groupFields.put("count", new BasicDBObject("$sum", 1));
					groupFields.put("productName", new BasicDBObject("$push", "$productName"));
					groupFields.put("review", new BasicDBObject("$push", "$reviewText"));
					groupFields.put("rating", new BasicDBObject("$push", "$reviewRating"));
					
					group = new BasicDBObject("$group", groupFields);

					projectFields = new BasicDBObject("_id", 0);
					projectFields.put("Manfacter Name", "$_id");
					projectFields.put("Review Count", "$count");
					projectFields.put("Product", "$productName");
					projectFields.put("Reviews", "$review");
					projectFields.put("Rating", "$rating");
										
					project = new BasicDBObject("$project", projectFields);
					
					aggregate = myReviews.aggregate(group, project);				
							
					//Construct the page content
					constructGroupByManFactContent(aggregate, output, countOnly);
					
				}else if(groupByZipCode){
					groupFields = new BasicDBObject("_id", 0);
					groupFields.put("_id", "$retailerZip");
					if(filterByPrice == true)
					{
						if(comparePrice.equals("MAX"))
						{
							groupFields.put("price", new BasicDBObject("$max", "$productPrice"));
						}
						else
						{
							hasMatch = true;
							if(comparePrice.equals("GREATER_THAN"))
							{
								match = new BasicDBObject("$match", new BasicDBObject("productPrice",new BasicDBObject("$gt", productPrice)));
							}
							else if(comparePrice.equals("LESS_THAN"))
							{
								match = new BasicDBObject("$match", new BasicDBObject("productPrice",new BasicDBObject("$lt", productPrice)));	
							}
							else
							{
								match = new BasicDBObject("$match", new BasicDBObject("productPrice",new BasicDBObject("$eq", productPrice)));
							}
						}
					}
					if(filterByRating == true)
					{
						hasMatch = true;
						if(compareRating.equals("GREATER_THAN"))
						{
							match = new BasicDBObject("$match", new BasicDBObject("reviewRating",new BasicDBObject("$gt", reviewRating)));
						}
						else
						{
							match = new BasicDBObject("$match", new BasicDBObject("reviewRating",new BasicDBObject("$eq", reviewRating)));
						}
					}
					groupFields.put("count", new BasicDBObject("$sum", 1));
					groupFields.put("productName", new BasicDBObject("$push", "$productName"));
					groupFields.put("productprice", new BasicDBObject("$push", "$productPrice"));
					groupFields.put("review", new BasicDBObject("$push", "$reviewText"));
					groupFields.put("rating", new BasicDBObject("$push", "$reviewRating"));
					
					group = new BasicDBObject("$group", groupFields);

					projectFields = new BasicDBObject("_id", 0);
					projectFields.put("Retailer Zip", "$_id");
					if(filterByPrice == true)
					{
						if(comparePrice.equals("MAX"))
						{
							projectFields.put("Max Price","$price");
						}
					}
					projectFields.put("Review Count", "$count");
					projectFields.put("Product", "$productName");
					projectFields.put("Price", "$productprice");
					projectFields.put("Reviews", "$review");
					projectFields.put("Rating", "$rating");
					
					project = new BasicDBObject("$project", projectFields);
					
//aggregate = myReviews.aggregate(match,group, project);
					if(hasMatch)
						aggregate = myReviews.aggregate(match,group, project);
					else
						aggregate = myReviews.aggregate(group, project);
												
					//Construct the page content
					constructGroupByZipCodeContent(aggregate, output, countOnly);
				}		 
			}else if(groupBy == true && highlights == true){
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
				String groupVariable = "";
				String text = "";

				if(groupByCity == true){
					groupVariable = "$retailerCity";
					text = "City";
				}
				else if(groupByZipCode == true){
					groupVariable = "$retailerZip";
					text = "ZipCode";
				}
				else{
					groupVariable = "$retailerCity";
					text = "City";
				}
				if(topFiveLiked){

					groupFields = new BasicDBObject( "_id", 0 );
					groupFields.put( "_id", groupVariable);
					groupFields.put( "count", new BasicDBObject( "$sum", 1 ) );
					groupFields.put("product", new BasicDBObject("$push", "$productName"));
					groupFields.put("reviews", new BasicDBObject("$push", "$reviewRating"));
					group = new BasicDBObject( "$group", groupFields );

					DBObject sortFields = new BasicDBObject("reviewRating", -1);
		    		DBObject sort = new BasicDBObject("$sort", sortFields );

					projectFields = new BasicDBObject("_id", 0);
					projectFields.put(text, "$_id");
					projectFields.put("Review Count", "$count");
					projectFields.put("ProductName", "$product");
					projectFields.put("Reviews", "$reviews");
					project = new BasicDBObject("$project", projectFields);

		    		aggregate = myReviews.aggregate(sort,group,project);
		    		
		    		constructGroupByTopFiveContent(aggregate, output, text);
				}

				if(medianPrice){

					groupFields = new BasicDBObject( "_id", 0 );
					groupFields.put( "_id", "$retailerCity");
					groupFields.put( "count", new BasicDBObject( "$sum", 1 ) );
					groupFields.put("product", new BasicDBObject("$push", "$productName"));
					groupFields.put("price", new BasicDBObject("$push", "$productPrice"));
					group = new BasicDBObject( "$group", groupFields );

					DBObject sortFields = new BasicDBObject("productPrice", 1);
		    		DBObject sort = new BasicDBObject("$sort", sortFields );

					projectFields = new BasicDBObject("_id", 0);
					projectFields.put("retailer city", "$_id");
					projectFields.put("Review Count", "$count");
					projectFields.put("ProductName", "$product");
					projectFields.put("Price", "$price");
					project = new BasicDBObject("$project", projectFields);

		    		aggregate = myReviews.aggregate(sort,group,project);
		    		
		    		constructGroupByMedianContent(aggregate, output, countOnly);
				}


			}else{
				//Check the return value selected
				int returnLimit = 0;
				
				//Create sort variable
				DBObject sort = new BasicDBObject();
												
				if (returnValueDropdown.equals("TOP_5")){
					//Top 5 - Sorted by review rating
					returnLimit = 5;
					sort.put("reviewRating",-1);
					dbCursor = myReviews.find(query).limit(returnLimit).sort(sort);
				}else if (returnValueDropdown.equals("TOP_10")){
					//Top 10 - Sorted by review rating
					returnLimit = 10;
					sort.put("reviewRating",-1);
					dbCursor = myReviews.find(query).limit(returnLimit).sort(sort);
				}else if (returnValueDropdown.equals("LATEST_5")){
					//Latest 5 - Sort by date
					returnLimit = 5;
					sort.put("reviewDate",-1);
					dbCursor = myReviews.find(query).limit(returnLimit).sort(sort);
				}else if (returnValueDropdown.equals("LATEST_10")){
					//Latest 10 - Sort by date
					returnLimit = 10;
					sort.put("reviewDate",-1);
					dbCursor = myReviews.find(query).limit(returnLimit).sort(sort);
				}else{
					//Run the simple search query(default result)
					dbCursor = myReviews.find(query);
				}		
				
				//Construct the page content
				constructDefaultContent(dbCursor, output, countOnly);
			}			
			
			//Construct the bottom of the page
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
	
	public void constructDefaultContent(DBCursor dbCursor, PrintWriter output, boolean countOnly){
		int count = 0;
		String tableData = " ";
		String pageContent = " ";

		while (dbCursor.hasNext()) {		
			BasicDBObject bobj = (BasicDBObject) dbCursor.next();
			tableData =  "<tr><td>Product Name: <b>     " + bobj.getString("productName") + " </b></td></tr>"
						+ "<tr><td>Product Category:       " + bobj.getString("productCategory") + "</br>"
						+ "Price:       		" + bobj.getInt("productPrice") + "</br>"
						+ "Retailer:            " + bobj.getString("retailerName") + "</br>"
						+ "Retailer Zipcode:    " + bobj.getString("retailerZip") + "</br>"
						+ "Retailer City:       " + bobj.getString("retailerCity") + "</br>"
						+ "Retailer State:      " + bobj.getString("retailerState") + "</br>"
						+ "Sale:                " + bobj.getString("productOnSale") + "</br>"
						+ "User ID:             " + bobj.getString("userName") + "</br>"
						+ "User Age:            " + bobj.getString("userAge") + "</br>"
						+ "User Gender:         " + bobj.getString("userGender") + "</br>"
						+ "Manufacturer:        " + bobj.getString("manfName") + "</br>"
						+ "Rating:              " + bobj.getString("reviewRating") + "</br>"
						+ "Date:                " + bobj.getString("reviewDate") + "</br>"
						+ "Review Text:         " + bobj.getString("reviewText") + "</td></tr>";
				
			count++;
				
				output.println("<h3>"+count+"</h3>");
				pageContent = "<table class = \"query-table\">"+tableData+"</table>";		
			    output.println(pageContent);
		}

		//No data found
		if(count == 0){
			pageContent = "<h1>No Data Found</h1>";
			output.println(pageContent);
		}
		
	}
	
	public void constructGroupByCityContent(AggregationOutput aggregate, PrintWriter output, boolean countOnly){
		int rowCount = 0;
		int productCount = 0;
		String tableData = " ";
		String pageContent = " ";
		
		output.println("<h1> Grouped By - City </h1>");		
		for (DBObject result : aggregate.results()) {
				BasicDBObject bobj = (BasicDBObject) result;
				BasicDBList productList = (BasicDBList) bobj.get("Product");
				BasicDBList productReview = (BasicDBList) bobj.get("Reviews");
				BasicDBList rating = (BasicDBList) bobj.get("Rating");
				BasicDBList price = (BasicDBList) bobj.get("Price");
				
				rowCount++;
				tableData = "<tr><td>City: "+bobj.getString("City")+"</td>&nbsp"
						+	"<td>Reviews Found: "+bobj.getString("Review Count")+"</td>"
						+	"<td>Max Price: "+bobj.getString("Max Price")+"</td></tr>";
				
				pageContent = "<table class = \"query-table\">"+tableData+"</table>";		
			    output.println(pageContent);
				
				//Now print the products with the given review rating
				while (productCount < productList.size()) {
					tableData = "<tr rowspan = \"3\"><td> Product: "+productList.get(productCount)+"</br>"
							+   "Rating: "+rating.get(productCount)+"</br>"
							+	"Review: "+productReview.get(productCount)+"</br>"
							+ 	"Price: "+price.get(productCount)+"</td></tr>";
												
					pageContent = "<table class = \"query-table\">"+tableData+"</table>";		
					output.println(pageContent);
					
					productCount++;					
				}	
				
				//Reset product count
				productCount =0;
		}		
		
		//No data found
		if(rowCount == 0){
			pageContent = "<h1>No Data Found</h1>";
			output.println(pageContent);
		}
		
	}
	
	public void constructGroupByProductContent(AggregationOutput aggregate, PrintWriter output, boolean countOnly){
		int rowCount = 0;
		int reviewCount = 0;
		String tableData = " ";
		String pageContent = " ";
				
		output.println("<h1> Grouped By - Products </h1>");
		for (DBObject result : aggregate.results()) {
				BasicDBObject bobj = (BasicDBObject) result;
				BasicDBList productReview = (BasicDBList) bobj.get("Reviews");
				BasicDBList rating = (BasicDBList) bobj.get("Rating");
				
				rowCount++;
				tableData = "<tr><td>Product: "+bobj.getString("Product")+"</td>&nbsp"
						+	"<td>Reviews Found: "+bobj.getString("Review Count")+"</td></tr>";
				
				pageContent = "<table class = \"query-table\">"+tableData+"</table>";		
			    output.println(pageContent);
				
				//Now print the products with the given review rating
				while (reviewCount < productReview.size()) {
					tableData = "<tr rowspan = \"3\"><td>Rating: "+rating.get(reviewCount)+"</br>"
								+ "Review: "+productReview.get(reviewCount)+"</td></tr>";
							
					pageContent = "<table class = \"query-table\">"+tableData+"</table>";		
					output.println(pageContent);
					
					reviewCount++;					
				}
				//No data found
				if(rowCount == 0){
					pageContent = "<h1>No Data Found</h1>";
					output.println(pageContent);
				}
		}
	}

	public void constructGroupByRetailerContent(AggregationOutput aggregate, PrintWriter output, boolean countOnly){
		int rowCount = 0;
		int reviewCount = 0;
		String tableData = " ";
		String pageContent = " ";
				
		output.println("<h1> Grouped By - Retailer </h1>");
		for (DBObject result : aggregate.results()) {
				BasicDBObject bobj = (BasicDBObject) result;
				BasicDBList productReview = (BasicDBList) bobj.get("Reviews");
				BasicDBList rating = (BasicDBList) bobj.get("Rating");
				BasicDBList productName = (BasicDBList) bobj.get("Product");

				rowCount++;
				tableData = "<tr><td>Retailer Name: "+bobj.getString("Retailer Name")+"</td>&nbsp"
						+	"<td>Reviews Found: "+bobj.getString("Review Count")+"</td></tr>";
				
				pageContent = "<table class = \"query-table\">"+tableData+"</table>";		
			    output.println(pageContent);
				
				//Now print the products with the given review rating
				while (reviewCount < productReview.size()) {
					tableData = "<tr rowspan = \"3\"><td>Rating: "+rating.get(reviewCount)+"</br>"
								+ "Review: "+productReview.get(reviewCount)+"</td></tr>";
							
					pageContent = "<table class = \"query-table\">"+tableData+"</table>";		
					output.println(pageContent);
					
					reviewCount++;					
				}
					
				//Reset review count
				reviewCount = 0;
					
		}		
		
		//No data found
		if(rowCount == 0){
			pageContent = "<h1>No Data Found</h1>";
			output.println(pageContent);
		}
		
	}

	public void constructGroupByZipCodeContent(AggregationOutput aggregate, PrintWriter output, boolean countOnly){
		int rowCount = 0;
		int productCount = 0;
		String tableData = " ";
		String pageContent = " ";
		
		output.println("<h1> Grouped By - ZipCode </h1>");		
		for (DBObject result : aggregate.results()) {
				BasicDBObject bobj = (BasicDBObject) result;
				BasicDBList productList = (BasicDBList) bobj.get("Product");
				BasicDBList productReview = (BasicDBList) bobj.get("Reviews");
				BasicDBList rating = (BasicDBList) bobj.get("Rating");
				BasicDBList price = (BasicDBList) bobj.get("Price");
				
				rowCount++;
				tableData = "<tr><td>Zip Code: "+bobj.getString("Retailer Zip")+"</td>&nbsp"
						+	"<td>Reviews Found: "+bobj.getString("Review Count")+"</td>"
						+	"<td>Max Price: "+bobj.getString("Max Price")+"</td></tr>";
				
				pageContent = "<table class = \"query-table\">"+tableData+"</table>";		
			    output.println(pageContent);
				
				//Now print the products with the given review rating
				while (productCount < productList.size()) {
					tableData = "<tr rowspan = \"3\"><td> Product: "+productList.get(productCount)+"</br>"
							+   "Rating: "+rating.get(productCount)+"</br>"
							+	"Review: "+productReview.get(productCount)+"</br>"
							+ 	"Price: "+price.get(productCount)+"</td></tr>";
												
					pageContent = "<table class = \"query-table\">"+tableData+"</table>";		
					output.println(pageContent);
					
					productCount++;					
				}	
				
				//Reset product count
				productCount =0;
		}		
		
		//No data found
		if(rowCount == 0){
			pageContent = "<h1>No Data Found</h1>";
			output.println(pageContent);
		}

	}

	public void constructGroupByTopFiveContent(AggregationOutput aggregate, PrintWriter output, String label){
		int rowCount = 0;
		int productCount = 0;
		String tableData = " ";
		String pageContent = " ";
		
		output.println("<h1> Top 5 most liked product of every "+label+" </h1>");		
		for (DBObject result : aggregate.results()) {
				BasicDBObject bobj = (BasicDBObject) result;
				BasicDBList productList = (BasicDBList) bobj.get("ProductName");
				BasicDBList rating = (BasicDBList) bobj.get("Reviews");
				
				rowCount++;
				tableData = "<tr><td>"+label+": "+bobj.getString(label)+"</td>&nbsp"
						+	"<td>Reviews Found: "+bobj.getString("Review Count")+"</td>"
						+	"</tr>";
				
				pageContent = "<table class = \"query-table\">"+tableData+"</table>";		
			    output.println(pageContent);
				
				//Now print the products with the given review rating
				int tempLimit = productList.size();
				int limit = 0;
				if (tempLimit > 5)
					limit = 5;
				else
					limit = productList.size();
				while (productCount < limit) {
					tableData = "<tr rowspan = \"3\"><td> Product: "+productList.get(productCount)+"</br>"
							+   "Rating: "+rating.get(productCount)+"</br></tr>";
												
					pageContent = "<table class = \"query-table\">"+tableData+"</table>";		
					output.println(pageContent);
					
					productCount++;					
				}	
				
				//Reset product count
				productCount =0;
		}		
		
		//No data found
		if(rowCount == 0){
			pageContent = "<h1>No Data Found</h1>";
			output.println(pageContent);
		}

	}

	public void constructGroupByManFactContent(AggregationOutput aggregate, PrintWriter output, boolean countOnly){
		int rowCount = 0;
		int reviewCount = 0;
		String tableData = " ";
		String pageContent = " ";
				
		output.println("<h1> Grouped By - Retailer </h1>");
		for (DBObject result : aggregate.results()) {
				BasicDBObject bobj = (BasicDBObject) result;
				BasicDBList productReview = (BasicDBList) bobj.get("Reviews");
				BasicDBList rating = (BasicDBList) bobj.get("Rating");
				BasicDBList productName = (BasicDBList) bobj.get("Product");

				rowCount++;
				tableData = "<tr><td>Manfacter Name: "+bobj.getString("Manfacter Name")+"</td>&nbsp"
						+	"<td>Reviews Found: "+bobj.getString("Review Count")+"</td></tr>";
				
				pageContent = "<table class = \"query-table\">"+tableData+"</table>";		
			    output.println(pageContent);
				
				//Now print the products with the given review rating
				while (reviewCount < productReview.size()) {
					tableData = "<tr rowspan = \"3\"><td>Rating: "+rating.get(reviewCount)+"</br>"
								+ "Review: "+productReview.get(reviewCount)+"</td></tr>";
							
					pageContent = "<table class = \"query-table\">"+tableData+"</table>";		
					output.println(pageContent);
					
					reviewCount++;					
				}
					
				//Reset review count
				reviewCount = 0;
					
		}		
		
		//No data found
		if(rowCount == 0){
			pageContent = "<h1>No Data Found</h1>";
			output.println(pageContent);
		}

	}

	public void constructGroupByMedianContent(AggregationOutput aggregate, PrintWriter output, boolean countOnly){
		int rowCount = 0;
		int productCount = 0;
		String tableData = " ";
		String pageContent = " ";
		double median = 0;
		//int tempLength = 0;
		
		output.println("<h1> Median Price of each City </h1>");		
		for (DBObject result : aggregate.results()) {
				BasicDBObject bobj = (BasicDBObject) result;
				BasicDBList productList = (BasicDBList) bobj.get("ProductName");
				BasicDBList productPrice = (BasicDBList) bobj.get("Price");
				
				rowCount++;

				int tempLength = productPrice.size();
				if (tempLength % 2 == 0)
				    median = ((double)productPrice.get(tempLength/2) + (double)productPrice.get((tempLength/2) - 1))/2;
				else
				    median = (double) productPrice.get(tempLength/2);

				tableData = "<tr><td>City: "+bobj.getString("retailer city")+"</td>&nbsp"
						+	"<td>Median: "+median+"</td>"
						+	"</tr>";
				
				pageContent = "<table class = \"query-table\">"+tableData+"</table>";		
			    output.println(pageContent);

				//Now print the products with the given review rating
				while (productCount < productList.size()) {
					tableData = "<tr rowspan = \"3\"><td> Product: "+productList.get(productCount)+"</br>"
							+   "Price: "+productPrice.get(productCount)+"</br></tr>";
												
					pageContent = "<table class = \"query-table\">"+tableData+"</table>";		
					output.println(pageContent);
					
					productCount++;					
				}	
				
				//Reset product count
				productCount =0;
		}		
		
		//No data found
		if(rowCount == 0){
			pageContent = "<h1>No Data Found</h1>";
			output.println(pageContent);
		}
	}

}