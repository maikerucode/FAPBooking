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
public class AccountDetails {

    //Variables
    String uname = "";
    String role = "";
    String home = "";
    String currPath = System.getProperty("user.dir");
    ResultSet result;

    //Document
    Document doc = new Document();

    //General Constructor
    public void AccountDetails() {
    }

    public void AccountDetails(String uname, String role, ResultSet result) {
        this.uname = uname;
        this.role = role;

        //Debugging
        System.out.println("AccountDetails.java");
        System.out.println("Username: " + this.uname);
        System.out.println("Role: " + this.role + "\n");
        System.out.println("Current path: " + currPath);

        //Date
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();

        //Fonts
        Font headerFont = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD, new BaseColor(200, 0, 0));

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
            PdfWriter writer = PdfWriter.getInstance(doc, new FileOutputStream("C:\\Users\\" + System.getProperty("user.name") + "\\Desktop\\"
                    + uname + "_" + dtf.format(now) + "_AccountDetails.pdf"));

            //Directs PDF to currect project directory (Tentative)
            //PdfWriter.getInstance(doc, new FileOutputStream(currPath + "\\Admin" + uname + "Report.pdf"));
            
            //Header/Footer Event
            HeaderFooterPageEvent eve = new HeaderFooterPageEvent();
            writer.setPageEvent(eve);

            //PDF Open
            doc.open();
            Paragraph par = new Paragraph();
            Paragraph reportType = new Paragraph("Account Details \n", headerFont);

            par.add(dtf.format(now) + "\n");

            par.add("Welcome\n");
            par.add("User: " + uname + "\n");
            par.add("Role: " + this.role + "\n");

            //Tables
            PdfPTable table = new PdfPTable(2);
            table.addCell("Name");
            table.addCell("Role");

            while (result.next()) {
                table.addCell(result.getString("username"));
                table.addCell(result.getString("role"));
            }

            //Add to doc
            doc.add(reportType);
            doc.add(par);
            doc.add(table);

            //Close
            doc.close();

            System.out.println("Account Details Printed");
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
