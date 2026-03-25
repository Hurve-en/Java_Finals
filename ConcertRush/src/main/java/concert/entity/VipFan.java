package concert.entity;

import concert.model.Stadium;

// VIP fan that retries a few times.
public class VipFan extends Fan {

    public VipFan(String name, Stadium stadium) {
        super(name, stadium);
    }

    @Override
    protected boolean attemptToBook() {
        // Tries up to three times.
        for (int attempt = 1; attempt <= 3; attempt++) {
            if (stadium.tryBookSeat(name)) {
                return true;
            }
            // Small pause between attempts.
            try {
                Thread.sleep(5);
            } catch (InterruptedException ignored) {
            }
        }
        return false;
    }
}
