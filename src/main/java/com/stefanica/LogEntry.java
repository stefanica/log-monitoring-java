package com.stefanica;

import java.time.LocalTime;
import java.time.Duration;

//Class used to create LogEntry objects using the log file
public class LogEntry {

    //The Process ID of a task/job
    private final int pid;
    //When the task/job started
    private LocalTime startTime;
    //When the task/job ended
    private LocalTime endTime;
    //The task/job description
    private final String description;

    //The duration of a task/job: endTime - startTime
    private Duration duration;
    //This will be the displayed message (warring or error)
    private String message;
    private Boolean containsWarningOrError;
    //The log entry type
    private final String entryType;

    //Constructor used to create each LogEntry object from the log file
    public LogEntry(int pid, String description) {
        this.pid = pid;
        //this.startTime = LocalTime.parse(startTime);
        this.description = description;
        //determine if the type of the log entry (Task OR Job)
        this.entryType = description.contains("task") ? "task" : "job";
    }

    public void setStartTime(String startTime) {
        this.startTime = LocalTime.parse(startTime);
    }

    public void setEndTime(String endTime) {
        this.endTime = LocalTime.parse(endTime);
    }

    public Boolean getContainsWarningOrError() {
        return containsWarningOrError;
    }

    //Sets log entry duration
    public void logDuration(String firstLogTime, String lastLogTime) {
        if (startTime != null && endTime != null) {
            calculateLogDuration(startTime, endTime);
        }
        if (startTime == null && endTime != null) {
            calculateLogDuration(LocalTime.parse(firstLogTime), endTime);
        }
        if (startTime != null && endTime == null) {
            calculateLogDuration(startTime, LocalTime.parse(lastLogTime));
        }
    }

    //Calculates task/job duration and set a warning or error message IF it's the case
    //Some logs don't have a START or END time, in this case we calculate the difference based on the first or last log
    public void calculateLogDuration(LocalTime start, LocalTime end) {
        duration = Duration.between(start, end);
        long taskDurationInMinutes = duration.toMinutes();

        if (taskDurationInMinutes >= 5 && taskDurationInMinutes < 10) {
            message = "Warning: The " + entryType + " took longer than 5 minutes.";
            containsWarningOrError = true;
        } else if (taskDurationInMinutes >= 10) {
            message = "Error: The " + entryType + " took longer than 10 minutes.";
            containsWarningOrError = true;
        } else {
            message = "";
            containsWarningOrError = false;
        }
    }

    //Returns a String representation of the LogEntry object
    public String toString() {
        //IF the log entry has a START and END time
        if (duration != null && startTime != null && endTime != null) {
            return entryType + ":" + pid + " Description:" + description + " START: " + startTime +
                    " END: " + endTime + " DURATION: " + duration.toMinutes() + " minutes " + message;
        }

        //IF the log entry object has no START or END time defined
        if (duration != null) {
            String start = (startTime != null) ? startTime.toString() : "not specified";
            String end = (endTime != null) ? endTime.toString() : "still running";
            return entryType + ":" + pid + " Description:" + description + " START: " + start +
                    " END: " + end + " DURATION: " + duration.toMinutes() + " minutes " + message;
        }

        //If toString() is called before duration is calculated OR
        //IF some error occurs and the log entry has no START and END time defined
        return entryType + ":" + pid + " Description:" + description + " Duration: undefined";
    }
}