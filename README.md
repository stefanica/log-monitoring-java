# Log Monitoring Application

This is a simple Java-based log monitoring application developed as part of a coding task.

## 📝 Task Description

The application reads a `.log` file containing job/task logs, parses each entry, calculates execution durations, and produces warnings or errors based on defined thresholds:

- ⚠️ **Warning**: Duration exceeds 5 minutes
- ❌ **Error**: Duration exceeds 10 minutes

## ✅ Features

- Parses log entries from a file (`logs.log`)
- Tracks job/task start and end times
- Calculates total execution time per task
- Logs results to console and generates a timestamped report file with warnings and errors
- Tests the functionality of LogEntry class using JUnit 5

## 📁 Input Format

The `logs.log` file must be placed in: ('src/main/resources/inputLogs/') folder


## 📄 Output

- Console output with full task/job information
- Report file with only warnings and errors:
    - Generated at `src/main/resources/reports/report-<timestamp>.log`


## 💻 Code Structure
- Main application logic: src/main/java/com/stefanica/Main.class
- Class to create individual log entry objects: src/main/java/com/stefanica/LogEntry.java
- Unit tests for LogEntry class: src/test/java/com/stefanica/LogEntryTest.java


## 📦 Technologies
- Java 17
- Maven
- JUnit 5
- IntelliJ IDEA