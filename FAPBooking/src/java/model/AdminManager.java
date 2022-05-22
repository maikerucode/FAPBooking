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
    
    public AdminManager() { }
    
    public ResultSet getRecords(int start, int end,
                                    String tableName, Connection conn) {
        this.conn = conn;
        this.tableName = tableName;
        records = null;
        
        try {
            String query;
            if (tableName.equals("Reserve")) {
                query = "SELECT * FROM " + getDBTable(tableName)
                        // 'pending' > 'ongoing' > 'done' > check_in > check_out > email > room_no
                         + " ORDER BY reserve_status DESC, check_in, check_out, email, room_no"   
                         + " LIMIT " + (start - 1) + "," + end;
            } else if (tableName.equals("Rate")) {
                query = "SELECT * FROM " + getDBTable(tableName)
                         + " ORDER BY CAST(room_rate AS UNSIGNED) ASC"
                         + " LIMIT " + (start - 1) + "," + end;
            } else {
                query = "SELECT * FROM " + getDBTable(tableName)
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
            String query = "DELETE FROM " +  getDBTable(tableName)
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
    public boolean updateReserve(String tableName, String email, 
                                 String reserve_status, String ref_no, Connection conn) {
        this.conn = conn;
        
        try {
            String query = "UPDATE " + getDBTable(tableName)
                            + " SET reserve_status = ?, ref_no = ?"
                            + " WHERE email = ?";
            PreparedStatement ps = conn.prepareStatement(query);
            
            ps.setString(1, reserve_status);
            ps.setString(2, ref_no);
            ps.setString(3, email);
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
            // retrieve last record in table
            String query = "";
            switch (tableName) {
                case "Reserve":
                    query = "SELECT * FROM " + getDBTable(tableName)
                            + " ORDER BY reserve_status ASC, check_in DESC,"
                            + " check_out DESC, room_no DESC LIMIT 1";
                    break;
                case "User":
                    query = "SELECT * FROM " + getDBTable(tableName)
                            + " ORDER BY email DESC LIMIT 1";
                    break;
                default:
                    break;
            }
                
            Statement s = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                                                ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = s.executeQuery(query);
            
            if (!rs.next()) {
                last = true;
            } else {
                do {
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
                } while (rs.next() && records.next()); 
            }
        }
        
        catch (SQLException sqle) {
            sqle.printStackTrace();
        }
        return last;
    }
    
    
    // table name in database for SQL query
    public String getDBTable(String tableName) {
        String dbTable = "";
        
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
        return dbTable;
    }
}

/*
references:
pagination for DB records: https://www.javatpoint.com/pagination-in-jsp
get last row of DB: https://stackoverflow.com/questions/5191503/how-to-select-the-last-record-of-a-table-in-sql
*/