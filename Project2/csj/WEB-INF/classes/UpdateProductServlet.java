/*
 * LoginServlet.java
 *
 */
 
import java.io.*;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.*;

public class UpdateProductServlet extends HttpServlet 
{

    protected void updateProduct(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, java.io.IOException 
    {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        String userProdID = request.getParameter("prodID");
        String userUpdateKey = request.getParameter("updateColumnName");
        String userUpdateValue = request.getParameter("updateValue");
        if(userProdID != null && userProdID.length() != 0) 
        {
            userProdID = userProdID.trim();
        }
        // create your filewriter and bufferedreader
        ServletContext sc =request.getSession().getServletContext();
        BufferedReader buffReader = new BufferedReader(new FileReader(sc.getRealPath("productdetails.txt")));

        ArrayList<ProdDS> elementList = new ArrayList<ProdDS>();
        String readInput;
        while((readInput = buffReader.readLine()) != null)
        {
            ProdDS pds = new ProdDS();
            DataManipulation.setData(readInput,pds);
            elementList.add(pds);
        }
        buffReader.close();

        for(ProdDS element : elementList)
        {
            if(element.getID() == Integer.valueOf(userProdID))
            {
                if(userUpdateKey.equals("Name"))
                    element.setProdName(userUpdateValue);

                if(userUpdateKey.equals("Description"))
                    element.setProdDesc(userUpdateValue);

                if(userUpdateKey.equals("Category"))
                    element.setProdCat(userUpdateValue);

                if(userUpdateKey.equals("ManfName"))
                    element.setProdManfName(userUpdateValue);

                if(userUpdateKey.equals("Price"))
                    element.setProdPrice(Double.valueOf(userUpdateValue));

                if(userUpdateKey.equals("ManfRebate"))
                    element.setProdManfReb(userUpdateValue);

                if(userUpdateKey.equals("RetDiscount"))
                    element.setProdRetDisc(userUpdateValue);
            }
        }

        PrintWriter writer = new PrintWriter(sc.getRealPath("productdetails.txt"));

        for(ProdDS pds : elementList){
            String line = convertDS2String(pds);
            writer.print(line);
            writer.print("\n");
        }
        writer.close();
        showPage(response, "Product has been Updated successfully");
    }

    public static String convertDS2String(ProdDS pds)
    {
        StringBuffer sb = new StringBuffer();
        sb.append(pds.getID());
        sb.append("=");
        sb.append(pds.getProdName());
        sb.append(",");
        sb.append(pds.getProdDesc());
        sb.append(",");
        sb.append(pds.getProdCat());
        sb.append(",");
        sb.append(pds.getProdManfName());
        sb.append(",");
        sb.append(pds.getProdPrice());
        sb.append(",");
        sb.append(pds.getProdManfReb());
        sb.append(",");
        sb.append(pds.getProdRetDisc());
        return sb.toString();
    }

    /**
     * Actually shows the <code>HTML</code> result page
     */
    protected void showPage(HttpServletResponse response, String message)
    throws ServletException, java.io.IOException {
        response.setContentType("text/html");
        java.io.PrintWriter out = response.getWriter();
        String docType = 
        "<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 "+
        "Transitional//EN\">\n";
        out.println(docType + "<html>"+
            "<head>"+
            "<meta http-equiv='Content-Type' content='text/html; charset=utf-8' />"+
            "<title>Game Center - Login</title>"+
            "<link rel='stylesheet' href='styles.css' type='text/css' />"+
            "</head>"+
            "<body>"+
            "<div id='container'>"+
            "<header>"+
            "<h1><a href='http://localhost/csj/index.html'>CSP 595<span>Assignment 2</span></a></h1>"+
            "<h2>This is Assignment 1 for CSP 595</h2>"+
            "</header>"+
            "<h2>" + message + "</h2>"+
            "<br>"+
            "<a href='/csj/AdminServlet'><h2>Click here to see the products<h2></a>"+
            "<footer>"+
            "<div class='footer-bottom'>"+
            "<p>CSP 595 - Enterprise Web Application - Assignment 1</p>"+
            "</div>"+
            "</footer>"+
            "</div>"+
            "</body>"+
            "</html>");
        out.close();
    }

    
    /** Handles the HTTP <code>GET</code> method.
    * @param request servlet request
    * @param response servlet response
    */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
    {
        updateProduct(request, response);
    } 

    /** Handles the HTTP <code>POST</code> method.
    * @param request servlet request
    * @param response servlet response
    */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        updateProduct(request, response);
    }
}
