/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.itextpdf.text.DocumentException;
import java.io.IOException;
import java.sql.Connection;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.ReportManager;
import model.User;
import reports.*;

/**
 *
 * @author star
 */
public class ReportServlet extends HttpServlet {

    String filename;
    Stamper st = new Stamper();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("== ReportServlet =============================");

        HttpSession session = request.getSession(true);
        ServletContext sc = getServletContext();
        Connection conn = (Connection) sc.getAttribute("conn");

        String action = request.getParameter("action");
        System.out.println("action is: " + action);

        // prevents nullpointerexception to be thrown
        if (action == null) {
            action = "";
        }

        User user = (User) session.getAttribute("user");
        String email = user.getEmail();
        String role = user.getRole();

        try {
            // change/remove this
//            if (action.equals("Get Report")) {
//
//                ReportManager rm = new ReportManager();
//
//                // change/remove this
//                rm.printReport(email, role, conn);
//
//                response.sendRedirect("successreport.jsp");
//
//            } 
            if (action.equals("Get AccountDetails Report")) {
                AccountDetails ad = new AccountDetails();
                filename = ad.AccountDetails(email, role, conn);
                st.Stamper(email, role, filename, "AccountDetails");
            } 
            else if (action.equals("Get AnnualNumberOfUsers Report")) {
                String year = "2022";

                AnnualNumberOfUsers anou = new AnnualNumberOfUsers();
                filename = anou.AnnualNumberOfUsers(email, role, year, conn);
                st.Stamper(email, role, filename, "AnnualNumberOfUsers");
            } 
            else if (action.equals("Get MonthlyNumberOfUsers Report")) {
                String year = "2022";
                String month = "05";

                MonthlyNumberOfUsers mnou = new MonthlyNumberOfUsers();
                filename = mnou.MonthlyNumberOfUsers(email, role, year, month, conn);
                st.Stamper(email, role, filename, "MonthlyNumberOfUsers");
            }
            else if (action.equals("Get Report")) {
                String year = "2022";

                AnnualPrefferredRooms apr = new AnnualPrefferredRooms();
                filename = apr.AnnualPrefferredRooms(email, role, year, conn);
                st.Stamper(email, role, filename, "AnnualPrefferredRooms");
            } 

        } catch (Exception ex) {
            Logger.getLogger(ReportServlet.class.getName()).log(Level.SEVERE, null, ex);
            response.sendRedirect("errorreport.jsp");
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
