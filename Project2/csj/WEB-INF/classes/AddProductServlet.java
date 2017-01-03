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

public class AddProductServlet extends HttpServlet {

    protected void register(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, java.io.IOException {
         response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        String s1 = request.getParameter("id");
        String s2 = request.getParameter("prodName");
        String s3 = request.getParameter("prodDesc");
        String s4 = request.getParameter("prodComp");
        String s5 = request.getParameter("ManfName");
        String s6 = request.getParameter("prodprice");
        s6 = s6 + ".0";
        String s7 = request.getParameter("manfReb");
        String s8 = request.getParameter("retDisc");

        // create your filewriter and bufferedreader
        ServletContext sc = request.getSession().getServletContext();
        File filename = new File(sc.getRealPath("productdetails.txt"));
        FileWriter filestream = new FileWriter(filename,true);
        BufferedWriter buffWriter = new BufferedWriter(filestream);

        if(s1 != null && s1.length() != 0) {
            s1 = s1.trim();
        }
        if(s2 != null && s2.length() != 0) {
            s2 = s2.trim();
        }
        if(s3 != null && s3.length() != 0) {
            s3 = s3.trim();
        }
         if(s4 != null && s4.length() != 0) {
            s4 = s4.trim();
        }
         if(s5 != null && s5.length() != 0) {
            s5 = s5.trim();
        }
         if(s6 != null && s6.length() != 0) {
            s6 = s6.trim();
        }
        if(s7 != null && s7.length() != 0) {
            s7 = s7.trim();
        }
        if(s8 != null && s8.length() != 0) {
            s8 = s8.trim();
        }

        buffWriter.write(s1+"="+s2+","+s3+","+s4+","+s5+","+s6+","+s7+","+s8);
        buffWriter.write("\n");
        buffWriter.close();
        filestream.close();
        showPage(response, "Product has been added successfully");
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
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, java.io.IOException {
        register(request, response);
    } 

    /** Handles the HTTP <code>POST</code> method.
    * @param request servlet request
    * @param response servlet response
    */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, java.io.IOException {
        register(request, response);
    }
}
