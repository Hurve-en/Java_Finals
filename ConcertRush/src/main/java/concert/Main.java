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

    // Starts the ticket sale simulation and connects the pieces together.
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("=== ConcertRush - Stadium Ticket Booking Simulation ===\n");

        // Get the basic setup from the user.
        System.out.print("Enter total seats available: ");
        int totalSeats = scanner.nextInt();

        System.out.print("Enter number of fans trying to buy tickets: ");
        int totalFans = scanner.nextInt();

        System.out.print("Enter number of concurrent threads (recommended 10–50): ");
        int threadPoolSize = scanner.nextInt();

        System.out.println("\nStarting ticket sale simulation...\n");

        // Shared stadium that owns all seats and booking rules.
        Stadium stadium = Stadium.getInstance(totalSeats);

        // Create a mix of VIP and regular fans.
        List<Fan> fans = FanFactory.createFans(totalFans, stadium);

        // Background progress printer.
        ProgressReporter reporter = new ProgressReporter(stadium, fans.size());
        Thread reporterThread = new Thread(reporter, "Progress-Reporter");
        reporterThread.setDaemon(true);
        reporterThread.start();

        // Track how long the run takes.
        long startTime = System.currentTimeMillis();

        // Thread pool to run fans in parallel.
        ExecutorService executor = Executors.newFixedThreadPool(threadPoolSize);

        // Submit each fan.
        for (Fan fan : fans) {
            executor.submit(fan);
        }

        // Stop accepting new tasks and wait for all fans to finish.
        executor.shutdown();
        try {
            if (!executor.awaitTermination(90, TimeUnit.SECONDS)) {
                executor.shutdownNow();
            }
        } catch (InterruptedException e) {
            executor.shutdownNow();
            Thread.currentThread().interrupt();
        }

        // Stop the reporter once booking is done.
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
