/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package reports;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import java.io.*;
import java.sql.*;
import java.time.*;
import java.time.format.*;
import java.util.logging.*;
import model.ReportManager;

/**
 *
 * @author Lenovo
 */
public class AnnualRevenue {
    
    double totalRevenue = 0;
    int totalRoomsSold = 0;
    
    Connection conn;
    
    //Document
    Document doc = new Document();

    public AnnualRevenue() { }

    public void printAnnualRevenue(String email, String role,
                                    String year, String roomType, Connection conn) {
        this.conn = conn;
        System.out.println("AnnualRevenue.java");
        
        //Compute for the annual revenue
        computeAnnualRevenue(year,roomType);

        //Debugging
        System.out.println("== printAnnualRevenue() ==========================");
        System.out.println("Username: " + email + "\n");
        System.out.println("Role: " + role + "\n");
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
                    + email + "_" + dtf.format(now) + "Annual" + roomType + "Revenue.pdf"));

            //Header/Footer Event
            AnnualRevenue.HeaderFooterPageEvent eve = new AnnualRevenue.HeaderFooterPageEvent();
            writer.setPageEvent(eve);

            //PDF Open
            doc.open();
            Paragraph reportType = new Paragraph("Annual Revenue: \n", headerFont);
            Paragraph introduction = new Paragraph();
            Paragraph body = new Paragraph();
            
            introduction.add(dtf.format(now) + "\n");
            
            //User
            introduction.add("Welcome\n");
            introduction.add("User: " + email + "\n");
            introduction.add("Role: " + role + "\n");
            
            //Body
            body.add("Total Revenue: ");
            body.add(Double.toString(this.totalRevenue) + "\n");
            body.add("Total Room Sold: ");
            body.add(Integer.toString(this.totalRoomsSold) + "\n");
            
            //Add to doc
            doc.add(reportType);
            doc.add(introduction);
            doc.add(body);
            
            //Close
            doc.close();

            System.out.println("Annual Revenue" + roomType + " Printed");
        } catch (Exception ex) {
            Logger.getLogger(AccountDetails.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
        }
    }
    
    public void computeAnnualRevenue(String year, String roomType) {
        try {
            // get room rate
            String q1 = "SELECT * FROM hotelbookingdb.rate_table"
                            + " WHERE room_type=?";
            PreparedStatement ps1 = this.conn.prepareStatement(q1);
            ps1.setString(1, roomType);
            ResultSet rateRecords = ps1.executeQuery();
            
            rateRecords.next();
            double roomRate = Double.parseDouble(rateRecords.getString("room_rate"));
            
            // get reservations
            String q2 = "SELECT COUNT(*) AS count_name FROM hotelbookingdb.reserve_table"
                            + " WHERE room_no IN ("
                            + "     SELECT room_no FROM hotelbookingdb.room_table"
                            + "     WHERE room_type=?"
                            + " ) AND '" + year + "-12-31' >= check_in"
                            + " AND check_out > '" + year + "-01-01'";
            PreparedStatement ps2 = this.conn.prepareStatement(q2);
            ps2.setString(1, roomType);
            ResultSet reserveRecords = ps2.executeQuery();
            
            if (reserveRecords.next()) {
                totalRoomsSold = reserveRecords.getInt("count_name");
            }
            
            totalRevenue = totalRoomsSold*roomRate;
            
            System.out.println("== computeAnnualRevenue() ==========================");
            System.out.println("roomRate: " + roomRate);
            System.out.println("totalRoomsSold: " + totalRoomsSold);
            System.out.println("totalRevenue: " + totalRevenue);
        } catch (SQLException sqle) {
            sqle.printStackTrace();
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

/* References:
overlapping dates query: https://stackoverflow.com/questions/28927400/sql-query-to-select-rows-where-the-year-is-between-two-dates
retrieve records based on values of another table: https://stackoverflow.com/questions/19163219/select-records-in-on-table-based-on-conditions-from-another-table
*/