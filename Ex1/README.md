# Ex1 — Design Patterns

This is a Java 17 project that demonstrates six classic GoF design patterns:
- Strategy (Behavioral)
- Observer (Behavioral)
- Factory (Creational)
- Builder (Creational)
- Adapter (Structural)
- Decorator (Structural)

Each pattern has its own dedicated `main` entrypoint so you can run them independently.

## Prerequisites
- Java 17 or newer on PATH
  - Verify with: `java -version` and `javac -version`

## Compile
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

## Run (each pattern separately)
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

## What you will see
- Strategy: Route planning using three interchangeable strategies (driving, walking, transit)
- Observer: News publisher notifying email and mobile subscribers
- Factory: Notification factory creating Email/SMS/Push notifications
- Builder: Report constructed step-by-step and printed in Text and JSON formats
- Adapter: Unified payment processor adapting two different gateway clients
- Decorator: User repository augmented with logging and in-memory caching

## Project Layout
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
## Pattern Summaries
- Strategy: Define a family of algorithms, encapsulate each, and make them interchangeable.
- Observer: Establish a one-to-many dependency; observers are notified when the subject changes.
- Factory: Create objects without exposing instantiation logic; use a common interface.
- Builder: Construct complex objects step-by-step; same construction can yield different representations.
- Adapter: Convert one interface into another expected by the client without changing the adaptee.
- Decorator: Attach responsibilities to objects dynamically without subclass explosion.

## Detailed Use Cases & How Patterns Are Applied

### Strategy — Route planning modes
- **Use case**: Plan a route from `start` to `end` using different transportation modes (driving, walking, transit).
- **Pattern application**:
  - `RouteStrategy` defines the strategy interface.
  - `DrivingStrategy`, `WalkingStrategy`, `TransitStrategy` implement `plan()` with different assumptions (distance, time).
  - `RoutePlanner` is the context that holds a `RouteStrategy` and delegates `plan()` to it.
  - See `StrategyMain` for running this pattern in isolation.
- **Why Strategy here**: Cleanly replaces conditional logic (if mode == X) with interchangeable algorithm objects.

### Observer — News publishing and subscribers
- **Use case**: A news publisher broadcasts headlines per `NewsCategory` to multiple subscribers.
- **Pattern application**:
  - `Publisher`/`Subscriber` define the contracts.
  - `NewsPublisher` keeps `List<Subscriber>` per category (`EnumMap`) and notifies on `publish()`.
  - `EmailSubscriber`, `MobileAppSubscriber` react to `onNews(...)` independently.
  - See `ObserverMain` for running this pattern in isolation.
- **Why Observer here**: Decouples event producer from consumers; supports 0..N listeners and dynamic subscription.

### Factory — Notifications by type
- **Use case**: Create a notification sender based on a type and send messages uniformly.
- **Pattern application**:
  - `Notification` is the product interface; `EmailNotification`, `SmsNotification`, `PushNotification` are concrete products.
  - `NotificationFactory.create(type)` maps input to the right product without leaking constructor logic to callers.
  - See `FactoryMain` for running this pattern in isolation.
- **Why Factory here**: Centralizes creation and returns a common interface; easy to extend with new notification types.

### Builder — Mission report construction and formatting
- **Use case**: Build a multi-section mission report with metadata, then render it in different formats.
- **Pattern application**:
  - `Report.Builder` collects title, author, sections, and metadata, then produces an immutable `Report`.
  - `ReportFormatter` is a small SPI; `TextReportFormatter` and `JsonReportFormatter` are two renderers.
  - See `BuilderMain` for running this pattern in isolation.
- **Why Builder here**: Stepwise construction with validation potential and clear semantics; separates building from representation.

### Adapter — Unifying payment gateways
- **Use case**: Process payments through different third-party clients under a unified interface.
- **Pattern application**:
  - `PaymentProcessor` is the target interface expected by the application.
  - `RazorPayClient` expects amount in paise; `StripeClient` expects cents and outputs through an array.
  - `RazorPayAdapter` and `StripeAdapter` translate to/from these APIs (amount conversion, transaction id normalization) returning `PaymentResult`.
  - See `AdapterMain` for running this pattern in isolation.
- **Why Adapter here**: Shields the app from vendor API shapes; enables swapping providers without touching callers.

### Decorator — Repository with logging and caching
- **Use case**: Enhance a `UserRepository` with caching and timing logs without altering the base implementation.
- **Pattern application**:
  - `UserRepository` is the component; `InMemoryUserRepository` is the concrete component.
  - `CacheUserRepository` adds read-through cache; `LoggingUserRepository` times calls and logs.
  - Decorators wrap the component: `new LoggingUserRepository(new CacheUserRepository(base))`.
  - See `DecoratorMain` for running this pattern in isolation.
- **Why Decorator here**: Adds cross-cutting behavior dynamically; order of decorators is explicit and composable.

### How to extend (quick ideas)
- Strategy: add `CyclingStrategy` and set it in `RoutePlanner`.
- Observer: add `PushSubscriber` or new `NewsCategory` and subscribe.
- Factory: add `InAppNotification` and switch on a new type.
- Builder: add validation in `build()` or new `MarkdownReportFormatter`.
- Adapter: add `PayPalClient` with a `PayPalAdapter` to `PaymentProcessor`.
- Decorator: add `AuthUserRepository` to enforce authorization before calls.
