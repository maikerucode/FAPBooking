/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package reports;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.BookingManager;
import model.ReportManager;

/**
 *
 * @author Lenovo
 */
public class MonthlyNumberOfUsers {

    //Variables
    //m//mainModel mm = new mainModel();
    String email = "";
    String role = "";
    String month = "";
    String year = "";
    ResultSet result;
    int userCount = 0;

    //Mandatory
    String location = System.getProperty("user.home");
    String Filename;
    Connection conn;
    
    //Model
    BookingManager bm = new BookingManager();
    
    //Document
    Document doc = new Document();

    public void MonthlyNumberOfUsers() {}

    public String MonthlyNumberOfUsers(String email, String role, String year, String month, Connection conn) {
        this.email = email;
        this.role = role;
        this.conn = conn;
        this.year = year;
        this.month = month;

        //Debugging
        System.out.println("MonthlyNumberOfUsers.java");
        System.out.println("Username: " + this.email + "\n");
        System.out.println("Role: " + this.role + "\n");

        //Querries - galing sa model for querries
        result = bm.GetMonthlyNumUsers(month, year, conn);
        
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
                    + email + "_" + dtfFilename.format(now) + "_MonthlyNumberOfUsers.pdf"));

            Filename = location + "\\Desktop\\" + email + "_" + dtfFilename.format(now) + "_MonthlyNumberOfUsers.pdf";
            
            System.out.println("MonyhlyNumberOfUsers Filename: " + Filename);
            
            //Directs PDF to currect project directory (Tentative)
            //PdfWriter.getInstance(doc, new FileOutputStream(currPath + "\\Admin" + uname + "Report.pdf"));
            //Header/Footer Event
            MonthlyNumberOfUsers.HeaderFooterPageEvent eve = new MonthlyNumberOfUsers.HeaderFooterPageEvent();
            writer.setPageEvent(eve);

            //PDF Open
            doc.open();
            Paragraph reportType = new Paragraph("Monthly Number of Users: \n", headerFont);
            Paragraph introduction = new Paragraph();
            Paragraph body = new Paragraph();

            introduction.add(dtf.format(now) + "\n");

            //User
            introduction.add("Welcome\n");
            introduction.add("User: " + email + "\n");
            introduction.add("Role: " + this.role + "\n");

            //Body
            //Tables
            PdfPTable table = new PdfPTable(2);
            table.addCell("User");
            table.addCell("First Name");
            table.addCell("Last Name");
            table.addCell("Role");

            while (result.next()) {
                table.addCell(result.getString("email"));
                table.addCell(result.getString("firstName"));
                table.addCell(result.getString("lastName"));
                table.addCell(result.getString("role"));
                userCount++;
            }
            body.add("Number of Users: " + userCount);
            
            //Add to doc
            doc.add(reportType);
            doc.add(introduction);
            doc.add(body);
            doc.add(table);

            //Close
            doc.close();

            System.out.println("Monthly Number of Users Printed");
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
