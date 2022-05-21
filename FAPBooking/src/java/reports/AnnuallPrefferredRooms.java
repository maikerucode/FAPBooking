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
public class AnnuallPrefferredRooms {

    //Variables
    String email;
    String role;

    //type
    String firstType;
    String secondType;
    String thirdType;
    String fourthType;

    //quantity per type
    int firstCount;
    int secondCount;
    int thirdCount;
    int fourthCount;
    ResultSet result;

    //Document
    Document doc = new Document();

    //General Constructor
    public void AnnualPrefferredRooms() {
    }

    public void AnnualPrefferredRooms(String email, String role,
            String firstType, String secondType, String thirdType, String fourthType,
            int firstCount, int secondCount, int thirdCount, int forthCount, ResultSet result) {
        this.email = email;
        this.role = role;

        this.firstType = firstType;
        this.secondType = secondType;
        this.thirdType = thirdType;
        this.fourthType = fourthType;

        this.firstCount = firstCount;
        this.secondCount = secondCount;
        this.thirdCount = thirdCount;
        this.fourthCount = forthCount;

        //Debugging
        System.out.println("AnnualPrefferredRooms.java");
        System.out.println("Username: " + this.email + "\n");
        System.out.println("Role: " + this.role + "\n");

        //Quantity
        System.out.println("First Count: " + this.firstCount + "\n");
        System.out.println("Second Count: " + this.secondCount + "\n");
        System.out.println("Third Count: " + this.thirdCount + "\n");
        System.out.println("Forth Count" + this.fourthCount + "\n");

        //Type
        System.out.println("First Type: " + this.firstType + "\n");
        System.out.println("Second Type: " + this.secondType + "\n");
        System.out.println("Third Type: " + this.thirdType + "\n");
        System.out.println("Forth Type" + this.fourthType + "\n");

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
                    + email + "_" + dtf.format(now) + "AnnualPrefferredRooms.pdf"));

            //Directs PDF to currect project directory (Tentative)
            //PdfWriter.getInstance(doc, new FileOutputStream(currPath + "\\Admin" + uname + "Report.pdf"));
            //Header/Footer Event
            AnnuallPrefferredRooms.HeaderFooterPageEvent eve = new AnnuallPrefferredRooms.HeaderFooterPageEvent();
            writer.setPageEvent(eve);

            //PDF Open
            doc.open();
            Paragraph reportType = new Paragraph("Annual Prefferred Rooms: \n", headerFont);
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
            table.addCell(firstType);
            table.addCell(Integer.toString(firstCount));
            table.addCell(secondType);
            table.addCell(Integer.toString(secondCount));
            table.addCell(thirdType);
            table.addCell(Integer.toString(thirdCount));
            table.addCell(fourthType);
            table.addCell(Integer.toString(fourthCount));

            //Add to doc
            doc.add(reportType);
            doc.add(introduction);

            //Close
            doc.close();
            System.out.println("Annual Prefferred Rooms Printed");
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
