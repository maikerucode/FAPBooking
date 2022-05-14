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
    boolean availSingle, availDouble, availTriple, availQuad;
    boolean availRooms = true;
    
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
        
        System.out.println("BM roomSingle: " + roomSingle);
        System.out.println("BM roomDouble: " + roomDouble);
        System.out.println("BM roomTriple: " + roomTriple);
        System.out.println("BM roomQuad: " + roomQuad);
        
        availSingle = checkRoom("Single", roomSingle);
        availDouble = checkRoom("Double", roomDouble);
        availTriple = checkRoom("Triple", roomTriple);
        availQuad = checkRoom("Quad", roomQuad);
        
        System.out.println("availSingle: " + availSingle);
        System.out.println("availDouble: " + availDouble);
        System.out.println("availTriple: " + availTriple);
        System.out.println("availQuad: " + availQuad);
        
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

            ResultSet resultRooms = ps.executeQuery();
            int ctr = 0;
            System.out.println("roomTypeName: " + roomTypeName);
            System.out.println("roomTypeVal: " + roomTypeVal);
            
            while (resultRooms.next() && ctr != roomTypeVal) {
                System.out.println("--------------------------");
                System.out.println(roomTypeName + " ctr: " + ctr);
                
                LocalDate from = bookDateIn.minusDays(30); // prev 30 days
                LocalDate to = bookDateOut.plusDays(30); // next 30 days
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
                
                System.out.println("resultRooms: " + resultRooms.next());
                System.out.println("resultReserve: " + resultReserve.next());
                
                // if there are no existing reservations for that room
                if (resultReserve.next() == false) {
                    ctr++;
                } 

                // else, check for conflicting reservation dates
                else {
                    while (resultReserve.next() && ctr < roomTypeVal) {
                        LocalDate reserveDateIn = resultReserve.getDate("check_in")
                                                    .toLocalDate();
                        LocalDate reserveDateOut = resultReserve.getDate("check_out")
                                                    .toLocalDate();

                        /* check if bookDateIn > reserveDateOut
                            or bookDateOut < reserveDateIn */
                        if ((bookDateIn.isAfter(reserveDateOut)) 
                                || (bookDateOut.isBefore(reserveDateIn))) {
                            ctr++;
                        }
                    }
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