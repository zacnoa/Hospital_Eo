package entity;

import jakarta.json.bind.annotation.JsonbProperty;
import jakarta.xml.bind.annotation.*;
import java.io.Serializable;
import java.util.Map;
import java.util.Scanner;


@XmlAccessorType(XmlAccessType.PROPERTY)
public class Visitor extends Person implements Serializable {

    public Visitor(String ime, String oib) {
        super(ime, oib);
    }

    public Visitor() {}

    @Override
    public void basicInformation() {
        System.out.println("Posjetitelj:" + super.getName() + " " + super.getOIB());
    }

    public static Visitor generateVisitor(Scanner sc) {
        Map<String, String> map = Person.getBasicInfo(sc);
        return new Visitor(map.get("ime"), map.get("oib"));
    }
}