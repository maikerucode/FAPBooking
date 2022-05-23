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
import model.ReportManager;
import model.BookingManager;
import model.UserManager;

/**
 *
 * @author Lenovo
 */
public class AccountDetails {

    BookingManager bm = new BookingManager();
    UserManager um = new UserManager();
    
    //Variables
    String email = "";
    String role = "";
    ResultSet userResult;
    ResultSet reservationResult;
    String filename = "";

    //Document
    Document doc = new Document();

    //General Constructor
    public void AccountDetails() {
    }

    public String AccountDetails(String email, String role, Connection conn) {
        this.email = email;
        this.role = role;
        this.userResult = um.getSingleUser(email, conn);
        this.reservationResult = bm.userReservations(email, conn);;

        //Debugging
        System.out.println("AccountDetails.java");
        System.out.println("Username: " + this.email);
        System.out.println("Role: " + this.role + "\n");

        //Date
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd_HH:mm:ss");
        DateTimeFormatter dtfFileName = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");
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
            PdfWriter writer = PdfWriter.getInstance(doc, new FileOutputStream("C:\\Users\\CG\\Desktop\\"
                    + email + "_" + dtfFileName.format(now) + "_" + "AccountDetails.pdf"));

            System.out.println("Account Details Filename: " + "C:\\Users\\CG\\Desktop\\"
                    + email + "_" + dtfFileName.format(now) + "_" + "AccountDetails.pdf");

            filename = "C:\\Users\\CG\\Desktop\\" + email + "_" + dtfFileName.format(now) + "_" + "AccountDetails.pdf";
            
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

            //User Table
            usertbl.addCell("Email");
            usertbl.addCell("firstname");
            usertbl.addCell("lastname");
            usertbl.addCell("Role");

            while (userResult.next()) {
                usertbl.addCell(userResult.getString("email"));
                usertbl.addCell(userResult.getString("firstname"));
                usertbl.addCell(userResult.getString("lastname"));
                usertbl.addCell(userResult.getString("role"));
            }

            body.add("Reservation Details");

            //Reservation Headers
            reservationtbl.addCell("Room Number");
            reservationtbl.addCell("Check In");
            reservationtbl.addCell("Check Out");
            reservationtbl.addCell("Total Charge");
            reservationtbl.addCell("Reference Number \n");

            if (reservationResult.next() == false) {
                //Reservation Table
                System.out.println("reservation Result is null");

            } else {
                do {
//                    reservationtbl.addCell(reservationResult.getString("email"));
//                    System.out.println("Room no: " + reservationResult.getString("email"));

                    reservationtbl.addCell(reservationResult.getString("room_no"));
                    System.out.println("Room no: " + reservationResult.getString("room_no"));

                    reservationtbl.addCell(reservationResult.getString("check_in"));
                    System.out.println("Check In: " + reservationResult.getString("check_in"));
//                    
                    reservationtbl.addCell(reservationResult.getString("check_out"));
                    System.out.println("Check Out: " + reservationResult.getString("check_out"));
//                    
                    reservationtbl.addCell(reservationResult.getString("total_charge"));
                    System.out.println("Total Charge: " + reservationResult.getString("total_charge"));
//                    
                    reservationtbl.addCell(reservationResult.getString("ref_no"));
                    System.out.println("Reference No.: " + reservationResult.getString("ref_no"));
                } while (reservationResult.next());
            }

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
        
        return filename;
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
            cCompany.setPadding(10);
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
