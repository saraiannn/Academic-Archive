# FIVe Cafè Management System

A **Java-based management system** for a café, developed using **Maven** and **JavaFX**.  
The project simulates a **self-service ordering totem** with a **separate order display**, following the **Boundary – Control – Entity (BCE)** architectural pattern and applying key **Design Patterns**.

---

## Main features

- Order creation via totem
- Beverage selection using Factory pattern
- Dynamic ingredient addition using Decorator pattern
- Order status management
- Order display updates using Observer pattern
- JavaFX GUI

---

## Architecture

The system follows the **BCE (Boundary – Control – Entity)** architectural pattern:

- **Boundary**: JavaFX user interfaces (BarmanBoundary and CustomerBoundary)
- **Control**: OrderManager, responsible for coordinating use cases and enforcing business rules.
- **Entity**: Domain objects such as Order, Beverage, and BeverageType.

Design Patterns used:
- Factory
- Decorator
- Observer

---

## Requirements

Before running the project, make sure you have:

- **Java JDK 21**
- **Maven 3.8+**
- **IntelliJ IDEA** (recommended)

Check your Java version:
```bash
java -version
```

---

## How to run the project
### 1. Clone the repository
```git
git clone https://github.com/saraiannn/FIVe-cafe.git
cd FIVe-cafe
```

### 2. Import the project into IntelliJ
- File -> Open
- Select the project folder
IntelliJ will automatically detect the Mavem configuration

### 3. Run the JavaFX application
#### DO NOT use the IntelliJ 'Run' button
Instead, open the integrated terminal (second icon in the left corner of the IDE window) and run the following command:
```bash
mvn clean javafx:run 
```
This command will download JavaFX dependencies, configure the JavaFX runtime and launch the application.

---

## Common issues
### Error: JavaFX runtime components are missing
- Make sure you are using Java 21
- Always run the app using mvn javafx:run
### Error: ClassNotFoundException: MainFX
- Ensure the package name of MainFX matches the one defined in pom.xml

```xml

<mainClass>it.fiv.FIVecafe.boundary.CustomerBoundaryit.fiv.FIVecafe.boundary.CustomerBoundary</mainClass>
```

---

A console-based version of the application is included for educational purposes.
The JavaFX GUI is the current version under development.

---

## Author
Project developed for educational purposes as part of a university course.

---

## Guided System Usage (Simulation Walkthrough)
The following steps describe the correct workflow to simulate the full system behavior, from order creation at the totem to order preparation at the bar display.

**1. Start a new order:** launch the application and click the “Start Order” button to create a new customer order.

**2. Select a beverage category:** from the left sidebar, choose one of the available beverage categories (e.g. Basic Coffee, Cold Coffee, Tea & Non Coffee).

**3. Select a beverage and optional extras:** click on a beverage from the list on the right. A description of the selected beverage will be shown, and optional extras (such as milk, sugar, caramel, or cocoa) can be added. Confirm the selection by clicking “Add to cart”.

**4. Send the order to the bar:** once the order is complete, click the “Send” button. The order is now submitted and appears in the Barman Display with status **RECEIVED**.

**5. Manage the order from the Barman Display:** in the barman display window, the operator must first select an order from the list. Only after selecting it, the status buttons become effective: **PREPARING** to indicate the order is being prepared, **READY** to indicate the order is completed.

If no order is selected, status buttons will not perform any action.
