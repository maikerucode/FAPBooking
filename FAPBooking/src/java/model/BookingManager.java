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
    boolean availRooms;

    public BookingManager() {
    }

    // returns true if user has a booking w/ ongoing/pending payment
    public boolean checkOngoing(String email, Connection conn) {
        try {
            String query = "SELECT * FROM hotelbookingdb.reserve_table"
                    + " WHERE email = ?"
                    + " AND (reserve_status = 'Pending' OR reserve_status = 'Done')";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, email);

            ResultSet result = ps.executeQuery();

            if (result.next() == false) {
                return false;
            }
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }

        return true;
    }

    //checks all reservations made by the user
    public ResultSet userReservations(String email, Connection conn) {
        ResultSet records = null;

        if (conn != null) {
            try {
                String query = "SELECT * FROM hotelbookingdb.reserve_table"
                        + " WHERE email = ?";
                PreparedStatement ps = conn.prepareStatement(query);
                ps.setString(1, email);
                records = ps.executeQuery();
            } catch (SQLException sqle) {
                sqle.printStackTrace();
            }
        } else {
            System.out.println("userReservations is null: ");
        }
        return records;
    }

    // checks the database if there are available rooms at the given date
    public boolean checkBooking(Booking booking, Connection conn) {
        this.booking = booking;
        this.conn = conn;

        availSingle = checkRoom("Single", booking.getTypeSingle(), false, "");
        availDouble = checkRoom("Double", booking.getTypeDouble(), false, "");
        availTriple = checkRoom("Triple", booking.getTypeTriple(), false, "");
        availQuad = checkRoom("Quad", booking.getTypeQuad(), false, "");

        System.out.println("availSingle: " + availSingle);
        System.out.println("availDouble: " + availDouble);
        System.out.println("availTriple: " + availTriple);
        System.out.println("availQuad: " + availQuad);

        return (availSingle && availDouble
                && availTriple && availQuad);
    }

    public void book(String email) {
        getRoomRates();

        checkRoom("Single", booking.getTypeSingle(), true, email);
        checkRoom("Double", booking.getTypeDouble(), true, email);
        checkRoom("Triple", booking.getTypeTriple(), true, email);
        checkRoom("Quad", booking.getTypeQuad(), true, email);

        getTotalCharge(email);
    }
//  ==============================================================================

    // checks for available rooms of a specific room type
    public boolean checkRoom(String roomTypeName, int roomTypeVal,
            boolean addRoom, String email) {
        try {
            System.out.println("== bm.checkRooms() ==========================");
            // retrieve all records of the given room type that are open
            String query = "SELECT * FROM hotelbookingdb.room_table"
                    + " WHERE (room_type = ? AND room_status = 'Open')";
            PreparedStatement ps1 = conn.prepareStatement(query);
            ps1.setString(1, roomTypeName);
            resultRooms = ps1.executeQuery();

            int ctr = 0;

            System.out.println("roomTypeName: " + roomTypeName);
            System.out.println("roomTypeVal: " + roomTypeVal);

            while (resultRooms.next() && ctr != roomTypeVal) {
                System.out.println("--------------------------");
                System.out.println(roomTypeName + " ctr: " + ctr);

                // retrieve all ongoing/pending reservations for the room 
                query = "SELECT * FROM hotelbookingdb.reserve_table"
                        + " WHERE room_no = ?"
                        + " AND NOT check_in < ? AND NOT check_out > ?";
                PreparedStatement ps2 = conn.prepareStatement(query,
                        ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
                ps2.setString(1, resultRooms.getString("room_no"));
                ps2.setObject(2, booking.getBookDateIn().minusDays(30)); // prev 30 days
                ps2.setObject(3, booking.getBookDateOut().plusDays(30)); // next 30 days
                resultReserve = ps2.executeQuery();

                // if there are no existing reservations for that room
                if (resultReserve.next() == false) {
                    System.out.println("resultReserve is empty...");
                    ctr++;
                    if (addRoom) {
                        addBooking(email);
                    }
                } // else, check for conflicting dates
                else {
                    System.out.println("resultReserve is not empty...");
                    do {
                        reserveDateIn = resultReserve.getDate("check_in")
                                .toLocalDate();
                        reserveDateOut = resultReserve.getDate("check_out")
                                .toLocalDate();

                        System.out.println("booking.getBookDateIn(): " + booking.getBookDateIn());
                        System.out.println("reserveDateOut: " + reserveDateOut);
                        System.out.println("booking.getBookDateOut(): " + booking.getBookDateOut());
                        System.out.println("reserveDateIn: " + reserveDateIn);

                        /* check if bookDateIn > reserveDateOut
                            or bookDateOut < reserveDateIn */
                        if ((booking.getBookDateIn().isAfter(reserveDateOut))
                                || (booking.getBookDateOut().isBefore(reserveDateIn))) {
                            ctr++;
                            if (addRoom) {
                                addBooking(email);
                            }
                        }
                    } while (resultReserve.next() && ctr < roomTypeVal);
                }
            }

            availRooms = (roomTypeVal != 0 && ctr == roomTypeVal)
                    || (roomTypeVal == 0);

            System.out.println("final ctr: " + ctr);
            System.out.println("availRooms: " + availRooms);
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }
        return availRooms;
    }

    public void addBooking(String email) {
        try {
            /* email | room_no | room_type | check_in | check_out
                 | total_charge | reserve_status */
            String query = "INSERT INTO hotelbookingdb.reserve_table"
                    + " VALUES(?,?,?,?,'','Pending','')";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, email);
            ps.setString(2, resultRooms.getString("room_no"));
            ps.setObject(3, booking.getBookDateIn());
            ps.setObject(4, booking.getBookDateOut());

            ps.executeUpdate();

            System.out.println("initial total_charge: " + total_charge);

            switch (resultRooms.getString("room_type")) {
                case "Single":
                    total_charge = total_charge + rateSingle;
                    System.out.println("total_charge + rateSingle: " + total_charge);
                    break;
                case "Double":
                    total_charge = total_charge + rateDouble;
                    System.out.println("total_charge + rateDouble: " + total_charge);
                    break;
                case "Triple":
                    total_charge = total_charge + rateTriple;
                    System.out.println("total_charge + rateTriple: " + total_charge);
                    break;
                case "Quad":
                    total_charge = total_charge + rateQuad;
                    System.out.println("total_charge + rateQuad: " + total_charge);
                    break;
                default:
                    break;
            }
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }
    }

    public void getTotalCharge(String email) {
        try {
            String query = "UPDATE hotelbookingdb.reserve_table"
                    + " SET total_charge = ? WHERE email = ?";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, total_charge);
            ps.setString(2, email);

            ps.executeUpdate();
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }
    }

    public void getRoomRates() {
//        System.out.println("-- getRoomRates() --------");
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM hotelbookingdb.rate_table"
                    + " WHERE room_type = 'Single'");
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

//            System.out.println("rateSingle: " + rateSingle);
//            System.out.println("rateDouble: " + rateDouble);
//            System.out.println("rateTriple: " + rateTriple);
//            System.out.println("rateQuad: " + rateQuad);
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }
//        System.out.println("--------------------------");
    }
}

// References:
// https://stackoverflow.com/questions/325933/determine-whether-two-date-ranges-overlap
// https://stackoverflow.com/questions/14208958/select-data-from-date-range-between-two-dates
// https://javarevisited.blogspot.com/2016/10/how-to-check-if-resultset-is-empty-in-Java-JDBC.html
// https://stackoverflow.com/questions/6367737/resultset-exception-set-type-is-type-forward-only-why
