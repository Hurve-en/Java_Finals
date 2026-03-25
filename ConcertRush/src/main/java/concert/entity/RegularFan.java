package concert.entity;

import concert.model.Stadium;

public class RegularFan extends Fan {

    public RegularFan(String name, Stadium stadium) {
        super(name, stadium);
    }

    @Override
    protected boolean attemptToBook() {
        // Tries once; no retries.
        return stadium.tryBookSeat(name);
    }
}
