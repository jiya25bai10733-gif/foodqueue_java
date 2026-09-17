# 🍔 Canteen Ordering & Queue Engine

A robust, console-based canteen ordering and queue application built in Java demonstrating Object-Oriented Programming, multithreading, custom exception handling, and persistent file I/O.

### 🔹 Object-Oriented Architecture
Implements all four core OOP principles:
- **Abstraction** through the `MenuItem` base class.
- **Encapsulation** using private fields with controlled access methods.
- **Inheritance** via `FoodItem` and `BeverageItem`.
- **Polymorphism** for specialized item behavior and preparation times.

### 🔹 Multithreading & Concurrency
- Background daemon thread (`OrderProcessingTask`) automatically processes orders through kitchen stages.
- Thread-safe order placement and status updates using synchronized methods.

### 🔹 Custom Exception Handling
Implements domain-specific checked exceptions:
- `InsufficientBalanceException`
- `ItemNotFoundException`

This ensures graceful error handling during order placement and balance verification.

### 🔹 Persistent File Storage
- Uses `BufferedReader` and `BufferedWriter`.
- Stores order and wallet information in `data/orders.txt`.
- Automatically reloads wallet balances and historical orders when the application starts.
- Saves all data safely before exit via JVM shutdown hooks.

### 🔹 Interactive Command-Line Interface
- Built entirely using `java.util.Scanner`.
- Lightweight CLI with no external libraries or frameworks.
- Suitable for automated command-line evaluation environments.

---

# 📁 Project Structure

```text
canteen-queue-system/
├── src/
│   └── com/
│       └── canteen/
│           ├── exceptions/
│           │   ├── InsufficientBalanceException.java
│           │   └── ItemNotFoundException.java
│           ├── model/
│           │   ├── MenuItem.java
│           │   ├── FoodItem.java
│           │   ├── BeverageItem.java
│           │   └── Order.java
│           ├── service/
│           │   ├── CanteenService.java
│           │   ├── CanteenServiceImpl.java
│           │   └── OrderProcessingTask.java
│           └── Main.java
├── data/
│   └── orders.txt
├── .gitignore
├── statement.md
└── README.md
```

---

# ⚙️ Prerequisites
Before running the project, ensure you have:
- **Java Development Kit (JDK) 17 or above** (Tested on JDK 26)
- A terminal environment:
  - Windows Command Prompt
  - PowerShell
  - Bash (Linux/macOS)

---

# 🚀 Compilation & Execution

### 1️⃣ Clone the Repository
```bash
git clone https://github.com/jiya25bai10733-gif/foodqueue_java.git
cd foodqueue_java
```

### 2️⃣ Windows (Command Prompt / PowerShell)
```cmd
javac -d bin -sourcepath src src\com\canteen\Main.java
java -cp bin com.canteen.Main
```

### 3️⃣ Linux / macOS
```bash
javac -d bin -sourcepath src src/com/canteen/Main.java
java -cp bin com.canteen.Main
```

---

# 💾 Persistent Storage
Order and balance information is stored locally in:
```text
data/orders.txt
```

On application exit:
- Current wallet balance is written to the file.
- Existing order records are saved and updated.
- The order processing daemon thread shuts down safely before termination.

---

# 🧵 Concurrency Implementation
The project includes a background daemon thread:
```text
OrderProcessingTask
```

Responsibilities:
- Periodically checks pending orders in the kitchen queue.
- Automatically advances order status from `WAITING` → `PREPARING` → `READY`.
- Runs concurrently without interrupting user operations.
Safe ordering uses synchronized locking to ensure data consistency during simultaneous operations.

---

# ⚠️ Exception Handling
Custom checked exceptions improve reliability:
- `InsufficientBalanceException`: Thrown when a student tries to place an order exceeding their available wallet balance.
- `ItemNotFoundException`: Thrown when an invalid menu item ID is entered.

---

# 🧩 Core Java Concepts Demonstrated
- Object-Oriented Programming (OOP)
- Abstract Classes
- Inheritance & Polymorphism
- Interfaces
- Collections (`ArrayList`, `LinkedHashMap`)
- File Handling (`BufferedReader`, `BufferedWriter`)
- Exception Handling (`try-catch`, custom checked exceptions)
- Multithreading (Daemon thread with `Thread.sleep()`)
- Synchronization & Thread Safety
- Command-Line User Interaction (`java.util.Scanner`)

---

# 🎯 Learning Outcomes
This project demonstrates the implementation of a simplified canteen ordering and queue system while applying important Java programming concepts including:
- Modular software design.
- Thread-safe queue processing.
- Persistent data management without external database overhead.
- Exception-driven error handling.
- Clean command-line application architecture.

---

## 👩‍💻 Author
**Jiya Kalra**  

