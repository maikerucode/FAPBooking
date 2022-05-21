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
            if (tableName.equals("Reserve")) {
                query = "SELECT * FROM " + dbTable
                        // 'pending' > 'ongoing' > 'done' > check_in > check_out > room_no
                         + " ORDER BY reserve_status DESC, check_in ASC, check_out ASC, room_no ASC"   
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
    
    public boolean deleteRecord(String tableName, Object val, Connection conn) {
        this.conn = conn;
        String col = "";
        
        switch (tableName) {
            case "Reserve": case "User":
                col = "email";
                break;
            default:
                break;
        }
        
        try {
            String query = "DELETE FROM " +  dbTable
                            + " WHERE " + col + " = ?";
            PreparedStatement ps = conn.prepareStatement(query);

            ps.setObject(1, val);
            ps.executeUpdate();
            
            System.out.println("record deleted!");
            return true;
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }
        
        return false;
    }
    
    // for reserve_table
    public boolean updateRecord(String tableName, Object email, 
                                 Object reserve_status, Object ref_no, Connection conn) {
        this.conn = conn;
        
        try {
            String query = "UPDATE " +  dbTable
                            + " SET reserve_status = ?"
                            + " AND ref_no = ?"
                            + " WHERE email = ?";
            PreparedStatement ps = conn.prepareStatement(query);

            ps.setObject(1, reserve_status);
            ps.setObject(2, ref_no);
            ps.setObject(3, email);
            ps.executeUpdate();
            
            System.out.println("record updated!");
            return true;
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }
        
        return false;
    }
    
    public boolean checkLast() {
//        System.out.println("== am.checkLast() ===========================");
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

                switch (tableName) {
                    case "Reserve":
                        if (rs.getString("email").equals(records.getString("email"))
                                && rs.getString("room_no").equals(records.getString("room_no"))) {
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
        }
        
        catch (SQLException sqle) {
            sqle.printStackTrace();
        }
        return last;
    }
}

/*
references:
pagination for DB records: https://www.javatpoint.com/pagination-in-jsp
get last row of DB: https://stackoverflow.com/questions/5191503/how-to-select-the-last-record-of-a-table-in-sql
*/