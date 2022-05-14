package model;

import java.sql.*;
import java.time.*;

/**
 *
 * @author star
 */
public class BookingManager {
    double priceSingle = 10;
    double priceDouble = 20;
    double priceTriple = 30;
    double priceQuad = 40;
    // place these in a new database?
    
    private LocalDate bookDateIn;
    private LocalDate bookDateOut;
    Connection conn;
    String unavailRooms = "false";
    
//     ctr to check if all tentative room reservations are possible
//     if rooms are available on given date range & no conflict
//       with other reservations, then success
    
    // checks the database if there are available rooms at the given date
    public boolean checkBooking(int roomSingle, int roomDouble, int roomTriple,
                                    int roomQuad, LocalDate bookDateIn,
                                    LocalDate bookDateOut, Connection conn) {
        
        this.bookDateIn = bookDateIn;
        this.bookDateOut = bookDateOut;
        this.conn = conn;
        
        return checkRoom("Single", roomSingle) && checkRoom("Double", roomDouble)
                && checkRoom("Triple", roomTriple) && checkRoom("Quad", roomQuad);
    }
    
    // checks for available rooms of a specific room type
    public boolean checkRoom(String roomTypeName, int roomTypeVal) {
        try {
            int ctr = 0;

            while (ctr != roomTypeVal || unavailRooms.equals("false")) {
                // retrieve all records of the given room type that are open
                String query = "SELECT * FROM hotelbookingdb.room_table"
                            + " WHERE (room_type = ? AND room_status = 'Open')";
                PreparedStatement ps = conn.prepareStatement(query);
                ps.setString(1, roomTypeName);

                ResultSet resultRooms = ps.executeQuery();
                System.out.println("checkRoom() ctr: " + ctr);

                /* if there is a reservation for that room w/ coinciding dates,
                    check another room */
                while (resultRooms.next()) {
                    LocalDate from = bookDateIn.minusMonths(30); // prev 30 days
                    LocalDate to = bookDateOut.plusMonths(30); // next 30 days
                    System.out.println("LocalDate from: " + from);
                    System.out.println("LocalDate to: " + to);
                    
                    // retrieve all records for the room 
                    query = "SELECT * FROM hotelbookingdb.reserve_table"
                            + " WHERE room_no = ?"
                            + " AND NOT (check_in > ?  OR check_out < ?)";

                    ps = conn.prepareStatement(query);
                    ps.setString(1, resultRooms.getString("room_no"));
                    ps.setObject(2, from);
                    ps.setObject(3, to); 

                    ResultSet resultReserve = ps.executeQuery();

                    // check for conflicting dates
                    while (resultReserve.next() && ctr < roomTypeVal) {
                        LocalDate reserveDateIn = resultReserve.getDate("check_in")
                                                    .toLocalDate();
                        LocalDate reserveDateOut = resultReserve.getDate("check_out")
                                                    .toLocalDate();

                        // check if bookDateIn > reserveDateOut or bookDateOut < reserveDateIn
                        if ((bookDateIn.isAfter(reserveDateOut)) 
                                || (bookDateOut.isBefore(reserveDateIn))) {
                            ctr++;
                            return true;
                        }
                    }
                }
            }

            /* if number of available rooms do not satisfy the number 
                of user's requested rooms of a specific room type */
            if (ctr != roomTypeVal) {
                unavailRooms = "true";
            }
        }

        catch (SQLException sqle) {
            sqle.printStackTrace();
        }
        
        return false;
    }
    
    /* in reserve_table, update record w/ the finalized total charges
       if user has checked out */
    // performs the actual booking
    
    public void book() {
        // TBA...
    }
    
    // nts: include the computation of expenses
}

// References:
// https://stackoverflow.com/questions/325933/determine-whether-two-date-ranges-overlap
// https://stackoverflow.com/questions/14208958/select-data-from-date-range-between-two-dates