package userLogs;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElementWrapper;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class LogWrapper {

    private List<LogEntry>  logEntries;

    public LogWrapper()
    {
        this.logEntries = new ArrayList<LogEntry>();
    }
    protected void addLogEntry(LogEntry entry)
    {
        this.logEntries.add(entry);
    }
    protected void printLog()
    {
        System.out.println("Log of user commands chosen");
        for(LogEntry entry: this.logEntries)
        {
            System.out.println(entry.getMessage() + " || " + entry.getTimestamp().format(DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm:ss")));
        }
    }



}
