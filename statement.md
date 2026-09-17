# Canteen Ordering & Queue System — Problem Statement & Scope

## 1. Problem Statement
In college and university canteens, students face prolonged queues and waiting times during short lecture intervals. Manual token collection and verbal order routing lead to order mix-ups, loose change delays, and counter congestion.

This project delivers a lightweight, console-based **Canteen Ordering & Queue System** in Java that enables students to browse menus, place orders, deduct payments from a local digital campus wallet, and track kitchen queue states in real-time.

---

## 2. Scope of the Project
- **In-Scope**:
  - Menu browsing across food and beverage items with pricing and preparation times.
  - Interactive CLI ordering with automatic order ID generation and total price calculation.
  - Digital campus wallet management with balance validation, deductions, and top-up.
  - Real-time kitchen queue tracking (`WAITING` -> `PREPARING` -> `READY`).
  - Background daemon thread automatically advancing kitchen queue states.
  - Persistent file storage using `BufferedReader` and `BufferedWriter` to save orders across sessions.
  - Domain-specific checked exceptions (`InsufficientBalanceException`, `ItemNotFoundException`).
- **Out-of-Scope**:
  - Complex external database servers (the project deliberately runs standalone with persistent text storage for maximum portability and zero-setup evaluation).
  - Web UI / mobile frontend.

---

## 3. Target Users
- **College Students**: View the canteen menu, place orders without physical queues, track order cooking status, and manage their campus wallet.
- **Canteen Staff**: Process incoming food and drink orders in sequence.

---

## 4. High-Level Features
1. **Object-Oriented Menu**: Abstract `MenuItem` class with specialized `FoodItem` (veg/non-veg) and `BeverageItem` (hot/cold) subclasses.
2. **Persistent Storage**: Persistent file I/O storing transactions and balances in `data/orders.txt`.
3. **Concurrent Queue Worker**: Background daemon thread `OrderProcessingTask` simulating real-time kitchen preparation.
4. **Defensive Error Handling**: Custom checked exceptions guarding against overdrafts and invalid item lookups.
