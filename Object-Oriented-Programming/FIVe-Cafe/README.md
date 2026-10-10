# FIVe Cafè

A self-service ordering system for a café, written in **Java 21** with **JavaFX** and **Maven**.

It simulates an ordering **totem** for the customer and a separate **display for the barman**.
The code follows the **Boundary – Control – Entity (BCE)** architecture and applies three design
patterns: **Simple Factory**, **Decorator** and **Observer**.

Developed for educational purposes as part of a university course.

---

## Features

- A menu of 39 beverages in 6 categories
- Optional extras (milk, sugar, caramel, cocoa), added with the Decorator pattern
- A live cart with the total price
- Simulated payment (card or cash)
- An order number assigned at payment
- A barman display that updates automatically when an order arrives or changes status
- Dark theme, defined in a single CSS file

---

## Requirements

- **JDK 21**
- **Maven 3.8+**

Check that Maven is running with Java 21:

```bash
mvn -version
```

The line `Java version` must say 21.

---

## How to run

```bash
git clone https://github.com/<your-username>/academic-archive.git
cd academic-archive/Object-Oriented-Programming/FIVe-Cafe
mvn clean javafx:run
```

The first run downloads JavaFX. Two windows open: the customer totem and the barman display.

> **Do not use the IDE "Run" button.** JavaFX needs a runtime configuration that the Maven
> plugin sets up. Always start the app with `mvn clean javafx:run`.

---

## Architecture

| Zone | Package | Responsibility |
|---|---|---|
| **Boundary** | `boundary` | JavaFX screens: `CustomerBoundary` (totem) and `BarmanBoundary` (barman display) |
| **Control** | `control` | Use cases: `OrderController` coordinates orders, `BeverageFactory` builds beverages |
| **Entity** | `entity` | Domain objects: beverages, menu, orders, order status |

Dependencies go in one direction only: `Boundary → Control → Entity`.
Entities know nothing about screens, and screens contain no business rules.

### Project structure

```
src/main/java/it/fiv/FIVeCafe/
├── entity/
│   ├── Beverage              interface: name and price
│   ├── BasicBeverage         a beverage without extras
│   ├── BeverageType          the menu (39 beverages)
│   ├── BeverageCategory      the 6 menu categories
│   ├── Extra                 MILK, SUGAR, CARAMEL, COCOA
│   ├── Order                 an order and its status rules
│   ├── OrderStatus           CREATED → RECEIVED → PREPARING → READY → DELIVERED
│   └── decorator/            BeverageDecorator + one decorator per extra
├── control/
│   ├── BeverageFactory       builds a beverage with its extras
│   └── OrderController       creates, submits and updates orders (Observer subject)
├── observer/
│   └── OrderObserver         interface implemented by whoever wants to be notified
└── boundary/
    ├── CustomerBoundary      the totem (application entry point)
    ├── BarmanBoundary        the barman display (an Observer)
    └── Styles                loads the stylesheet
src/main/resources/css/style.css
```

---

## Design patterns

- **Simple Factory** (`BeverageFactory`): a single place that knows how to build a beverage
  from a `BeverageType` and a set of extras.
- **Decorator** (`BeverageDecorator` and its subclasses): each extra wraps a beverage and adds
  its own name and price. Any combination of extras is possible without a class per combination.
- **Observer** (`OrderObserver`, `OrderController`, `BarmanBoundary`): the controller notifies its
  subscribers when an order is submitted or changes status. The barman display subscribes and
  redraws itself, without the controller knowing it is a screen.

---

## Order life cycle

```
CREATED → RECEIVED → PREPARING → READY → DELIVERED
```

- An order is **CREATED** while the customer builds the cart. It has no number yet and the bar
  does not know about it.
- On payment the order gets its **number** and becomes **RECEIVED**: it appears on the barman display.
- The barman moves it forward with **PREPARING**, **READY** and **DELIVERED**.
- Only the next status is allowed: no skipping and no going back. An empty order cannot be submitted.

---

## Usage walkthrough

1. **Start Order** on the totem.
2. Pick a category in the left sidebar, then a beverage in the list.
3. Tick the extras you want: the price updates immediately. Click **Add to cart**.
4. Repeat for other beverages. The cart and the total are on the right.
5. **Pay**, choose card or cash. The order is sent to the bar and the totem returns to the home screen.
   **Cancel order** discards the cart instead.
6. On the barman display, select the order and use **PREPARING**, **READY** and **DELIVERED**.
   Only the button for the allowed next status is enabled.

---

## Design decisions

- **The menu lives in one place.** `BeverageType` holds name, category, price, description and
  whether extras are allowed. Adding a beverage means adding one line.
- **Order numbers are assigned at payment**, so an abandoned order never consumes a number.
- **The barman only sees paid orders.** For this reason there is no RECEIVED button: the totem does that.
- **Extras are always applied in the same order** (milk, sugar, caramel, cocoa), so names are
  predictable whatever order the customer ticked them in.
- Rules are enforced by the entities (`Order.canTransitionTo`), and the screens ask them instead of
  repeating them.

---

## Known limitations

- Prices are `double`. For a real payment system `BigDecimal` (or integer cents) would be needed.
- Payment is only simulated.
- Orders are kept in memory and are lost when the application is closed.
- There are no automated tests.

---

## Common issues

**`JavaFX runtime components are missing`**: the app was started with the IDE Run button.
Use `mvn clean javafx:run`.

**Compilation error about release 21**: Maven is running with another Java version.
Check the `Java version` line of `mvn -version`.

**`Stylesheet /css/style.css not found`**: the file must be in `src/main/resources/css/`.

---

## Author

Sara