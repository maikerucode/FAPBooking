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
    BookingManager bm = new BookingManager();
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession(true);
        Booking booking = (Booking) session.getAttribute("booking"); 
        
        ServletContext sc = getServletContext();
        Connection conn = (Connection) sc.getAttribute("conn");
        
        String action = request.getParameter("action");
        System.out.println("action is: " + action);
        
        // prevents nullpointerexception to be thrown
        if (action == null) {
            action = "";
        }
        
        // home.jsp -> booking.jsp
        if (action.equals("Book a Room")) {
            response.sendRedirect("booking.jsp");
        }
        
        else if (action.equals("Confirm Booking") || action.equals("Submit Payment")) {            
            User user = (User) session.getAttribute("user");
            boolean validBooking = false;
            System.out.println("user email: " + user.getEmail());
                    
            // check if user tries to book while having an ongoing booking
            if(action.equals("Confirm Booking")
                    && bm.checkOngoing(user.getEmail(), conn)) {
                response.sendRedirect("errorongoing.jsp");
            }
            
            // === input validation ================================
            else if(action.equals("Confirm Booking")) {
                int inMonth = Integer.parseInt(request.getParameter("inMonth"));
                int inDay = Integer.parseInt(request.getParameter("inDay"));
                int inYear = Integer.parseInt(request.getParameter("inYear"));
                int outMonth = Integer.parseInt(request.getParameter("outMonth"));
                int outDay = Integer.parseInt(request.getParameter("outDay"));
                int outYear = Integer.parseInt(request.getParameter("outYear"));

                // result of the validation of the input dates
                String checkDate = ev.checkDates(inMonth, inDay, inYear,
                                    outMonth, outDay, outYear);

                // if an date is invalid (ex: February 30)
                switch (checkDate) {
                    case "invalid date":
                        response.sendRedirect("errordates.jsp");
                        break;
                    case "invalid date range":
                        response.sendRedirect("errordaterange.jsp");
                        break;
                    case "past date":
                        response.sendRedirect("errordatepast.jsp");
                        break;
                    case "max booking length":
                        response.sendRedirect("errorbookinglength.jsp");
                        break;
                    default:
                        validBooking = true;
                        break;
                }
            }
            
            // === booking =========================================
            if (validBooking || action.equals("Submit Payment")) {
                
                if (action.equals("Confirm Booking")) {
                    booking = new Booking();
                    
                    booking.setTypeSingle(Integer.parseInt(request.getParameter("roomSingle")));
                    booking.setTypeDouble(Integer.parseInt(request.getParameter("roomDouble")));
                    booking.setTypeTriple(Integer.parseInt(request.getParameter("roomTriple")));
                    booking.setTypeQuad(Integer.parseInt(request.getParameter("roomQuad")));
                    booking.setBookDateIn(Integer.parseInt(request.getParameter("inYear")),
                            Integer.parseInt(request.getParameter("inMonth")), Integer.parseInt(request.getParameter("inDay")));
                    booking.setBookDateOut(Integer.parseInt(request.getParameter("outYear")),
                            Integer.parseInt(request.getParameter("outMonth")), Integer.parseInt(request.getParameter("outDay")));
                }
                
                // if the values for each room type are all 0
                if (booking.getSumRoomTypes() == 0) {
                    response.sendRedirect("errorbookingroomtypes.jsp");
                }

                // if there is at least 1 room of any type provided by user
                else if(bm.checkBooking(booking, conn)) {
                    
                    if(action.equals("Confirm Booking")) {
                        session.setAttribute("user", user);
                        session.setAttribute("booking", booking);
                        response.sendRedirect("bookingpayment.jsp");
                    }
                    
                    else if(action.equals("Submit Payment")) {
                        // add payment input validation methods here
                        // if input is valid, process the booking
                        
                        // NTS: check if values in bm are still stored
                        bm.book(user.getEmail());
                        session.setAttribute("user", user);
                        response.sendRedirect("bookingsuccess.jsp");
                    }
                }
                
                // if the booking is not possible (ex: insufficient available rooms)
                else {
                    response.sendRedirect("errorbooking.jsp");
                }
            }
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
