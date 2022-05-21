/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.sql.*;

/**
 *
 * @author star
 */
public class AdminManager {
    
    Connection conn;
    ResultSet records;
    String tableName;
    String dbTable;
    
    public AdminManager() { }
    
    public ResultSet getRecords(int start, int end,
                                    String tableName, Connection conn) {
        
        this.conn = conn;
        this.tableName = tableName;
        records = null;
        
        // TBU...
        
        switch (tableName) {
            case "Reserve":
                dbTable = "hotelbookingdb.reserve_table";
                break;
            case "Room":
                dbTable = "hotelbookingdb.room_table";
                break;
            case "Rate":
                dbTable = "hotelbookingdb.rate_table";
                break;
            case "User":
                dbTable = "hotelbookingdb.user_table";
                break;
            default:
                break;
        }
        
        try {
            String query;
            if (tableName.equals("Reserve") || tableName.equals("Room")) {
                query = "SELECT * FROM " + dbTable
                        // 'pending' > 'ongoing' > 'done' > check_in > check_out
                         + " ORDER BY reserve_status DESC, check_in ASC, check_out ASC"   
                         + " LIMIT " + (start - 1) + "," + end;
            } else {
                query = "SELECT * FROM " + dbTable
                               + " LIMIT " + (start - 1) + "," + end;
            }
            
            Statement s = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                                                ResultSet.CONCUR_READ_ONLY);
            records = s.executeQuery(query);
        }
        
        catch (SQLException sqle) {
            sqle.printStackTrace();
        }
        
        return records;
    }
    
    // edit/delete record methods
    // include confirmation of booking payment
    
    public boolean checkLast() {
        System.out.println("== am.checkLast() ===========================");
        boolean last = false;
        
        try {
            // NTS: ADJUST SQL COMMANDS FOR RESERVE & ROOM (BEC OF ORDER BY DATE)
            
            // retrieve last record in table
            String query = "";
            switch (tableName) {
                case "Reserve":
                    query = "SELECT * FROM " + dbTable
                            + " ORDER BY reserve_status ASC, check_in DESC,"
                            + " check_out DESC, room_no DESC LIMIT 1";
                    break;
                case "Room":
                    query = "SELECT * FROM " + dbTable
                            + " ORDER BY room_no DESC LIMIT 1";
                    break;
                case "User":
                    query = "SELECT * FROM " + dbTable
                            + " ORDER BY email DESC LIMIT 1";
                    break;
                default:
                    break;
            }
                
            Statement s = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                                                ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = s.executeQuery(query);
            
            while (rs.next() && records.next()) {
                records.last();
                
                System.out.println("rs email: " + rs.getString("email"));
                System.out.println("rs room_no: " + rs.getString("room_no"));
                System.out.println("records room_no: " + records.getString("email"));
                System.out.println("records room_no: " + records.getString("room_no"));
                
                switch (tableName) {
                    case "Reserve":
                        if (rs.getString("email").equals(records.getString("email"))
                                && rs.getString("room_no").equals(records.getString("room_no"))) {
                            last = true;
                        }
                        break;
                    case "Room":
                        if (rs.getString("room_no").equals(records.getString("room_no"))) {
                            last = true;
                        }
                        break;
                    case "User":
                        if (rs.getString("email").equals(records.getString("email"))) {
                            last = true;
                        }
                        break;
                    default:
                        break;
                }
            }
            
            // new:
            // check if last record of "records" == last record of entire/complete table
        }
        
        catch (SQLException sqle) {
            sqle.printStackTrace();
        }
        System.out.println("=============================================");
        return last;
    }
}

/*
references:
pagination for DB records: https://www.javatpoint.com/pagination-in-jsp
get last row of DB: https://stackoverflow.com/questions/5191503/how-to-select-the-last-record-of-a-table-in-sql
*/