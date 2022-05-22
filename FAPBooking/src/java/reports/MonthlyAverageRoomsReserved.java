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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.ReportManager;

/**
 *
 * @author Lenovo
 */
public class MonthlyAverageRoomsReserved {
    
    String email = "";
    String role = "";
    String type = "";
    double totalRevenue;
    int totalRoomsSold;
    double averageRoomRate;

    //Document
    Document doc = new Document();

    MonthlyAverageRoomsReserved() {

    }
    
    MonthlyAverageRoomsReserved(String email, String role, String type, double totalRevenue, int totalRoomsSold) {
        this.email = email;
        this.role = role;
        this.type = type;
        this.totalRevenue = totalRevenue;
        this.totalRoomsSold = totalRoomsSold;
        this.averageRoomRate = this.totalRevenue / this.totalRoomsSold;

        //Debugging
        System.out.println("MonthlyAverageRoomsReserved.java");
        System.out.println("Username: " + this.email + "\n");
        System.out.println("Role: " + this.role + "\n");
        System.out.println("Total Revenue: " + this.totalRevenue);
        System.out.println("Total Room Sold: " + this.totalRoomsSold);

        //Date
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();

        //Fonts
        Font headerFont = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD, new BaseColor(200, 0, 0));
        Font bodyFont = new Font(Font.FontFamily.HELVETICA, 12, Font.ITALIC);

        //PDF Formulation
        try {
            //Filename
            PdfWriter writer = PdfWriter.getInstance(doc, new FileOutputStream("C:\\Users\\" + System.getProperty("user.name") + "\\Desktop\\"
                    + email + "_" + dtf.format(now) + "Annual" + type + "RoomReserved.pdf"));

            //Header/Footer Event
            MonthlyAverageRoomsReserved.HeaderFooterPageEvent eve = new MonthlyAverageRoomsReserved.HeaderFooterPageEvent();
            writer.setPageEvent(eve);

            //PDF Open
            doc.open();
            Paragraph reportType = new Paragraph("Monthly Average Rooms Reserved: \n", headerFont);
            Paragraph introduction = new Paragraph();
            Paragraph body = new Paragraph();
            
            introduction.add(dtf.format(now) + "\n");
            
            //User
            introduction.add("Welcome\n");
            introduction.add("User: " + email + "\n");
            introduction.add("Role: " + this.role + "\n");
            
            //Body
            body.add("Total Revenue: ");
            body.add(Double.toString(this.totalRevenue) + "\n");
            body.add("Total Room Sold: ");
            body.add(Integer.toString(this.totalRoomsSold) + "\n");
            body.add("Average Room Rate: ");
            body.add(Double.toString(this.averageRoomRate));
            
            //Add to doc
            doc.add(reportType);
            doc.add(introduction);
            doc.add(body);
            
            //Close
            doc.close();

            System.out.println("Monthly Average " + type + " Printed");
        } catch (Exception ex) {
            Logger.getLogger(AccountDetails.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
        }
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
