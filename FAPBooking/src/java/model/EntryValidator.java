package model;

import java.time.DateTimeException;
import java.time.*;
import static java.time.temporal.ChronoUnit.DAYS;

/**
 *
 * @author star
 */
public class EntryValidator {
    
    LocalDate ldIn;
    LocalDate ldOut;
    LocalDate ldNow = LocalDate.now();
    
    // check if check-in & check-out dates are valid
    public String checkDates(int inMonth, int inDay, int inYear,
                                int outMonth, int outDay, int outYear) {
        
        try {
            // LocalDate throws a DTE if an invalid date value is passed
            ldIn = LocalDate.of(inYear, inMonth, inDay);
            ldOut = LocalDate.of(outYear, outMonth, outDay);
            
            System.out.println("check-in date: " + ldIn);
            System.out.println("check-out date: " + ldOut);
            System.out.println("present date: " + ldNow);
            
            // check if (ldIn <= ldOut)
            if (ldIn.isBefore(ldOut) || ldIn.isEqual(ldOut)) {
                // check if present/future date
                if (!ldNow.isAfter(ldIn)) {
                    // check if not more than max of 30 days
                    if (DAYS.between(ldIn, ldOut) <= 30) {
                        return "valid";
                    }
                    return "max booking length";
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
}

// Reference:
// https://www.baeldung.com/java-creating-localdate-with-values
