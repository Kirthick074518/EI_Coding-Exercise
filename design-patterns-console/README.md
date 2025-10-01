# Design Patterns Console (Java)

A clean, production-grade Java 17 console application that demonstrates six GoF design patterns with strong engineering practices: logging, validation, exception handling, transient error retry, and defensive programming.

## Patterns Included
- Behavioral 1: Strategy — Route Planner strategies (driving, walking, transit)
- Behavioral 2: Observer — News publisher & subscribers
- Creational 1: Factory — Notification factory (Email/SMS/Push)
- Creational 2: Builder — Report builder (Text/JSON)
- Structural 1: Adapter — Payment gateways with transient-retry wrapper
- Structural 2: Decorator — Repository with logging and in-memory caching

## How to Run
1. Build (using Maven Wrapper; Maven install not required)
Windows (PowerShell/CMD):
```
./mvnw.cmd -q -DskipTests package
```
macOS/Linux:
```
./mvnw -q -DskipTests package
```
2. Run
```
java -jar target/design-patterns-console-1.0.0.jar
```

## Commands
- `help` — See available commands
- `strategy` — Run Strategy pattern demo
- `observer` — Run Observer pattern demo
- `factory` — Run Factory pattern demo
- `builder` — Run Builder pattern demo
- `adapter` — Run Adapter pattern demo (with transient retry)
- `decorator` — Run Decorator pattern demo (with caching/logging)
- `clear` — Clear the console
- `exit` — Quit the application

Note: The app avoids hard-coded infinite loops like `while(true)` by using a stream-driven input loop with explicit termination on `exit`.

## Engineering Practices
- Logging: SLF4J + Logback with console and rolling file appenders.
- Validation: `Validation` utility guards inputs at boundaries.
- Exceptions: Purpose-specific exceptions (`ValidationException`, `TransientException`, etc.).
- Transient retry: `RetryExecutor` with exponential backoff and jitter, controlled by `RetryPolicy`.
- Defensive code: Null-checks, immutable DTOs where appropriate, and input normalization.
- Structure: Each class lives in its own file; meaningful package separation; readable naming.

## Project Structure (key packages)
- `com.kirthick.dp.app` — Main console app, command loop, routing
- `com.kirthick.dp.common` — Validation and shared utilities
- `com.kirthick.dp.common.exceptions` — Custom exceptions
- `com.kirthick.dp.common.retry` — RetryPolicy + RetryExecutor
- `com.kirthick.dp.behavioral.strategy` — Strategy pattern demo
- `com.kirthick.dp.behavioral.observer` — Observer pattern demo
- `com.kirthick.dp.creational.factory` — Factory pattern demo
- `com.kirthick.dp.creational.builder` — Builder pattern demo
- `com.kirthick.dp.structural.adapter` — Adapter pattern demo
- `com.kirthick.dp.structural.decorator` — Decorator pattern demo

## Notes
- Logs are written to `logs/app.log` (rolled daily).
- The code is intentionally self-explanatory and documented to support a confident walk-through.

## Pattern Descriptions & Purpose

- **Strategy (Behavioral)**
  - **Purpose**: Define a family of algorithms, encapsulate each one, and make them interchangeable so the algorithm can vary independently from clients that use it.
  - **In this app**: `RouteStrategy` is the strategy interface with concrete strategies `DrivingStrategy`, `WalkingStrategy`, and `TransitStrategy` in `com.kirthick.dp.behavioral.strategy/`. The context `RoutePlanner` selects a strategy at runtime based on user input and plans a `Route` accordingly.

- **Observer (Behavioral)**
  - **Purpose**: Establish a one-to-many dependency so that when one object changes state, all its dependents are notified and updated automatically.
  - **In this app**: `Publisher`/`Subscriber` contracts with `NewsPublisher` notifying `EmailSubscriber` and `MobileAppSubscriber` in `com.kirthick.dp.behavioral.observer/`. Users subscribe to `NewsCategory` values, and publishing a headline notifies observers.

