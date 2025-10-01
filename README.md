# Educational Initiatives — Coding Exercise

This repository contains multiple Java projects developed under the same parent directory. This document aggregates the READMEs for quick reference.

- Ex1 — Design Patterns (pure Java source with per-pattern mains)
- Astronaut Daily Schedule Organizer — Console app using Maven Wrapper

---

## Ex1 — Design Patterns

This is a Java 17 project that demonstrates six classic GoF design patterns:
- Strategy (Behavioral)
- Observer (Behavioral)
- Factory (Creational)
- Builder (Creational)
- Adapter (Structural)
- Decorator (Structural)

Each pattern has its own dedicated `main` entrypoint so you can run them independently.

### Location
- Directory: `Ex1/`
- Entry points per pattern are under `src/` (see Run section below)

### Prerequisites
- Java 17 or newer on PATH
  - Verify with: `java -version` and `javac -version`

### Compile
- Windows (PowerShell) — Option A (recommended)
```
mkdir out 2>$null
$files = Get-ChildItem -Recurse -Filter *.java -Path src | Select-Object -ExpandProperty FullName
javac -d out $files
```

- Windows (PowerShell) — Option B (argfile)
```
mkdir out 2>$null
Get-ChildItem -Recurse -Filter *.java -Path src | ForEach-Object { ($_.FullName -replace '\\','/') } | Set-Content -Encoding ASCII sources.txt
javac -d out "@sources.txt"
```

- Windows (CMD):
```
mkdir out 2>NUL
del /q sources.txt 2>NUL
for /R src %f in (*.java) do @echo %f>>sources.txt
javac -d out @sources.txt
```

- macOS/Linux:
```
mkdir -p out
javac -d out $(find src -name "*.java")
```

### Run (each pattern separately)
- Strategy:
```
java -cp out ex1.behavioral.strategy.StrategyMain
```
- Observer:
```
java -cp out ex1.behavioral.observer.ObserverMain
```
- Factory:
```
java -cp out ex1.creational.factory.FactoryMain
```
- Builder:
```
java -cp out ex1.creational.builder.BuilderMain
```
- Adapter:
```
java -cp out ex1.structural.adapter.AdapterMain
```
- Decorator:
```
java -cp out ex1.structural.decorator.DecoratorMain
```

### What you will see
- Strategy: Route planning using interchangeable strategies (driving, walking, transit)
- Observer: News publisher notifying email and mobile subscribers
- Factory: Notification factory creating Email/SMS/Push notifications
- Builder: Report constructed step-by-step and printed in Text and JSON
- Adapter: Unified payment processor adapting two different gateway clients
- Decorator: User repository augmented with logging and in-memory caching

### Project Layout
```
Ex1/
  README.md
  src/
    ex1/behavioral/strategy/... (Strategy)
    ex1/behavioral/observer/... (Observer)
    ex1/creational/factory/...  (Factory)
    ex1/creational/builder/...  (Builder)
    ex1/structural/adapter/...  (Adapter)
    ex1/structural/decorator/... (Decorator)
```

### Pattern Summaries
- Strategy: Define a family of algorithms, encapsulate each, and make them interchangeable.
- Observer: Establish a one-to-many dependency; observers are notified when the subject changes.
- Factory: Create objects without exposing instantiation logic; use a common interface.
- Builder: Construct complex objects step-by-step; same construction can yield different representations.
- Adapter: Convert one interface into another expected by the client without changing the adaptee.
- Decorator: Attach responsibilities to objects dynamically without subclass explosion.

### Detailed Use Cases & How Patterns Are Applied

#### Strategy — Route planning modes
- Use case: Plan a route from `start` to `end` using different transportation modes.
- Pattern application:
  - `RouteStrategy` interface; `DrivingStrategy`, `WalkingStrategy`, `TransitStrategy` implementations
  - `RoutePlanner` (context) delegates to the current strategy
  - See `StrategyMain` to run this in isolation

#### Observer — News publishing and subscribers
- Use case: Publisher broadcasts headlines per `NewsCategory` to subscribers.
- Pattern application:
  - `Publisher`/`Subscriber` contracts; `NewsPublisher` manages subscribers by category
  - `EmailSubscriber`, `MobileAppSubscriber` respond to `onNews(...)`
  - See `ObserverMain`

#### Factory — Notifications by type
- Use case: Create a notification sender by type and send messages uniformly.
- Pattern application:
  - `Notification` interface; `EmailNotification`, `SmsNotification`, `PushNotification` products
  - `NotificationFactory.create(type)` returns the correct product
  - See `FactoryMain`

