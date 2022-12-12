package ua.pp.kusochok.services;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Logus {
    public static void log() {
        StackTraceElement stackTraceElement = Thread.currentThread().getStackTrace()[2];

        System.out.println(
                "Call at " +
                        LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SS")) +
                        " from " + stackTraceElement.getClassName() +
                        "." + stackTraceElement.getMethodName() +
                        ":" + stackTraceElement.getLineNumber()
        );
    }
}
