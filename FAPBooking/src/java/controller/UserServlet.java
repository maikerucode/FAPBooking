package controller;

import java.io.*;
import java.sql.Connection;
import javax.servlet.*;
import javax.servlet.http.*;
import model.User;
import model.UserManager;

/**
 *
 * @author star
 */
public class UserServlet extends HttpServlet {
    
    private UserManager um;
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("== UserServlet===============================");
        
        HttpSession session = request.getSession(true);
        User user = (User) session.getAttribute("user");
        
        ServletContext sc = getServletContext();
        Connection conn = (Connection) sc.getAttribute("conn");
        
        String action = request.getParameter("action");
        System.out.println("action is: " + action);
        // prevents nullpointerexception to be thrown
        if (action == null) {
            action = "";
        }
        
        // check if connection is null
        if (conn != null) {
            um = new UserManager();
        
            if (action.equals("User Dashboard")) {
                String refNumber = um.getRefNumber(user.getEmail(), conn);
                request.setAttribute("refNumber", refNumber);
                request.getRequestDispatcher("userdashboard.jsp").forward(request, response);
            }

            else if (action.equals("Update Reference Number")) {
                String newRefNumber = request.getParameter("refNumber");
                um.updateRefNumber(newRefNumber, user.getEmail(), conn);
                response.sendRedirect("successuserupdate.jsp");
            }

            else {
                response.sendRedirect("error.jsp");
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
