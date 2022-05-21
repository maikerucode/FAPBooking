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
import java.util.Date;

import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.ReportManager;

/**
 *
 * @author Lenovo
 */
public class OrderOfReceipt {

    //Variables
    String email;
    String role;
    Date date;
    String roomType;
    String numberOfPeople;
    double price;

    //Document
    Document doc = new Document();


    public void OrderOfRecipt(String email, String role, Date date, String roomType, String numberOfPeople, double price) {
        this.email = email;
        this.role = role;
        this.date = date;
        this.roomType = roomType;
        this.numberOfPeople = numberOfPeople;
        this.price = price;

        //Debugging
        System.out.println("OrderOfReceipt.java");
        System.out.println("Username: " + this.email + "\n");
        System.out.println("Role: " + this.role + "\n");
        System.out.println("Date: " + this.date + "\n");
        System.out.println("Room type: " + this.roomType + "\n");
        System.out.println("Number of People: " + this.numberOfPeople + "\n");
        System.out.println("Price: " + this.price + "\n");

        //Date
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();

        //Fonts
        Font headerFont = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD, new BaseColor(200, 0, 0));
        Font bodyFont = new Font(Font.FontFamily.HELVETICA, 12, Font.ITALIC);

        //PDF Formulation
        try {
            //PDFWriter Directs PDF to Desktop
            PdfWriter writer = PdfWriter.getInstance(doc, new FileOutputStream("C:\\Users\\" + System.getProperty("user.name") + "\\Desktop\\"
                    + email + "_" + dtf.format(now) + "OrderOfReceipt.pdf"));

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

            introduction.add(dtf.format(now) + "\n");

            //User
            introduction.add("Welcome\n");
            introduction.add("User: " + email + "\n");
            introduction.add("Role: " + this.role + "\n");

            //Body
            Phrase userP = new Phrase("User: ", bodyFont);
            body.add(email + "\n");
            Phrase dateP = new Phrase("Date Reserved: ", bodyFont);
            body.add(date.toString() + "\n");
            Phrase roomTP = new Phrase("Room Type: ", bodyFont);
            body.add(roomType + "\n");
            Phrase numberOfPeopleP = new Phrase("Number of People: ", bodyFont);
            body.add(numberOfPeople + "\n");
            Phrase PriceP = new Phrase("Price: ", bodyFont);
            body.add(price + "\n");

            //Add to doc
            doc.add(reportType);
            doc.add(introduction);
            doc.add(body);
            doc.add(userP);
            doc.add(dateP);
            doc.add(roomTP);
            doc.add(numberOfPeopleP);
            doc.add(PriceP);

            //Close
            doc.close();

            System.out.println("Order of Receipts Printed");
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
