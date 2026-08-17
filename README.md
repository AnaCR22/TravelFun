# TravelFun

TravelFun is a Java desktop application for exploring theme parks and accommodation and managing reservations.

The application allows users to browse available destinations, select accommodation, complete a reservation and obtain discounts through an integrated interactive game.

## Features

* **Browse catalogue:** explore available theme parks and accommodation.
* **Accommodation filtering:** filter accommodation options by type.
* **Reservation management:** select, modify and remove parks and accommodation from a reservation.
* **Price calculation:** calculate the total reservation price and applicable discounts.
* **Discount game:** interactive card game that allows users to obtain a voucher for future reservations.
* **Help & Support:** integrated documentation through JavaHelp.
* **Input validation:** validation of reservation data and accommodation capacity.

## Architecture

The application separates the user interface, application logic and data model into different packages.

```text
src/
├── Model/
├── Service/
├── UI/
└── Util/
```

### Model

The `Model` package contains the main domain entities used by the application:

* `Accommodation`
* `ThemePark`
* `Catalog`
* `Client`
* `Reservation`

The model is responsible for storing application data and providing the operations required to manage it.

### Service

The `Logic` class acts as an intermediary between the model and the user interface.

It manages operations such as:

* Creating and updating reservations.
* Selecting and removing parks and accommodation.
* Calculating prices and discounts.
* Accessing catalogue information.
* Saving reservations and generating reservation summaries.

### UI

The graphical interface was developed using **Java Swing**, with separate windows for the catalogue, product details, reservation summary, registration and the discount game.

## Technology Stack

* **Java SE**
* **Java Swing**
* **JavaHelp**
* **Object-Oriented Programming**
* **File-based data persistence**

## Technical Concepts

The project applies several core Java and object-oriented programming concepts:

* **Interfaces**, including `Comparable`.
* **Abstract classes** for shared utility functionality.
* **Enumerations** for accommodation types.
* **Collections** such as `List` and `ArrayList`.
* **Exception handling** for file and data-related operations.
* **Event-driven programming** using listeners such as `ActionListener` and `ChangeListener`.
* **Input validation** for user-provided reservation data.
* **File-based persistence** using `.dat` files.

## User Interface

The interface was implemented with Java Swing components including:

* `JFrame`
* `JPanel`
* `JTabbedPane`
* `JScrollPane`
* `JSpinner`
* `JCheckBox`
* `JTextField`
* `JButton`

Catalogue elements are generated dynamically, and the application includes dedicated views for browsing parks and accommodation, configuring a reservation and reviewing its summary.

## Help & Support

The application includes an integrated **JavaHelp** system that provides contextual documentation and support to users directly within the application.

## Testing & Usability

The application was evaluated through several scenarios covering the main reservation flow and user interactions.

Testing focused on:

* Reservation creation and cancellation.
* Accommodation capacity validation.
* Price and discount calculation.
* Selection and replacement of accommodation.
* Navigation and user feedback.
* Help and support functionality.

Issues identified during testing were addressed through iterative improvements to the interface and application logic.

## Getting Started

### Requirements

* Java Development Kit (JDK)
* Eclipse IDE

### Installation

1. Clone the repository.
2. Open the project in Eclipse as an existing project.
3. Add `lib/jhall.jar` to the build path if it is not detected automatically.
4. Run `src/app/Main.java`.

## Project Context

Academic project developed at the **University of Oviedo**.

**Author:** Ana Calleja Ramón
