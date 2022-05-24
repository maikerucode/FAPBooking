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

import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.*;

/**
 *
 * @author Lenovo
 */
public class AccountDetails {

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

    //General Constructor
    public void AccountDetails() {
    }

    public String AccountDetails(String email, String role, Connection conn) {
        this.email = email;
        this.role = role;
        this.conn = conn;

        //Debugging
        System.out.println("AccountDetails.java");
        System.out.println("Username: " + this.email);
        System.out.println("Role: " + this.role + "\n");
        
        //Querries - galing sa model for querries
        userResult = um.getSingleUser(email, conn);
        reservationResult = bm.userReservations(email, conn);
        
        //Date
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        DateTimeFormatter dtfFilename = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");
        LocalDateTime now = LocalDateTime.now();

        //Fonts
        Font headerFont = new Font(Font.FontFamily.HELVETICA, 14, Font.BOLD, new BaseColor(200, 0, 0));

        //Sample
//        Font[] fonts = {
//            new Font(),
//            new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD, new BaseColor(0, 0, 0))
//        };

        /*
            Font("Font-Fam", ""Font-size", "Font-type", "BaseColor(R,G,B)")
                RGB Max Val - 255
                
            Default
              Font-Fam: Helvetica
              Font-size: 12
              color: black
         */
        //PDF Formulation
        try {
            //PDFWriter Directs PDF to Desktop
            PdfWriter writer = PdfWriter.getInstance(doc, new FileOutputStream(location + "\\Desktop\\"
                    + email + "_" + dtfFilename.format(now) + "_AccountDetails.pdf"));

            Filename = location + "\\Desktop\\" + email + "_" + dtfFilename.format(now) + "_AccountDetails.pdf";

            System.out.println("Account Details Filename: " + Filename);
            
            System.out.println("location: " + location);
            
            //Directs PDF to currect project directory (Tentative)
            //PdfWriter.getInstance(doc, new FileOutputStream(currPath + "\\Admin" + uname + "Report.pdf"));
            //Header/Footer Event
            HeaderFooterPageEvent eve = new HeaderFooterPageEvent();
            writer.setPageEvent(eve);

            //PDF Open
            doc.open();
            Paragraph reportType = new Paragraph("Account Details: \n", headerFont);
            Paragraph introduction = new Paragraph();
            Paragraph body = new Paragraph();

            introduction.add("Date and Time: " + dtf.format(now) + "\n\n");

            //User
            introduction.add("Welcome\n");
            introduction.add("User: " + email + "\n");
            introduction.add("Role: " + this.role + "\n");

            //Tables
            PdfPTable usertbl = new PdfPTable(4);
            PdfPTable reservationtbl = new PdfPTable(5);

            introduction.add("User Details");
            //User Table
            usertbl.addCell("Email");
            usertbl.addCell("firstname");
            usertbl.addCell("lastname");
            usertbl.addCell("Role");

            while (userResult.next()) {
                usertbl.addCell(userResult.getString("email"));
                usertbl.addCell(userResult.getString("firstName"));
                usertbl.addCell(userResult.getString("lastName"));
                usertbl.addCell(userResult.getString("role"));
            }

            body.add("\n Reservation Details");

            if (reservationResult.next() == false) {
                System.out.println("reservationResult is null");
            } else {
                do {
                    //Reservation Table
                    reservationtbl.addCell("Room Number");
                    reservationtbl.addCell("Check In");
                    reservationtbl.addCell("Check Out");
                    reservationtbl.addCell("Total Charge");
                    reservationtbl.addCell("Reference Number");

                    reservationtbl.addCell(reservationResult.getString("room_no"));
                    reservationtbl.addCell(reservationResult.getString("check_in"));
                    reservationtbl.addCell(reservationResult.getString("check_out"));
                    reservationtbl.addCell(reservationResult.getString("total_charge"));
                    reservationtbl.addCell(reservationResult.getString("ref_no"));

                } while (reservationResult.next());

            }

            //Reservations Table
            //Add to doc
            doc.add(reportType);
            doc.add(introduction);
            doc.add(usertbl);
            doc.add(body);
            doc.add(reservationtbl);

            //Close
            doc.close();

            System.out.println("Account Details Printed");

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

        @Override //Footer
        public void onEndPage(PdfWriter writer, Document document) {
            ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_CENTER, new Phrase("page " + document.getPageNumber()), 550, 30, 0);
        }

    }

}
