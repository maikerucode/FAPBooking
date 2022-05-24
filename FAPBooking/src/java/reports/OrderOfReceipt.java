/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package reports;

//Imports
import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.Font;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.util.Date;

import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.BookingManager;
import model.ReportManager;
import model.UserManager;

/**
 *
 * @author Lenovo
 */
public class OrderOfReceipt {

    //Variables
    String email = "";
    String role = "";
    ResultSet userResult;
    ResultSet reservationResult;

    //Mandatory
    String location = System.getProperty("user.home");
    String Filename;
    Connection conn;
    
    //Model
    UserManager um = new UserManager();
    BookingManager bm = new BookingManager();
    
    //Document
    Document doc = new Document();

    public void OrderOfRecipt(){}

    public String OrderOfRecipt(String email, String role, Connection conn) {
        this.email = email;
        this.role = role;
        this.conn = conn;

        //Debugging
        System.out.println("OrderOfReceipt.java");
        System.out.println("Username: " + this.email + "\n");
        System.out.println("Role: " + this.role + "\n");

        //Querries - galing sa model for querries
        userResult = um.getSingleUser(email, conn);
        reservationResult = bm.userPendingReservations(email, conn);
        
        //Date
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        DateTimeFormatter dtfFilename = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");
        LocalDateTime now = LocalDateTime.now();

        //Fonts
        Font headerFont = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD, new BaseColor(200, 0, 0));
        Font bodyFont = new Font(Font.FontFamily.HELVETICA, 12, Font.ITALIC);

        //PDF Formulation
        try {
            //PDFWriter Directs PDF to Desktop
            PdfWriter writer = PdfWriter.getInstance(doc, new FileOutputStream(location + "\\Desktop\\"
                    + email + "_" + dtfFilename.format(now) + "_OrderOfReceipt.pdf"));

            Filename = location + "\\Desktop\\" + email + "_" + dtfFilename.format(now) + "_OrderOfReceipt.pdf";

            System.out.println("Order of Receipt Filename: " + Filename);
            
            //Directs PDF to currect project directory (Tentative)
            //PdfWriter.getInstance(doc, new FileOutputStream(currPath + "\\Admin" + uname + "Report.pdf"));
            //Header/Footer Event
            HeaderFooterPageEvent eve = new HeaderFooterPageEvent();
            writer.setPageEvent(eve);

            //PDF Open
            doc.open();
            Paragraph reportType = new Paragraph("Order Of Receipt: \n", headerFont);
            Paragraph introduction = new Paragraph();
            Paragraph body = new Paragraph();

            introduction.add("Date and Time: " + dtf.format(now) + "\n");

            //User
            userResult.next();
            introduction.add("Welcome\n");
            introduction.add("User: " + email + "\n");
            introduction.add("Firsname: " + userResult.getString("firstName") + "\n");
            introduction.add("Latsname: " + userResult.getString("lastName") + "\n");
            introduction.add("Role: " + this.role + "\n");

            //Tables
            PdfPTable reservationtbl = new PdfPTable(6);
            
            //Body
            if (reservationResult.next() == false) {
                System.out.println("reservationResult is null");
            } else {
                do {
                    //Reservation Table
                    reservationtbl.addCell("Room Number");
                    reservationtbl.addCell("Check In");
                    reservationtbl.addCell("Check Out");
                    reservationtbl.addCell("Total Charge");
                    reservationtbl.addCell("Reserve Status");
                    reservationtbl.addCell("Reference Number");

                    reservationtbl.addCell(reservationResult.getString("room_no"));
                    reservationtbl.addCell(reservationResult.getString("check_in"));
                    reservationtbl.addCell(reservationResult.getString("check_out"));
                    reservationtbl.addCell(reservationResult.getString("total_charge"));
                    reservationtbl.addCell(reservationResult.getString("reserve_status"));
                    reservationtbl.addCell(reservationResult.getString("ref_no"));

                } while (reservationResult.next());

            }

            body.add("Thank you for Joining University Inn");
            
            //Add to doc
            doc.add(reportType);
            doc.add(introduction);
            doc.add(reservationtbl);
            doc.add(body);

            //Close
            doc.close();

            System.out.println("Order of Receipts Printed");
        } catch (Exception ex) {
            Logger.getLogger(AccountDetails.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
        }
        return Filename;
    }

    public class HeaderFooterPageEvent extends PdfPageEventHelper {

        @Override //Header
        public void onStartPage(PdfWriter writer, Document document) {
            PdfPTable tableHeader = new PdfPTable(1);
            Font fontTitle = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD, new BaseColor(0, 0, 0));
            Font subTitle = new Font(Font.FontFamily.HELVETICA, 18, Font.ITALIC);

            PdfPCell cCompany = new PdfPCell(new Phrase("University Inn", fontTitle));
            PdfPCell cSub = new PdfPCell(new Phrase("Hotel Room Booking", subTitle));

            cCompany.setBorder(Rectangle.NO_BORDER);
            cSub.setBorder(Rectangle.NO_BORDER);
            cSub.setPadding(10);

            tableHeader.addCell(cCompany);
            tableHeader.addCell(cSub);

            try {
                doc.add(tableHeader);
            } catch (DocumentException ex) {
                Logger.getLogger(ReportManager.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        @Override //Header
        public void onEndPage(PdfWriter writer, Document document) {
            ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_CENTER, new Phrase("page " + document.getPageNumber()), 550, 30, 0);
        }

    }

}
