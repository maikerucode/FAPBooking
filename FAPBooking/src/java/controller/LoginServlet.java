
package controller;

import java.io.IOException;
import java.sql.Connection;
import javax.servlet.*;
import javax.servlet.http.*;
import model.*;
import nl.captcha.Captcha;

/**
 *
 * @author star
 */
public class LoginServlet extends HttpServlet {
    
    private Connection conn;
    private String key;
    private String cipher;
    private UserManager um;
    
    @Override
    public void init(ServletConfig config) throws ServletException {
        
        super.init(config);
        
        String driver = config.getInitParameter("jdbcClassName");
        String username = config.getInitParameter("dbUserName");
        String password = config.getInitParameter("dbPassword");
        
        String driverUrl = config.getInitParameter("jdbcDriverURL");
        String hostname = getInitParameter("dbHostName");
        String port = config.getInitParameter("dbPort");
        String database = config.getInitParameter("databaseName");
        
        //Debugging
        System.out.println("driver: " + driver);
        System.out.println("usernamae: " + username);
        System.out.println("password: " + driver);
        
        System.out.println("driverUrl: " + driverUrl);
        System.out.println("hostname: " + hostname);
        System.out.println("port: " + port);
        System.out.println("database: " + database);
        
        ConnectionManager cm = new ConnectionManager();
        conn = cm.establishConn(driver, username, password, driverUrl, hostname, port, database);
        
        ServletContext sc = getServletContext();
        sc.setAttribute("conn", conn);
        
        key = getServletContext().getInitParameter("securityKey");
        cipher = getServletContext().getInitParameter("securityCipher");
        System.out.println("key: " + key);
        System.out.println("cipher: " + cipher);
        
        um = new UserManager();
    }
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // check if connection is null
        if (conn != null) {
            
            HttpSession session = request.getSession();
            
            String email = request.getParameter("email");
            String password = request.getParameter("password");
            String role = request.getParameter("role");
            
            Captcha captcha = (Captcha) session.getAttribute(Captcha.NAME);
            request.setCharacterEncoding("UTF-8"); // to capture non-Latin chars
            String captchaAnswer = request.getParameter("captchaAnswer");
            
            String action = request.getParameter("action");
            System.out.println("action is: " + action);
            
            // check captcha answer
            if (captcha.isCorrect(captchaAnswer)) {
                
                if (action.equals("Login")) {
                    
                    User user = um.login(email, password, key, cipher, conn);
                    
                    // check if log-in is successful
                    if (user != null) {
                        session.setAttribute("user", user);
                        response.sendRedirect("home.jsp");
                    }

                    else {
                        response.sendRedirect("errorlogin.jsp");
                    }
                }
                
                else if (action.equals("Sign-up")) {
                
                    String confirmPass = request.getParameter("confirmPass");

                    if (password.equals(confirmPass)) {
                        boolean dupe = um.register(email, password, role, key, cipher, conn);
                        
                        if (!dupe) {
                            request.getRequestDispatcher("login.jsp").forward(request, response);
                        }
                        
                        else {
                            response.sendRedirect("erroremaildupe.jsp");
                        }
                    }

                    else {
                        response.sendRedirect("errorlogin.jsp");
                    }
                }
            }
            
            else {
                    response.sendRedirect("errorcaptcha.jsp");
            }
        }
        
        else {
            response.sendRedirect("errorconn.jsp");
        }
        
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
