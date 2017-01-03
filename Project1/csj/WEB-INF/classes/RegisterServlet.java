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

public class RegisterServlet extends HttpServlet {

    protected void register(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, java.io.IOException {
         response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        String s1 = request.getParameter("name");
        String s2 = request.getParameter("username");
        String s3 = request.getParameter("password");

        // create your filewriter and bufferedreader
        ServletContext sc = request.getSession().getServletContext();
        File filename = new File(sc.getRealPath("logindetails.txt"));
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

        buffWriter.write(s2+":"+s3+","+s1);
        buffWriter.write("\n");
        buffWriter.close();
        filestream.close();
        showPage(response, s1);
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
            "<h2> Hello " +  message + "</h2>"+
            "<br>"+
            "<a href='login.html'><h2>Sign In<h2></a>"+
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
