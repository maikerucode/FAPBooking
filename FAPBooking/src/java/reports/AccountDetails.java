/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package reports;

//Imports
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.Font;
import com.itextpdf.text.BaseColor;

import java.io.FileNotFoundException;

import java.io.FileOutputStream;
import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;
import java.util.logging.Logger;

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

        //Document
        Document doc = new Document();

        //Fonts
        //Sample
//        Font[] fonts = {
//            new Font(),
//            new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD, new BaseColor(0, 0, 0))
//        };

        Font headerFont = new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD, new BaseColor(200,0,0));

        /*
            Font("Font-Fam", ""Font-size", "Font-type", "BaseColor(R,G,B)")
                RGB Max Val - 255
                
            Default
              Font-Fam: Helvetica
              Font-size: 12
              color: black
         */
        
        try {
            //Directs PDF to Desktop
            PdfWriter.getInstance(doc, new FileOutputStream("C:\\Users\\" + System.getProperty("user.name") + "\\Desktop\\"
                    + uname + "_" + dtf.format(now) + "AccountDetails.pdf"));

            //Directs PDF to currect project directory (Tentative)
            //PdfWriter.getInstance(doc, new FileOutputStream(currPath + "\\Admin" + uname + "Report.pdf"));
           
            //PDF Formulation
            doc.open();
            Paragraph par = new Paragraph();
            Paragraph header = new Paragraph("Account Details Report \n", headerFont);
            
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
            doc.add(header);
            doc.add(par);
            doc.add(table);
            doc.close();

            System.out.println("Account Details Printed");
        } catch (Exception ex) {
            Logger.getLogger(AccountDetails.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
        }
    }

}
