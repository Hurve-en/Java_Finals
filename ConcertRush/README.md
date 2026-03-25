# ConcertRush

A small console app that pretends to sell stadium tickets. Regular and VIP fans race each other using threads. Needs JDK 17+.

## Quick start
```bash
cd ConcertRush
javac -d out $(find src -name "*.java")
java -cp out concert.Main
```

## How it works
- You enter total seats, number of fans, and thread pool size.
- A shared `Stadium` holds the seats (A1, A2, ...).
- `FanFactory` builds regular and VIP fans; VIPs get up to three tries.
- A thread pool runs all fans at once.
- `ProgressReporter` prints updates every 2 seconds.
- At the end, you see how many seats sold and how many fans missed out.

## Files to know
- `src/main/java/concert/Main.java` — reads input and runs the sim.
- `src/main/java/concert/model/Seat.java` — seat object.
- `src/main/java/concert/model/Ticket.java` — who got which seat.
- `src/main/java/concert/model/Stadium.java` — seat list and booking logic.
- `src/main/java/concert/entity/Fan.java` — base fan runnable.
- `src/main/java/concert/entity/RegularFan.java` — one-and-done fan.
- `src/main/java/concert/entity/VipFan.java` — retries up to three times.
- `src/main/java/concert/service/ProgressReporter.java` — progress printer.
- `src/main/java/concert/util/FanFactory.java` — builds the fan list.
