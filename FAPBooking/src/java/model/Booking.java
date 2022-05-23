package model;

import java.time.LocalDate;

/**
 *
 * @author star
 */
public class Booking {

    private int typeSingle;
    private int typeDouble;
    private int typeTriple;
    private int typeQuad;

    private LocalDate bookDateIn;
    private LocalDate bookDateOut;

    private int total_charge;

    public Booking() {
    }

    public int getTypeSingle() {
        return typeSingle;
    }

    public void setTypeSingle(int typeSingle) {
        this.typeSingle = typeSingle;
    }

    public int getTypeDouble() {
        return typeDouble;
    }

    public void setTypeDouble(int typeDouble) {
        this.typeDouble = typeDouble;
    }

    public int getTypeTriple() {
        return typeTriple;
    }

    public void setTypeTriple(int typeTriple) {
        this.typeTriple = typeTriple;
    }

    public int getTypeQuad() {
        return typeQuad;
    }

    public void setTypeQuad(int typeQuad) {
        this.typeQuad = typeQuad;
    }

    public int getSumRoomTypes() {
        return typeSingle + typeDouble + typeTriple + typeQuad;
    }

    public LocalDate getBookDateIn() {
        return bookDateIn;
    }

    public void setBookDateIn(int inYear, int inMonth, int inDay) {
        this.bookDateIn = LocalDate.of(inYear, inMonth, inDay);
    }

    public LocalDate getBookDateOut() {
        return bookDateOut;
    }

    public void setBookDateOut(int outYear, int outMonth, int outDay) {
        this.bookDateOut = LocalDate.of(outYear, outMonth, outDay);
    }

    public int getTotal_charge() {
        return total_charge;
    }
}
