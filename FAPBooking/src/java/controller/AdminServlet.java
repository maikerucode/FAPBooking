package controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.AdminManager;

/**
 *
 * @author star
 */
public class AdminServlet extends HttpServlet {

    AdminManager am = new AdminManager();
    
    // TBU...
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession(true);
        
        ServletContext sc = getServletContext();
        Connection conn = (Connection) sc.getAttribute("conn");
        
        String action = request.getParameter("action");
        System.out.println("action is: " + action);
        // prevents nullpointerexception to be thrown
        if (action == null) {
            action = "";
        }
        
        String tableName = request.getParameter("tableName");
        int pageId = Integer.parseInt(request.getParameter("pageNumber"));
        int pageTotal = 20;   // display 20 records
        System.out.println("tableName: " + tableName);
        System.out.println("pageId: " + pageId);
        System.out.println("pageTotal: " + pageTotal);
        
        if (pageId == 1) { }
        else {
            pageId = pageId - 1;
            pageId = pageId*pageTotal + 1;
        }
        
        // NTS: pass the value of pageNumber, checkLast to admindashboard jsp
        if (action.equals("Admin Dashboard")) {
            ResultSet rs = am.getRecords(pageId, pageTotal, tableName, conn);
            boolean checkLast = am.checkLast();
            
            request.setAttribute("records", rs);
            request.setAttribute("checkLast", checkLast);
            request.setAttribute("pageNumber", pageId);
            
            System.out.println("rs: " + rs);
            System.out.println("checkLast: " + checkLast);
            System.out.println("pageId: " + pageId);
            
            request.getRequestDispatcher("admindashboard.jsp").forward(request, response);
        }
        
        else {
            // placeholder jsp destination
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

/*
references:
JDBC pagination: https://www.javamadesoeasy.com/2015/11/jdbc-pagination-how-can-we-readfetch.html
*/