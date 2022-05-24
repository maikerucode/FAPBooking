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
import java.sql.Date;

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
public class MonthlyPrefferredRooms {

    //Variables
    //mainModel mm = new mainModel();
    String email = "";
    String role = "";
    String year = "";

    //quantity per type
    int singleCount;
    int doubleCount;
    int tripleCount;
    int quadCount;
    ResultSet result;

    //Mandatory
    String location = System.getProperty("user.home");
    String Filename;
    Connection conn;

    //Model
    BookingManager bm = new BookingManager();

    //Document
    Document doc = new Document();

    //General Constructor
    public void MonthlyPrefferredRooms() {
    }

    public String MonthlyPrefferredRooms(String email, String role, String year, String month, Connection conn) {
        this.email = email;
        this.role = role;
        this.conn = conn;

        //Debugging
        System.out.println("MonthlyPrefferredRooms.java");
        System.out.println("Username: " + this.email + "\n");
        System.out.println("Role: " + this.role + "\n");

        //Querries - galing sa model for querries
        result = bm.getMonthlyReservations(month, year, conn);

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
            //PDFWriter Directs PDF to Desktop
            PdfWriter writer = PdfWriter.getInstance(doc, new FileOutputStream(location + "\\Desktop\\"
                    + email + "_" + dtfFilename.format(now) + "_MonthlyPrefferredRooms.pdf"));

            Filename = location + "\\Desktop\\" + email + "_" + dtfFilename.format(now) + "_MonthlyPrefferredRooms.pdf";

            System.out.println("MonthlyPrefferredRooms Filename: " + Filename);

            System.out.println("location: " + location);

            //Header/Footer Event
            MonthlyPrefferredRooms.HeaderFooterPageEvent eve = new MonthlyPrefferredRooms.HeaderFooterPageEvent();
            writer.setPageEvent(eve);

            if (result.next() == false) {
                System.out.println("annualPrefferredRooms is null");
            } else {
                do {
                    //Room Counts
                    //Single Rooms
                    if (result.getString("room_no").equals("200")
                            || result.getString("room_no").equals("201")
                            || result.getString("room_no").equals("300")
                            || result.getString("room_no").equals("301")) {
                        singleCount++;
                    }
                    //Double Rooms
                    if (result.getString("room_no").equals("202")
                            || result.getString("room_no").equals("302")) {
                        doubleCount++;
                    }
                    //Triple Rooms
                    if (result.getString("room_no").equals("203")
                            || result.getString("room_no").equals("303")) {
                        tripleCount++;
                    }
                    //Quad Rooms
                    if (result.getString("room_no").equals("204")
                            || result.getString("room_no").equals("304")) {
                        quadCount++;
                    }

                } while (result.next());
                //Quantity
                System.out.println("First Count: " + this.singleCount + "\n");
                System.out.println("Second Count: " + this.doubleCount + "\n");
                System.out.println("Third Count: " + this.tripleCount + "\n");
                System.out.println("Forth Count" + this.quadCount + "\n");
            }

            //PDF Open
            doc.open();
            Paragraph reportType = new Paragraph("Monthly Prefferred Rooms: \n", headerFont);
            Paragraph introduction = new Paragraph();
            Paragraph body = new Paragraph();

            introduction.add(dtf.format(now) + "\n");

            //User
            introduction.add("Welcome\n");
            introduction.add("User: " + email + "\n");
            introduction.add("Role: " + this.role + "\n");

            //Tables
            PdfPTable table = new PdfPTable(2);
            table.addCell("Type");
            table.addCell("Quantity");

            //Table Input
            table.addCell("Single Rooms");
            table.addCell(Integer.toString(singleCount));
            table.addCell("Double Rooms");
            table.addCell(Integer.toString(doubleCount));
            table.addCell("Triple Rooms");
            table.addCell(Integer.toString(tripleCount));
            table.addCell("Quadruple Rooms");
            table.addCell(Integer.toString(quadCount));

            String Largest;

            if ((singleCount >= doubleCount) && (singleCount >= tripleCount) && (singleCount >= quadCount)) { // singleCount >= doubleCount,tripleCount,quadCount,e
                Largest = "Single with " + singleCount;
            } else if ((doubleCount >= tripleCount) && (doubleCount >= quadCount)) {      // doubleCount >= tripleCount,quadCount,e
                Largest = "Double with " + doubleCount;
            } else if ((tripleCount >= quadCount)) {                            // tripleCount >= quadCount,e
                Largest = "Triple with " + tripleCount;
            } else {                                                // quadCount >= e
                Largest = "Quadrouple with " + quadCount;
            }

            body.add("Most Prefferred Room This Year " + year + ": " + Largest); //Add to doc

            //Add to doc
            doc.add(reportType);
            doc.add(introduction);
            doc.add(table);
            doc.add(body);
            
            //Close
            doc.close();

            System.out.println("Monthly Prefferred Rooms Printed");
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
