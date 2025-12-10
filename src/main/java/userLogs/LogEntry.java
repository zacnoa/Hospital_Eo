package userLogs;

import adapters.LocalDateTimeXMLAdapter;
import jakarta.xml.bind.annotation.*;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


import java.time.LocalDateTime;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class LogEntry {


    private String message;

    @XmlJavaTypeAdapter(LocalDateTimeXMLAdapter.class)
    private LocalDateTime timestamp;


    public LogEntry(){};


    public LogEntry(String message)
    {
        this.message=message;
        this.timestamp=LocalDateTime.now();
    }

    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public LocalDateTime getTimestamp() {
        return timestamp;
    }
    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
