/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.itextpdf.text.DocumentException;
import java.io.IOException;
import java.sql.Connection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
import reports.OrderOfReceipt;

/**
 *
 * @author star
 */
public class ReportServlet extends HttpServlet {

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

        User user = (User) session.getAttribute("user");
        String email = user.getEmail();
        String role = user.getRole();
        ;

        if (action.equals("Get Report")) {
            try {
                String dateString = "2002/01/14";
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
                Date date = formatter.parse(dateString);
                
                ReportManager rm = new ReportManager();
                OrderOfReceipt oor = new OrderOfReceipt();
                oor.OrderOfRecipt(email, role, date, role, role, 0);
                rm.printReport(email, role, conn);
                response.sendRedirect("reportconfirm.jsp");
            } catch (DocumentException exm) {
                Logger.getLogger(ReportServlet.class.getName()).log(Level.SEVERE, null, exm);
                response.sendRedirect("errorreport.jsp");
            } catch(ParseException pe){
                Logger.getLogger(ReportServlet.class.getName()).log(Level.SEVERE, null, pe);
                response.sendRedirect("errorreport.jsp");
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
