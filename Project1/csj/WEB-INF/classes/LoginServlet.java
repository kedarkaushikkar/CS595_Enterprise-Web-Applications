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

public class LoginServlet extends HttpServlet {
   
    protected Map users = new HashMap();
    /** 
     * Initializes the servlet with some usernames/password
    */  
    public void init() {
                users.put("admin", "admin");
                users.put("salesman", "pass");
    }

    /** Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
    * @param request servlet request
    * @param response servlet response
    */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, java.io.IOException {
        String userid = request.getParameter("userid");
        String password = request.getParameter("password");
        int isValidEntry = 0;
        if(userid != null && userid.length() != 0) {
            userid = userid.trim();
        }
        if(password != null && password.length() != 0) {
            password = password.trim();
        }
        if(userid != null &&
            password != null) 
        {
                String realpassword = (String)users.get(userid);
                if(realpassword != null && realpassword.equals(password)) {
                    //showPage(response, "Login Success!");
                    if(realpassword.equals("admin"))
                        response.sendRedirect("/csj/AdminServlet");
                    else if(realpassword.equals("pass"))
                        response.sendRedirect("/csj/SalesmanServlet");
                    else
                        showPage(response, "Admins Failure! Username or password is incorrect");
                } 
                else 
                {
                    ServletContext sc =request.getSession().getServletContext();
                    BufferedReader buffReader = new BufferedReader(new FileReader(sc.getRealPath("logindetails.txt")));

                    String readInput;
                    while((readInput = buffReader.readLine()) != null)
                    {
                        String[] temp = readInput.split(":");
                        String pass[] = temp[1].split(",");

                        if(userid.equals(temp[0]) && password.equals(pass[0]))
                            isValidEntry = 1;
                    }

                    if(isValidEntry == 1)
                        response.sendRedirect("index.html");
                    else
                        showPage(response, "here Failure! Username or password is incorrect");
                }
        }
        else
        {
            showPage(response, "Login Failure!  You must supply a username and password");
        }
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
        processRequest(request, response);
    } 

    /** Handles the HTTP <code>POST</code> method.
    * @param request servlet request
    * @param response servlet response
    */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, java.io.IOException {
        processRequest(request, response);
    }
}
