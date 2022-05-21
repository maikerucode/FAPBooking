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
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession(true);
        
        ServletContext sc = getServletContext();
        Connection conn = (Connection) sc.getAttribute("conn");
        
        String tableName = request.getParameter("tableName");
        String action = request.getParameter("action");
        System.out.println("action is: " + action);
        // prevents nullpointerexception to be thrown
        if (action == null) {
            action = "";
        }
        
        // NTS: pass the value of pageNumber, checkLast to admindashboard jsp
        if (action.equals("Admin Dashboard") || action.equals("View Users")
                || action.equals("Back") || action.equals("Next")) {
            int pageId = Integer.parseInt(request.getParameter("pageNumber"));
            int pageTotal = 20;   // display 20 records
            System.out.println("tableName: " + tableName);
            System.out.println("pageNumber: " + Integer.parseInt(request.getParameter("pageNumber")));

            if (pageId == 1) { }
            else {
                pageId = pageId - 1;
                pageId = pageId*pageTotal + 1;
            }
            
            ResultSet rs = am.getRecords(pageId, pageTotal, tableName, conn);
            boolean checkLast = am.checkLast();
            
            request.setAttribute("records", rs);
            request.setAttribute("checkLast", checkLast);
            request.setAttribute("pageNumber", Integer.parseInt(request.getParameter("pageNumber")));
            
            System.out.println("pageId: " + pageId);
            System.out.println("checkLast: " + checkLast);
            
            if (tableName.equals("Reserve")) {
                request.getRequestDispatcher("admindashboard.jsp").forward(request, response);
            } else if (tableName.equals("User")) {
                request.getRequestDispatcher("adminviewusers.jsp").forward(request, response);
            } else {
                response.sendRedirect("error.jsp");
            }
        }
        
        else if (action.equals("Delete")) {
            Object val = "";
            
            switch(tableName) {
                case "Reserve": case "User":
                    val = request.getParameter("email");
                    break;
                case "Room":
                    val = request.getParameter("roomNumber");
                    break;
                case "Rate":
                    val = request.getParameter("roomRate");
                    break;
                default:
                    break;
            }   
                    
            if (am.deleteRecord(tableName, val, conn)) {
                response.sendRedirect("successdelete.jsp");
            } else {
                response.sendRedirect("errordelete.jsp");
            }
        }
        
        else if (action.equals("Update")) {
            request.setAttribute("email", request.getParameter("email"));
            request.getRequestDispatcher("adminupdatereserve.jsp").forward(request, response);
        }
        
        else if (action.equals("Update Record")) {
            Object val1 = request.getParameter("email");
            Object val2 = request.getParameter("reserveStatus");
            Object val3 = request.getParameter("refNumber");
                    
            if (am.updateRecord(tableName, val1, val2, val3, conn)) {
                response.sendRedirect("successupdate.jsp");
            } else {
                response.sendRedirect("errorupdate.jsp");
            }
        }
        
        else {
            response.sendRedirect("error.jsp");
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