#### Builder — Mission report construction and formatting
- Use case: Build a multi-section `Report` and render it differently.
- Pattern application:
  - `Report.Builder` for stepwise construction
  - `ReportFormatter` SPI; `TextReportFormatter`, `JsonReportFormatter` renderers
  - See `BuilderMain`

#### Adapter — Unifying payment gateways
- Use case: Normalize RazorPay and Stripe clients into a single API.
- Pattern application:
  - `PaymentProcessor` target interface; `RazorPayAdapter`, `StripeAdapter` perform conversions
  - See `AdapterMain`

#### Decorator — Repository with logging and caching
- Use case: Add caching and timing logs without changing the base repository.
- Pattern application:
  - `UserRepository` component; `InMemoryUserRepository` concrete component
  - `CacheUserRepository` and `LoggingUserRepository` wrap the component
  - See `DecoratorMain`

---

## Ex2 - Astronaut Daily Schedule Organizer (Java)

A clean, console-based Java 17 application that helps astronauts organize their daily schedule using best practices and GoF patterns:
- Singleton — single `ScheduleManager` instance for all task operations
- Factory — `TaskFactory` to construct and validate `Task` objects
- Observer — observers notified on add/remove/update/complete/conflict events

### Location
- Directory: `astronaut-schedule-console/`

### How to Run
1) Build (Maven Wrapper included; Maven install not required)
- Windows (PowerShell/CMD):
```
./mvnw.cmd -q -DskipTests package
```
- macOS/Linux:
```
./mvnw -q -DskipTests package
```

2) Run
```
java -jar target/astronaut-schedule-console-1.0.0.jar
```

### Commands
- `help` — List commands
- `add` — Add a new task
- `remove` — Remove a task by description
- `view` — View all tasks sorted by start time
- `viewp` — View tasks by priority (HIGH/MEDIUM/LOW)
- `edit` — Edit an existing task
- `complete` — Mark a task as completed
- `clear` — Clear the console
- `exit` — Quit the application

### Input Guide & Examples
Times use 24-hr `HH:mm` format.
- `add`
  - Description: Morning Exercise
  - Start time (HH:mm): 07:00
  - End time (HH:mm): 08:00
  - Priority (High/Medium/Low): High
  - Output: `Task added successfully. No conflicts.`
- `view`
  - Output (example):
    - `07:00 - 08:00: Morning Exercise [HIGH]`
    - `09:00 - 10:00: Team Meeting [MEDIUM]`
- `remove`
  - Description of task to remove: Morning Exercise
  - Output: `Task removed successfully.`
- `edit`
  - Existing description: Team Meeting
  - New description (blank=keep): Daily Sync
  - New start (HH:mm, blank=keep): 09:00
  - New end (HH:mm, blank=keep): 10:00
  - New priority (High/Medium/Low, blank=keep): Medium
  - Output: `Task updated: 09:00 - 10:00: Daily Sync [MEDIUM]`
- `complete`
  - Description: Daily Sync
  - Output: `Task marked completed.`
- `viewp`
  - Priority (High/Medium/Low): High
  - Output: shows only tasks with `HIGH` priority or says `No tasks for priority HIGH.`

### Validations & Errors
- Overlap detection prevents adding/editing tasks that overlap an existing task.
  - Example: Adding `09:30-10:30` when `09:00-10:00` exists results in: `Error: Task conflicts with existing task "Team Meeting"`
- Invalid time format results in: `Error: Invalid time format. Expected HH:mm`
- Removing a non-existent task: `Error: Task not found`
- Duplicate description: `Error: Task with this description already exists`

### Design & Patterns
- `com.kirthick.astro.schedule.manager.ScheduleManager` — Singleton manager; maintains tasks; synchronized operations; publishes events
- `com.kirthick.astro.schedule.factory.TaskFactory` — Centralized `Task` creation, parsing, validation
- `com.kirthick.astro.schedule.observer.*` — `ConsoleObserver` and `LoggingObserver`
- `com.kirthick.astro.schedule.domain.*` — Immutable `Task`, `Priority`
- `com.kirthick.astro.app.*` — CLI loop, routing, command handlers

### Detailed Description (highlights)
- Conflict detection logic: overlap if `a.start < b.end && b.start < a.end`
- Observer events: `ADDED`, `REMOVED`, `UPDATED`, `COMPLETED`, `CONFLICT`
- Thread-safety: holder-idiom Singleton; concurrent collections; synchronized mutations
- Performance: O(n) conflict scan acceptable for daily schedule; sortable views
- Extensibility: persistence, dates, recurrence, IDs, import/export

For the full detailed documentation, see `astronaut-schedule-console/README.md`.

---

## Contact
- For any queries or suggestions, please contact me at [kirthickk622@gmail.com]
