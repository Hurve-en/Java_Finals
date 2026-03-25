package concert.model;

// Notes which fan got which seat.
public class Ticket {

    private final String seatLabel;
    private final String fanName;

    public Ticket(String seatLabel, String fanName) {
        this.seatLabel = seatLabel;
        this.fanName = fanName;
    }

    @Override
    public String toString() {
        return fanName + " → Seat " + seatLabel;
    }
}
