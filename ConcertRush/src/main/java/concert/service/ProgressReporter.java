package concert.service;

import concert.model.Stadium;

import java.util.List;

// Background task that periodically prints how many seats are sold.
public class ProgressReporter implements Runnable {

    private final Stadium stadium;
    private final int totalFans;
    private volatile boolean running = true; // flag toggled when the simulation ends

    public ProgressReporter(Stadium stadium, int totalFans) {
        this.stadium = stadium;
        this.totalFans = totalFans;
    }

    @Override
    public void run() {
        int counter = 0; // seconds elapsed for pretty printing
        while (running) {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                break;
            }

            if (!running) break;

            counter += 2;
            int sold = stadium.getSoldSeatsCount();
            int remainingFansEstimate = totalFans - sold;

            System.out.printf("[PROGRESS %2ds] %3d seats sold  | ~%3d fans still trying   ",
                    counter, sold, remainingFansEstimate);

            List<String> recent = stadium.getRecentBookings(1);
            if (!recent.isEmpty()) {
                System.out.print(" | " + recent.get(recent.size() - 1));
            }
            System.out.println();
        }
    }

    public void stop() {
        running = false;
    }
}