- **Factory (Creational)**
  - **Purpose**: Create objects without exposing the creation logic to the client and refer to the newly created object through a common interface.
  - **In this app**: `NotificationFactory` in `com.kirthick.dp.creational.factory/` produces a `Notification` (`EmailNotification`, `SmsNotification`, `PushNotification`) based on a string type. The console collects `to` and `message`, and the chosen notification sends it.

- **Builder (Creational)**
  - **Purpose**: Construct complex objects step-by-step. The same construction process can create different representations. Promotes immutability and validation.
  - **In this app**: `Report.Builder` incrementally builds an immutable `Report` with sections and metadata (`com.kirthick.dp.creational.builder/`). The result is formatted with either `TextReportFormatter` or `JsonReportFormatter` selected at runtime.

- **Adapter (Structural)**
  - **Purpose**: Convert the interface of a class into another interface clients expect, enabling integration of incompatible APIs without modifying them.
  - **In this app**: `PaymentProcessor` is the target interface (`com.kirthick.dp.structural.adapter/`). `RazorPayAdapter` adapts `RazorPayClient` and `StripeAdapter` adapts `StripeClient`. `PaymentService` wraps a processor and adds transient retry via `RetryExecutor` and `RetryPolicy` from `com.kirthick.dp.common.retry/`.

- **Decorator (Structural)**
  - **Purpose**: Attach additional responsibilities to an object dynamically. Decorators provide a flexible alternative to subclassing for extending behavior.
  - **In this app**: `UserRepository` is the component (`com.kirthick.dp.structural.decorator/`). `InMemoryUserRepository` is the concrete implementation. `CacheUserRepository` adds caching and `LoggingUserRepository` adds timing logs by wrapping the delegate, demonstrating layered, composable behaviors.

## When to use & Trade-offs

### Strategy
- **When to use**
  - Multiple interchangeable algorithms or policies (e.g., different routing modes).
  - You want to eliminate complex conditional logic based on a mode/type.
  - You want open/closed extensibility: add a new strategy class without touching the context.
  - You need to unit test algorithms independently of the calling context.
- **Trade-offs**
  - More classes/objects to manage; slight indirection overhead.
  - The client or context must choose the right strategy (selection logic shifts elsewhere).
  - Risk of strategy-specific assumptions leaking into the context if not carefully designed.

### Observer
- **When to use**
  - Event-driven workflows where many subscribers react to a publisher (e.g., notifications).
  - You want loose coupling between senders and receivers with dynamic subscription.
  - You need to broadcast updates to 0..N listeners without the publisher knowing them.
- **Trade-offs**
  - Notification order is typically undefined; can complicate determinism.
  - Potential memory leaks if subscribers are not unsubscribed appropriately.
  - Harder debugging due to implicit, asynchronous-feeling flows of control.

### Factory
- **When to use**
  - Creation logic varies by type/config and you want to centralize object creation.
  - You want to return a common interface (`Notification`) while hiding concrete classes.
  - You need to swap implementations based on config, environment, or runtime input.
- **Trade-offs**
  - Adds indirection; simple constructors may be sufficient for small cases.
  - String/type keys can drift without safeguards; consider enums or typed factories.
  - Too many factories can proliferate; keep them cohesive and discoverable.

### Builder
- **When to use**
  - Building complex immutable objects with many optional/required parts.
  - You need validation before creation and readable, stepwise construction.
  - You support multiple output representations (paired with formatters).
- **Trade-offs**
  - More boilerplate than direct constructors for simple objects.
  - Builder and target must be kept in sync; adds maintenance cost.
  - Validation must be thoughtfully placed (on each step vs on build()).

### Adapter
- **When to use**
  - Integrating incompatible third-party or legacy APIs under a unified interface.
  - You want to swap vendors (`RazorPayClient` vs `StripeClient`) without changing callers.
  - You need to add cross-cutting concerns (retry, metrics) around a standard interface.
- **Trade-offs**
  - Another abstraction layer to maintain; small runtime overhead.
  - Only adapts the interface; semantic differences (e.g., currency units) still need care.
  - Vendor-specific features may be hidden or require out-of-band access.

