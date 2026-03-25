package concert.entity;

import concert.model.Stadium;

public abstract class Fan implements Runnable {

    protected final String name;
    protected final Stadium stadium;

    // Base class for all fan types.
    public Fan(String name, Stadium stadium) {
        this.name = name;
        this.stadium = stadium;
    }

    @Override
    public void run() {
        // Let the fan run its own booking strategy.
        System.out.printf("%s started trying to book...%n", name);
        boolean success = attemptToBook();
        if (success) {
            System.out.printf("%s SUCCESSFULLY booked a ticket!%n", name);
        } else {
            System.out.printf("%s did NOT get a ticket.%n", name);
        }
    }

    // Subclasses decide how to retry.
    protected abstract boolean attemptToBook();

    public String getName() {
        return name;
    }
}
