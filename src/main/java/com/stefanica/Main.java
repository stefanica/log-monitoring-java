package com.stefanica;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.*;

public class Main {
    public static void main(String[] args) {

        //This LinkedHashMap will be used to store all the Task/Job logs from the .jobs file, as LogEntry objects
        LinkedHashMap<Integer, LogEntry> logsMap = new LinkedHashMap<>();

        //Get the time for the first log and the time for the last log
        //Used to calculate the time difference for log entries that don't have a START or END time
        String firstLogTime = "";
        String lastLogTime = "";

        //The try-catch block will parse the logs file and create a map of LogEntry objects
        try (Scanner scanner = new Scanner(Paths.get("src/main/resources/inputLogs/logs.log"))) {
            //The while loop iterates through the scanner object, creates LogEntry objects
            while (scanner.hasNextLine()) {
                //Get each task/job, line by line
                String row = scanner.nextLine();
                //Convert the string into a string array of task/job components (time, description, START/END, PID)
                String[] rowToArray = row.split(",");

                //Assign each task/job component to a variable to increase readability
                String logTime = rowToArray[0];
                String logDescription = rowToArray[1];
                String logTimeStatus = rowToArray[2].trim(); //This is START or END
                int logPID = Integer.parseInt(rowToArray[3]);

                if (firstLogTime.isEmpty()) {
                    firstLogTime = logTime;
                }
                if (!scanner.hasNextLine()) {
                    lastLogTime = logTime;
                }

                //If the lobsMaps doesn't contain the current logPID as key, create a new LogEntry object and add to map
                if (!logsMap.containsKey(logPID)) {
                    logsMap.put(logPID, new LogEntry(logPID, logDescription));
                }

                //Set START or END time for the current log
                if (logTimeStatus.equals("START")) {
                    logsMap.get(logPID).setStartTime(logTime);
                } else {
                    logsMap.get(logPID).setEndTime(logTime);
                }
            }
        } catch (Exception e) {
            System.out.println("Error:" + e.getMessage());
        }

        //Calculate task duration for each task
        for (LogEntry entry : logsMap.values()) {
            entry.logDuration(firstLogTime, lastLogTime);
        }

        //Output the results for each task/job to console
        logsMap.values().forEach(x -> System.out.println(x.toString()));

        //Get current time in seconds -> used in naming the new .log file that contains the warnings ane errors
        long timeStamp = System.currentTimeMillis() / 1000;
        String fileName = "report-" + timeStamp + ".log";
        String filePath = "src/main/resources/reports/" + fileName;

        //Creates the reports folder if it's not present
        File directory = new File("src/main/resources/reports/");
        if (!directory.exists()) {
            if(directory.mkdirs()) {
                System.out.println("Directory created successfully.");
            } else {
                System.out.println("Failed to create directory: " + directory.getAbsolutePath());
            }
        }

        //Save the logs with warnings and errors into a report-generatedTime.log file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (LogEntry entry : logsMap.values()) {
                if (entry.getContainsWarningOrError()) {
                    writer.write(entry.toString());
                    writer.newLine();
                }
            }
        } catch (IOException e) {
            System.out.println("Error:" + e.getMessage());
        }

    }
}