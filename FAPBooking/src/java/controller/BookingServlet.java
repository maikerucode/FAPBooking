package controller;

import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;
import model.*;

/**
 *
 * @author star
 */
public class BookingServlet extends HttpServlet {
    
    EntryValidator ev = new EntryValidator();
    
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
        
        if (action.equals("Book a Room")) {
            response.sendRedirect("booking.jsp");
        }
        
        else if (action.equals("Confirm Booking")) {
            String inMonth = request.getParameter("inMonth");
            String inDay = request.getParameter("inDay");
            String inYear = request.getParameter("inYear");
            String outMonth = request.getParameter("outMonth");
            String outDay = request.getParameter("outDay");
            String outYear = request.getParameter("outYear");
            String roomSingle = request.getParameter("roomSingle");
            String roomDouble = request.getParameter("roomDouble");
            String roomTriple = request.getParameter("roomTriple");
            String roomQuad = request.getParameter("roomQuad");
            
            System.out.println("in month: " + inMonth);
            System.out.println("in day: " + inDay);
            System.out.println("in year: " + inYear);
            System.out.println("out month: " + outMonth);
            System.out.println("out day: " + outDay);
            System.out.println("out year: " + outYear);
            System.out.println("room type single: " + roomSingle);
            System.out.println("room type double: " + roomDouble);
            System.out.println("room type triple: " + roomTriple);
            System.out.println("room type quad: " + roomQuad);
            
            // result of the validation of the input dates
            String checkDate = ev.checkDates(inMonth, inDay, inYear,
                                outMonth, outDay, outYear);
            
            // if an date is invalid (ex: February 30)
            if (checkDate.equals("invalid date")) {
                response.sendRedirect("errordates.jsp");
            }
            
            /* if check-in date is not before
                or same as the check-out date */
            else if (checkDate.equals("invalid date range")) {
                response.sendRedirect("errordaterange.jsp");
            }
            
            // if input is a past date
            else if (checkDate.equals("past date")) {
                response.sendRedirect("errordatepast.jsp");
            }
            
            // if no rooms are available
//            else if (!ev.checkRoom()) {
//                
//            }
            
            // if it passes all the validation checks
            else {
                response.sendRedirect("home.jsp");
            }
            
//            nts: error page if 0 rooms for all room types

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
