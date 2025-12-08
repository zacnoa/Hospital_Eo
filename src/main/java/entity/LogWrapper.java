package entity;

import jakarta.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "hospitalLog")
@XmlAccessorType(XmlAccessType.PROPERTY)  // PROMIJENI OVO!
public class LogWrapper {

    private List<LogEntry> logEntries = new ArrayList<>();

    public LogWrapper() {}

    public LogWrapper(List<LogEntry> logEntries) {
        this.logEntries = logEntries;
    }

    @XmlElement(name = "logEntry")  // ANOTACIJA NA GETTERU
    public List<LogEntry> getLogEntries() {
        return logEntries;
    }

    public void setLogEntries(List<LogEntry> logEntries) {
        this.logEntries = logEntries;
    }

    public void addLogEntry(LogEntry entry) {
        this.logEntries.add(entry);
    }
}