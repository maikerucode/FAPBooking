package model;

import java.time.DateTimeException;
import java.time.*;
import java.time.format.*;
import java.util.*;

/**
 *
 * @author star
 */
public class EntryValidator {
    
    LocalDate ldIn;
    LocalDate ldOut;
    LocalDate ldNow = LocalDate.now();
    
    // check if check-in & check-out dates are valid
    public String checkDates(String inMonth, String inDay, String inYear,
                                String outMonth, String outDay, String outYear) {
        
        try {
            // LocalDate throws a DTE if an invalid date value is passed
            ldIn = LocalDate.of(Integer.parseInt(inYear),
                    Integer.parseInt(inMonth), Integer.parseInt(inDay));
            ldOut = LocalDate.of(Integer.parseInt(outYear),
                    Integer.parseInt(outMonth), Integer.parseInt(outDay));
            
            System.out.println("check-in date: " + ldIn);
            System.out.println("check-out date: " + ldOut);
            System.out.println("present date: " + ldNow);
            
            // checks if (ldIn <= ldOut)
            if (ldIn.isBefore(ldOut) || ldIn.isEqual(ldOut)) {
                // checks if present/future date
                if (!ldNow.isAfter(ldIn)) {
                    return "valid";
                }
                return "past date";
            }
            return "invalid date range";
        }
        
        catch (DateTimeException e) {
            e.printStackTrace();
        }
        
        return "invalid date";
    }
        
    public boolean checkRoom() {
        
        // check room/s in database if available
        // create new java class for sql commands
        // also check if 0 value is provided for all room types
        
        return false;
    }
}

// References:
// https://www.baeldung.com/java-creating-localdate-with-values
