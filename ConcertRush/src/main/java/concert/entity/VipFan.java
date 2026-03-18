package concert.entity;

import concert.model.Stadium;

// VIP fan with a slightly more persistent retry strategy.
public class VipFan extends Fan {

    public VipFan(String name, Stadium stadium) {
        super(name, stadium);
    }

    @Override
    protected boolean attemptToBook() {
        // VIP fans try up to 3 times (simulate priority / faster retries)
        for (int attempt = 1; attempt <= 3; attempt++) {
            if (stadium.tryBookSeat(name)) {
                return true;
            }
            // tiny delay to simulate thinking / retry
            try {
                Thread.sleep(5);
            } catch (InterruptedException ignored) {
            }
        }
        return false;
    }
}
