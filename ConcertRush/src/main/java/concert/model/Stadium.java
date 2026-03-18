package concert.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

// Thread-safe singleton representing the venue; owns seats and booking logs.
public class Stadium {

    private static Stadium instance;

    private final List<Seat> seats = new ArrayList<>();
    private final List<String> bookingLog = Collections.synchronizedList(new ArrayList<>());

    private final AtomicInteger soldCount = new AtomicInteger(0);

    private Stadium(int totalSeats) {
        // Simple seat layout: A1–A20, B1–B20, ... until the requested count is reached.
        char section = 'A';
        int count = 0;
        while (count < totalSeats) {
            for (int i = 1; i <= 20 && count < totalSeats; i++, count++) {
                seats.add(new Seat(String.valueOf(section), i));
            }
            section++;
        }
    }

    public static synchronized Stadium getInstance(int totalSeats) {
        if (instance == null) {
            instance = new Stadium(totalSeats);
        }
        return instance;
    }

    public boolean tryBookSeat(String fanName) {
        // Shuffle to mimic random seat selection across fans.
        Collections.shuffle(seats);

        for (Seat seat : seats) {
            if (seat.book()) {
                String log = fanName + " booked " + seat.getLabel();
                bookingLog.add(log);
                soldCount.incrementAndGet();
                return true;
            }
        }
        return false;
    }

    public int getSoldSeatsCount() {
        return soldCount.get();
    }

    public int getTotalSeats() {
        return seats.size();
    }

    public List<String> getRecentBookings(int max) {
        synchronized (bookingLog) {
            int start = Math.max(0, bookingLog.size() - max);
            return new ArrayList<>(bookingLog.subList(start, bookingLog.size()));
        }
    }
}
