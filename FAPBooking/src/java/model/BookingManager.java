package model;

import java.sql.*;
import java.time.*;

/**
 *
 * @author star
 */
public class BookingManager {
    Booking booking;
    Connection conn;
    
    private int rateSingle;
    private int rateDouble;
    private int rateTriple;
    private int rateQuad;
    private int total_charge;
    
    private ResultSet resultRooms;
    private ResultSet resultReserve;
    
    private LocalDate reserveDateIn;
    private LocalDate reserveDateOut;
    
    boolean availSingle, availDouble, availTriple, availQuad;
    boolean availRooms = true;
    
    public BookingManager() { }
    
    // returns true if user has an ongoing room reservation
    public boolean checkOngoing(String email, Connection conn) {
        try {
            String query = "SELECT * FROM hotelbookingdb.reserve_table"
                        + " WHERE email = ? AND reserve_status = 'Ongoing'";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, email);

            ResultSet result = ps.executeQuery();
            
            if(result.next() == false) {
                return false;
            }
        }
        
        catch (SQLException sqle) {
            sqle.printStackTrace();
        }
                
        return true;
    }
    
    // checks the database if there are available rooms at the given date
    public boolean checkBooking(Booking booking, Connection conn) {
        
        this.booking = booking;
        this.conn = conn;
        
        availSingle = checkRoom("Single", booking.getTypeSingle());
        availDouble = checkRoom("Double", booking.getTypeDouble());
        availTriple = checkRoom("Triple", booking.getTypeTriple());
        availQuad = checkRoom("Quad", booking.getTypeQuad());
        
        System.out.println("availSingle: " + availSingle);
        System.out.println("availDouble: " + availDouble);
        System.out.println("availTriple: " + availTriple);
        System.out.println("availQuad: " + availQuad);
        
//        booking.setResultRooms(resultRooms);
        booking.setResultReserve(resultReserve);
        
        return (availSingle && availDouble
                    && availTriple && availQuad);
    }
    
    // checks for available rooms of a specific room type
    public boolean checkRoom(String roomTypeName, int roomTypeVal) {
        try {
            // retrieve all records of the given room type that are open
            String query = "SELECT * FROM hotelbookingdb.room_table"
                        + " WHERE (room_type = ? AND room_status = 'Open')";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, roomTypeName);

            resultRooms = ps.executeQuery();
            int ctr = 0;
            System.out.println("roomTypeName: " + roomTypeName);
            System.out.println("roomTypeVal: " + roomTypeVal);
            
            while (resultRooms.next() && ctr != roomTypeVal) {
                System.out.println("--------------------------");
                System.out.println(roomTypeName + " ctr: " + ctr);
                
                LocalDate from = booking.getBookDateIn().minusDays(30); // prev 30 days
                LocalDate to = booking.getBookDateOut().plusDays(30); // next 30 days
                System.out.println("LocalDate from: " + from);
                System.out.println("LocalDate to: " + to);

                // retrieve all records for the room 
                query = "SELECT * FROM hotelbookingdb.reserve_table"
                        + " WHERE room_no = ?"
                        + " AND NOT (check_in > ?  OR check_out < ?)";

                ps = conn.prepareStatement(query, 
                        ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
                ps.setString(1, resultRooms.getString("room_no"));
                ps.setObject(2, from);
                ps.setObject(3, to); 

                resultReserve = ps.executeQuery();
                
                // if there are no existing reservations for that room
                if (resultReserve.next() == false) {
                    ctr++;
                } 

                // else, check for conflicting dates
                else {
                    do {
                        reserveDateIn = resultReserve.getDate("check_in")
                                                    .toLocalDate();
                        reserveDateOut = resultReserve.getDate("check_out")
                                                    .toLocalDate();

                        /* check if bookDateIn > reserveDateOut
                            or bookDateOut < reserveDateIn */
                        if ((booking.getBookDateIn().isAfter(reserveDateOut)) 
                                || (booking.getBookDateIn().isBefore(reserveDateIn))) {
                            ctr++;
                        }
                    } while (resultReserve.next() && ctr < roomTypeVal);
                }
            }
            
            if (ctr == roomTypeVal) {
                availRooms = availRooms || true;
            }
            
            System.out.println("final ctr: " + ctr);
            System.out.println("availRooms: " + availRooms);
        }

        catch (SQLException sqle) {
            sqle.printStackTrace();
        }
        
        return availRooms;
    }
    
    // ====================================================================
    /* in reserve_table, update record w/ the finalized total charges
       if user has checked out */
    
    // performs the actual booking
    // nts: include the computation of expenses
    public void book(String email) {
        bookRooms("Single", booking.getTypeSingle(), email);
        bookRooms("Double", booking.getTypeDouble(), email);
        bookRooms("Triple", booking.getTypeTriple(), email);
        bookRooms("Quad", booking.getTypeQuad(), email);
        
        getTotalCharges(email);
        
        // close all resultsets?
    }
    
    public void bookRooms(String roomTypeName, int roomTypeVal, String email) {
        try {
            int ctr = 0;
            getRoomRates(); // get the room rates from the database
            
            // resultRooms
            String query = "SELECT * FROM hotelbookingdb.room_table"
                        + " WHERE (room_type = ? AND room_status = 'Open')";
            PreparedStatement ps = conn.prepareStatement(query,
                    ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ps.setString(1, roomTypeName);
            resultRooms = ps.executeQuery();
            
            // resultReserve
            resultReserve = booking.getResultReserve();
            
            // reset pointers to default position
            resultRooms.beforeFirst(); 
            resultReserve.beforeFirst();
            System.out.println("==========================");
            
            while (resultRooms.next() && ctr != roomTypeVal) {
                // if there are no existing reservations for that room
                if (resultReserve.next() == false) {
                    addBooking(email);
                    ctr++;
                }
                
                // else, check for conflicting dates
                else {
                    do {
                        /* check if bookDateIn > reserveDateOut
                            or bookDateOut < reserveDateIn */
                        if ((booking.getBookDateIn().isAfter(reserveDateOut)) 
                                || (booking.getBookDateOut().isBefore(reserveDateIn))) {
                            addBooking(email);
                            ctr++;
                        }
                    } while (resultReserve.next() && ctr < roomTypeVal);
                }
            }
            
            System.out.println("--------------------------");
            System.out.println("bookRooms");
            System.out.println("roomTypeVal " + roomTypeVal);
            System.out.println("ctr: " + ctr);
        }
        
        catch (SQLException sqle) {
            sqle.printStackTrace();
        }
    }
    
    public void addBooking(String email) {
        try {
            /* email | room_no | room_type | check_in | check_out
                 | total_charge | reserve_status */
            
            // add while loop/s here to reserve based on booking info
            String query = "INSERT INTO hotelbookingdb.reserve_table"
                            + " VALUES(?,?,?,?,?,'Ongoing')";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, email);
            ps.setString(2, resultRooms.getString("room_no"));
            ps.setObject(3, booking.getBookDateIn());
            ps.setObject(4, booking.getBookDateOut()); 
            ps.setString(5, "");
            
            ps.executeUpdate();

            switch (resultRooms.getString("room_type")) {
                case "Single":
                    total_charge = total_charge + rateSingle;
                    break;
                case "Double":
                    total_charge = total_charge + rateDouble;
                    break;
                case "Triple":
                    total_charge = total_charge + rateTriple;
                    break;
                case "Quad":
                    total_charge = total_charge + rateQuad;
                    break;
                default:
                    break;
            }
        }
        
        catch (SQLException sqle) {
            sqle.printStackTrace();
        }
    }
    
    public void getTotalCharges(String email) {        
        try {
            String query = "UPDATE hotelbookingdb.reserve_table"
                    + " SET total_charge = ? WHERE email = ?";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, total_charge);
            ps.setString(2, email);
            
            ps.executeUpdate();
        }
        
        catch (SQLException sqle) {
            sqle.printStackTrace();
        }
    }
    
    public void getRoomRates() {
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM hotelbookingdb.rate_table");
            rs.next();
            rateSingle = Integer.parseInt(rs.getString("room_rate"));
            
            rs = stmt.executeQuery("SELECT * FROM hotelbookingdb.rate_table"
                    + " WHERE room_type = 'Double'");
            rs.next();
            rateDouble = Integer.parseInt(rs.getString("room_rate"));
            
            rs = stmt.executeQuery("SELECT * FROM hotelbookingdb.rate_table"
                    + " WHERE room_type = 'Triple'");
            rs.next();
            rateTriple = Integer.parseInt(rs.getString("room_rate"));
            
            rs = stmt.executeQuery("SELECT * FROM hotelbookingdb.rate_table"
                    + " WHERE room_type = 'Quad'");
            rs.next();
            rateQuad = Integer.parseInt(rs.getString("room_rate"));
            
            System.out.println("rateSingle: " + rateSingle);
            System.out.println("rateDouble: " + rateDouble);
            System.out.println("rateTriple: " + rateTriple);
            System.out.println("rateQuad: " + rateQuad);
        }
        
        catch (SQLException sqle) {
            sqle.printStackTrace();
        }
    }
}

// References:
// https://stackoverflow.com/questions/325933/determine-whether-two-date-ranges-overlap
// https://stackoverflow.com/questions/14208958/select-data-from-date-range-between-two-dates
// https://javarevisited.blogspot.com/2016/10/how-to-check-if-resultset-is-empty-in-Java-JDBC.html
// https://stackoverflow.com/questions/6367737/resultset-exception-set-type-is-type-forward-only-why