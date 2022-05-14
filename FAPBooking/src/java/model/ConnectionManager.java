package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author star
 */
public class ConnectionManager {
    
    public Connection conn;
    
    public Connection establishConn(String driver, String username, String password,
                    String driverUrl, String hostname, String port, String database) {
        
        conn = null;
        
        try {
            // Load Driver
            Class.forName(driver);
        
            // Establish Connection
            StringBuffer url;
            
            // for MySQL
            if (driver.equals("com.mysql.jdbc.Driver")
                    || driver.equals("com.mysql.cj.jdbc.Driver")) {
                url = new StringBuffer(driverUrl)
                        .append("://")
                        .append(hostname)
                        .append(":")
                        .append(port)
                        .append("/")
                        .append(database)
                        .append("?useSSL=false&allowPublicKeyRetrieval=true");
            }
            
            else {
                url = new StringBuffer(driverUrl)
                        .append("://")
                        .append(hostname)
                        .append(":")
                        .append(port)
                        .append("/")
                        .append(database);
            }
                        
            conn = DriverManager.getConnection(url.toString(), username, password);
        }
        
        catch (SQLException sqle) {
            System.out.println("SQLException error occured - "
                                + sqle.getMessage());
        }
        
        catch (ClassNotFoundException nfe) {
            System.out.println("ClassNotFoundException error occured - "
                                + nfe.getMessage());
        }
        
        return conn;
    }
}
