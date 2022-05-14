package controller;

import java.io.*;
import java.sql.*;
import java.time.LocalDate;
import javax.servlet.*;
import javax.servlet.http.*;
import model.*;

/**
 *
 * @author star
 */
public class BookingServlet extends HttpServlet {
    
    EntryValidator ev = new EntryValidator();
    BookingManager bm = new BookingManager();
    
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
            int inMonth = Integer.parseInt(request.getParameter("inMonth"));
            int inDay = Integer.parseInt(request.getParameter("inDay"));
            int inYear = Integer.parseInt(request.getParameter("inYear"));
            int outMonth = Integer.parseInt(request.getParameter("outMonth"));
            int outDay = Integer.parseInt(request.getParameter("outDay"));
            int outYear = Integer.parseInt(request.getParameter("outYear"));
            int roomSingle = Integer.parseInt(request.getParameter("roomSingle"));
            int roomDouble = Integer.parseInt(request.getParameter("roomDouble"));
            int roomTriple = Integer.parseInt(request.getParameter("roomTriple"));
            int roomQuad = Integer.parseInt(request.getParameter("roomQuad"));
            
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
            
            // === input validation ================================
            
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
            
            // if input is a past date
            else if (checkDate.equals("max booking length")) {
                response.sendRedirect("errorbooklength.jsp");
            }
            
            // === booking =========================================
            // if the input passes the validation checks
            else {
                int sumRoomTypes = roomSingle + roomDouble + roomTriple + roomQuad;
                LocalDate dateIn = LocalDate.of(inYear, inMonth, inDay);
                LocalDate dateOut = LocalDate.of(outYear, outMonth, outDay);
                System.out.println("sumRoomTypes: " + sumRoomTypes);
                System.out.println("dateIn: " + dateIn);
                System.out.println("dateOut: " + dateOut);
                
                // if the values for each room type are all 0
                if (sumRoomTypes == 0) {
                    response.sendRedirect("errorbookroomtypes.jsp");
                }

                // if booking is successful & there is at least 1 room type value
                else if(bm.checkBooking(roomSingle, roomDouble, roomTriple,
                                    roomQuad, dateIn, dateOut, conn)) {
                    
                    response.sendRedirect("bookingconfirm.jsp");
                    /* nts: in bookingconfirm JSP, call bm.book() to
                       finalize booking, but call bm.checkBooking again
                       for double checking */
                }
                
                // if the booking is not possible (ex: insufficient rooms)
                else {
                    response.sendRedirect("errorbooking.jsp");
                }
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
