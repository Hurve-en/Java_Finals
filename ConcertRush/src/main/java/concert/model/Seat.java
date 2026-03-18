package concert.model;

// Represents a single seat in the stadium with a section/number label.
public class Seat {

    private final String section;
    private final int number;
    private boolean booked = false;

    public Seat(String section, int number) {
        this.section = section;
        this.number = number;
    }

    // Synchronized so only one fan can grab the seat at a time.
    public synchronized boolean book() {
        if (!booked) {
            booked = true;
            return true;
        }
        return false;
    }

    public String getLabel() {
        return section + "-" + number;
    }

    @Override
    public String toString() {
        return getLabel() + (booked ? " (booked)" : " (available)");
    }
}
