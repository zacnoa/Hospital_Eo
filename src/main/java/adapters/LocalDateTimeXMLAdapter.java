package adapters;

import jakarta.xml.bind.annotation.adapters.XmlAdapter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LocalDateTimeXMLAdapter extends XmlAdapter<String, LocalDateTime> {

    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

    @Override
    public LocalDateTime unmarshal(String v) throws Exception
    {
        return LocalDateTime.parse(v, formatter);
    }
    @Override
    public String marshal(LocalDateTime v) throws Exception {
        return v.format(formatter);
    }




}
