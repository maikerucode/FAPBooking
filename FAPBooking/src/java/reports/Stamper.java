/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package reports;

import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import java.io.FileOutputStream;
import java.time.LocalDateTime;
import static java.time.LocalDateTime.now;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Lenovo
 */
public class Stamper {

    //Variables
    String uname = "";
    String role = "";
    String reportType = "";

    public void Stamper() {
    }

    public void Stamper(String uname, String role, String reportType) {

        this.uname = uname;
        this.role = role;
        this.reportType = reportType;
        
        //Debugging
        System.out.println("Stamper.java");
        System.out.println("Username: " + this.uname);
        System.out.println("Role: " + this.role + "\n");
        System.out.println("Report Type: " + this.reportType);

        //Date
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();

        try {

            //PDFReader
            System.out.println("Reader: " + "C:\\Users\\" + System.getProperty("user.name") + "\\Desktop\\"
                    + uname + "_" + dtf.format(now) + "_" + reportType + ".pdf");
            PdfReader reader = new PdfReader("C:\\Users\\" + System.getProperty("user.name") + "\\Desktop\\"
                    + uname + "_" + dtf.format(now) + "_" + reportType + ".pdf");

            //PDFStamper
            System.out.println(
                    "IsTampered: " + reader.isTampered());
            PdfStamper stamper = new PdfStamper(reader, new FileOutputStream("C:\\Users\\" + System.getProperty("user.name") + "\\Desktop\\"
                    + uname + "_" + dtf.format(now) + "_" + reportType + "_Tampered.pdf"));

            System.out.println(
                    "IsTampered: " + reader.isTampered());
            Map<String, String> info = reader.getInfo();

            info.put(
                    "Author", role + "_" + uname);
            info.put(
                    "Title", "AccountDetails" + "_" + role + "_" + uname);
            info.put(
                    "Subject", "User's Account Details");
            stamper.setMoreInfo(info);

        } catch (Exception ex) {
            Logger.getLogger(Stamper.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
        }

    }

}
