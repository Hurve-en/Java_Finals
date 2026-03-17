# ConcertRush

Console simulation for a stadium ticket sale with multithreaded fans (Regular vs VIP) racing to book seats. Run with JDK 17+.

```bash
cd ConcertRush
javac -d out $(find src -name "*.java")
java -cp out concert.Main
```

What it does
- Prompts for total seats, fan count, and thread pool size.
- Spins up a shared `Stadium` singleton with simple A1, A2... sections.
- Builds a mix of VIP and regular `Fan` runnable tasks via `FanFactory`.
- Uses a thread pool to execute fan bookings concurrently; VIPs retry up to 3 times.
- `ProgressReporter` prints live progress every 2s until tasks finish.
- Prints a final summary showing seats sold vs fans who missed out.

Key files
- `src/main/java/concert/Main.java` — CLI + simulation orchestration.
- `src/main/java/concert/model/{Seat.java,Ticket.java,Stadium.java}` — seat state, ticket info, and synchronized booking log.
- `src/main/java/concert/entity/{Fan.java,RegularFan.java,VipFan.java}` — runnable fan behaviors.
- `src/main/java/concert/service/ProgressReporter.java` — background progress updates.
- `src/main/java/concert/util/FanFactory.java` — helper to generate fan list.
