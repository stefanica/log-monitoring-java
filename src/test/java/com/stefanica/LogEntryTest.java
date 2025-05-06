package com.stefanica;

import org.junit.jupiter.api.Test;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

class LogEntryTest {

    //Checks if the log entry will we labeled as task, according to description
    @Test
    void testEntryTypeTask() {
        LogEntry logEntry = new LogEntry(101, "scheduled task 796");
        assertEquals("task", logEntry.getEntryType());
    }

    //Checks if the log entry will be labeled as job, according to description
    @Test
    void testEntryTypeJob() {
        LogEntry logEntry = new LogEntry(102, "background job wmy");
        assertEquals("job", logEntry.getEntryType());
    }

    //Test to verify if the log duration is calculated correctly
    @Test
    void testLogDuration() {
        LogEntry logEntry = new LogEntry(103, "background job dej");
        logEntry.setStartTime("10:00");
        logEntry.setEndTime("10:07");
        logEntry.logDuration(null,null);
        assertEquals(Duration.ofMinutes(7), logEntry.getDuration());
    }

    //Tests to see is we will get a Warning message is the log duration exceeds 5 minutes
    @Test
    void testWarningMessage() {
        LogEntry logEntry = new LogEntry(104, "scheduled task 074");
        logEntry.setStartTime("10:00");
        logEntry.setEndTime("10:07");
        logEntry.logDuration(null,null);
        assertTrue(logEntry.getContainsWarningOrError());
        assertTrue(logEntry.getMessage().contains("Warning"));
    }

    //Tests to see is we will get an Error message is the log duration exceeds 10 minutes
    @Test
    void testErrorMessage() {
        LogEntry logEntry = new LogEntry(105, "scheduled task 051");
        logEntry.setStartTime("10:00");
        logEntry.setEndTime("10:11");
        logEntry.logDuration(null,null);
        assertTrue(logEntry.getContainsWarningOrError());
        assertTrue(logEntry.getMessage().contains("Error"));
    }

    //Tests the output when a LogEntry has both StartTime and EndTime
    @Test
    void testToStringNormalOutput() {
        LogEntry logEntry = new LogEntry(106, "background job wmy");
        logEntry.setStartTime("11:00");
        logEntry.setEndTime("11:04");
        logEntry.logDuration(null,null);
        String output = logEntry.toString();
        assertTrue(output.contains("START: 11:00"));
        assertTrue(output.contains("END: 11:04"));
        assertTrue(output.contains("DURATION: 4 minutes"));
    }

    //Tests the output when a LogEntry has only StartTime. The EndTime corresponds to the last entry from logs.log
    @Test
    void testToStringNoEndTimeOutput() {
        LogEntry logEntry = new LogEntry(107, "scheduled task 016");
        logEntry.setStartTime("12:05");
        logEntry.logDuration(null,"12:19");
        String output = logEntry.toString();
        assertTrue(output.contains("START: 12:05"));
        assertTrue(output.contains("END: still running"));
    }
}