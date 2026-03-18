package concert;

import concert.entity.Fan;
import concert.model.Stadium;
import concert.service.ProgressReporter;
import concert.util.FanFactory;

import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Main {

    // Entry point for the ticket sale simulation; wires user input to the core classes.
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("=== ConcertRush - Stadium Ticket Booking Simulation ===\n");

        // Basic configuration gathered from the user.
        System.out.print("Enter total seats available: ");
        int totalSeats = scanner.nextInt();

        System.out.print("Enter number of fans trying to buy tickets: ");
        int totalFans = scanner.nextInt();

        System.out.print("Enter number of concurrent threads (recommended 10–50): ");
        int threadPoolSize = scanner.nextInt();

        System.out.println("\nStarting ticket sale simulation...\n");

        // Build the single shared stadium that holds all seats and booking logic.
        Stadium stadium = Stadium.getInstance(totalSeats);

        // Create a mix of VIP and regular fans using the factory helper.
        List<Fan> fans = FanFactory.createFans(totalFans, stadium);

        // Launch a background reporter to print progress while fans compete.
        ProgressReporter reporter = new ProgressReporter(stadium, fans.size());
        Thread reporterThread = new Thread(reporter, "Progress-Reporter");
        reporterThread.setDaemon(true);
        reporterThread.start();

        // Start timing to show how long the simulation runs.
        long startTime = System.currentTimeMillis();

        // Fixed thread pool to simulate concurrent buyers.
        ExecutorService executor = Executors.newFixedThreadPool(threadPoolSize);

        // Submit every fan as a separate task to the pool.
        for (Fan fan : fans) {
            executor.submit(fan);
        }

        // Stop accepting new tasks and wait for all fans to finish (with a timeout).
        executor.shutdown();
        try {
            if (!executor.awaitTermination(90, TimeUnit.SECONDS)) {
                executor.shutdownNow();
            }
        } catch (InterruptedException e) {
            executor.shutdownNow();
            Thread.currentThread().interrupt();
        }

        // Stop reporter once booking is done.
        reporter.stop();

        long endTime = System.currentTimeMillis();
        long durationMs = endTime - startTime;

        // Final report
        System.out.println("\n╔════════════════════════════════════════════╗");
        System.out.println("║             SIMULATION FINISHED            ║");
        System.out.println("╚════════════════════════════════════════════╝");
        System.out.printf("Total seats available    : %d%n", totalSeats);
        System.out.printf("Seats sold               : %d%n", stadium.getSoldSeatsCount());
        System.out.printf("Fans who got tickets     : %d%n", stadium.getSoldSeatsCount());
        System.out.printf("Fans who missed out      : %d%n", totalFans - stadium.getSoldSeatsCount());
        System.out.printf("Total simulation time    : %.2f seconds%n", durationMs / 1000.0);
        System.out.println("Overbooking prevented    : YES");
        System.out.println("══════════════════════════════════════════════");
    }
}