### Decorator
- **When to use**
  - Add responsibilities dynamically (caching, logging, authorization) without subclass explosion.
  - Compose multiple concerns by stacking decorators (`Logging` + `Cache`).
  - Toggle features at wiring time (tests vs prod) without changing core code.
- **Trade-offs**
  - Many small objects; tracing call paths through multiple layers can be harder.
  - The order of decorators matters; misordering may change semantics/performance.
  - Potential duplication if multiple decorators try to address similar cross-cutting concerns.

## App Overview

This is an interactive console application to learn and demonstrate six core GoF design patterns with production-grade practices (logging, validation, retry, defensive programming). You run the app, type a command (e.g., `strategy`), and the app guides you with prompts to showcase how the pattern works in a realistic scenario.

- **Audience**: Students, interview prep, and engineers who want hands-on pattern demos.
- **Behavior**: The prompt `dp>` accepts commands; type `help` to see options, `exit` to quit.
- **Reliability**: Inputs are validated; errors are friendly. Transient failures (e.g., payments) are retried with backoff.
- **Logging**: Console + file at `logs/app.log` with rolling policies.

## Usage Guide: Inputs and Examples

Below are minimal inputs and expected outcomes for each command. Values shown are examples—feel free to use your own.

### 1) Strategy (Route Planner)
- **Command**: `strategy`
- **Prompts**:
  - Enter start: Home
  - Enter end: Office
  - Mode (driving/walking/transit): driving
- **Output**: A planned route, e.g.,
  ```
  Planned: Home -> Office via driving: 12.3 km ~ 19 mins
  ```
  Note: Distance and time are simulated and vary per run.

### 2) Observer (News Publisher)
- **Command**: `observer`
- **Prompts** (you can skip subscribers by leaving blank):
  - Enter email subscriber (or blank to skip): user@example.com
  - Categories for email:user@example.com (comma sep: sports,tech,finance,entertainment,weather): tech,finance
  - Enter mobile username (or blank to skip): alice
  - Categories for mobile:alice (...): sports
  - Type headlines to publish; type 'done' to finish.
  - Headline > Startup raises Series A
  - Category (sports/tech/finance/entertainment/weather): tech
- **Effect**: Matching subscribers receive updates and log entries show notifications.

### 3) Factory (Notification Sender)
- **Command**: `factory`
- **Prompts**:
  - Type (email/sms/push): sms
  - To (email or phone or userId): +9198123456780
  - Message: Your OTP is 123456
- **Output**:
  ```
  Sent using: SmsNotification
  ```
  Check logs to see the "sent" event.

### 4) Builder (Report Builder)
- **Command**: `builder`
- **Prompts**:
  - Title: Sales Summary
  - Author: Kirthick
  - Section 1 title: Q1 Highlights
  - Content: Revenue up 12% QoQ
  - Section 2 title: (leave blank to stop)
  - meta > region=APAC
  - meta > priority=high
  - meta > (blank to stop)
  - Formatter (text/json): json
- **Output**: A formatted report in the chosen representation printed to console.

### 5) Adapter (Payment Gateways with Retry)
- **Command**: `adapter`
- **Prompts**:
  - Gateway (razorpay/stripe): stripe
  - User ID: user-123
  - Currency (INR/USD): USD
  - Amount: 10.50
- **Output**:
  ```
  Result: PaymentResult{status=SUCCESS, transactionId='...', message='Paid via Stripe'}
  ```
  Transient errors (simulated) are retried automatically with exponential backoff.

### 6) Decorator (Repository with Caching & Logging)
- **Command**: `decorator`
- **Prompts**:
  - Enter user id: u1
  - Enter name: Alice
- **Output**:
  ```
  Fetching user twice to demonstrate cache hit on second call...
  First fetch:  User{id='u1', name='Alice'} in 12 ms
  Second fetch: User{id='u1', name='Alice'} in 0 ms
  (See logs for detailed timings)
  ```
  First read hits storage; second read is served from cache with logging around both.
