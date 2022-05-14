package model;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import java.io.FileOutputStream;
import java.sql.*;
import java.util.logging.*;

/**
 *
 * @author star
 */
public class ReportManager {
    
    private Document docs = new Document(); 
    private String email;
    private String role;
    
    public ReportManager() { }
    
    public void printReport(String email, String role,
                                Connection conn) throws DocumentException {
        
        this.email = email;
        this.role = role;
        
        String file = "C:\\Users\\Darla\\Downloads\\" + role + "-" + email + "-report.pdf";
//        Document docs = new Document();
        
        try {
            PdfWriter writer = PdfWriter.getInstance(docs, new FileOutputStream(file));
            HeaderFooterPageEvent event = new HeaderFooterPageEvent();
            writer.setPageEvent(event);
            
            docs.open();
            
            // user table w/ header rows
            PdfPTable tableUser = new PdfPTable(3);
            PdfPCell hr1 = new PdfPCell(new Phrase("Email"));
            PdfPCell hr2 = new PdfPCell(new Phrase("Password"));
            PdfPCell hr3 = new PdfPCell(new Phrase("Role"));
            
            hr1.setPadding(4);
            hr2.setPadding(4);
            hr3.setPadding(4);
            
            tableUser.addCell(hr1);
            tableUser.addCell(hr2);
            tableUser.addCell(hr3);
            
            // if admin requests the PDF
            if (role.equals("Admin")) {
                
                // retrieve ResultSet
                String query = "SELECT * FROM hotelbookingdb.user_table";
                PreparedStatement ps = conn.prepareStatement(query);
                ResultSet rs = ps.executeQuery();
                
                // retrieve each record in ResultSet
                while (rs.next()) {
                    PdfPCell c1 = new PdfPCell(new Phrase(rs.getString("email")));
                    PdfPCell c2 = new PdfPCell(new Phrase("X"));    // for password column
                    PdfPCell c3 = new PdfPCell(new Phrase(rs.getString("role")));
                    
                    c1.setPadding(4);
                    c2.setPadding(4);
                    c3.setPadding(4);
                    
                    tableUser.addCell(c1);
                    tableUser.addCell(c2);
                    tableUser.addCell(c3);
                }
            }
            
            // if guest requests the PF
            else if (role.equals("Guest")) {
                
                PdfPCell c1 = new PdfPCell(new Phrase(email));
                PdfPCell c2 = new PdfPCell(new Phrase("X"));    // for password column
                PdfPCell c3 = new PdfPCell(new Phrase(role));
                
                tableUser.addCell(c1);
                tableUser.addCell(c2);
                tableUser.addCell(c3);
            }
            
            docs.add(tableUser);
            docs.close();
        }
        
        catch (Exception e) {
            // TBA
        }
    }
    
    public class HeaderFooterPageEvent extends PdfPageEventHelper {

        @Override   // header
        public void onStartPage(PdfWriter writer, Document document) {
            PdfPTable tableHeader = new PdfPTable(1);
            Font fontTitle = FontFactory.getFont(FontFactory.HELVETICA, 28);
            
            PdfPCell cTitle = new PdfPCell(new Phrase("Hotel Booking Web App", fontTitle));
            PdfPCell cUser = new PdfPCell(new Phrase("Generated by: " + role + " " + email));
            
            cTitle.setBorder(Rectangle.NO_BORDER);
            cUser.setBorder(Rectangle.NO_BORDER);
            cUser.setPaddingBottom(20);
            
            tableHeader.addCell(cTitle);
            tableHeader.addCell(cUser);
            
            try {
                docs.add(tableHeader);
            } catch (DocumentException ex) {
                Logger.getLogger(ReportManager.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        @Override   // footer
        public void onEndPage(PdfWriter writer, Document document) {
            ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_CENTER, new Phrase("page " + document.getPageNumber()), 550, 30, 0);
        }
    }
}

// reference:
// HeaderFooterPageEvent: https://stackoverflow.com/questions/19856583/how-to-add-header-and-footer-to-my-pdf-using-itext-in-java