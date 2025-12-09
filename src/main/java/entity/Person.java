package entity;

import jakarta.json.bind.annotation.JsonbProperty;
import jakarta.json.bind.annotation.JsonbTransient;
import jakarta.xml.bind.annotation.*;
import java.io.Serializable;
import java.util.*;

/**
 * Predstavlja osobu
 * Omogucuje obradu osobnih informacije osobe
 */

public abstract class Person implements Serializable,PrintableMenuSelection {

    private String name;
    private String OIB;
    private String id;


    public static SequencedSet<Person> allPersons = new TreeSet<>(
            Comparator.comparing(Person::getName).thenComparing(Person::getOIB)
    );

    /**
     * @param name Ime
     * @param OIB OIB
     */
    public Person(String name, String OIB) {
        this.name = name;
        this.OIB = OIB;
        this.id = UUID.randomUUID().toString();
        Person.addPerson(this);
    }

    public Person() {}




    /**
     * Ispisuje staticno polje allPersons
     */
    public static void printAllPersons() {
        allPersons.forEach(Person::basicInformation);
    }

    /**
     * @param person Osoba
     */
    static void addPerson(Person person) {
        allPersons.add(person);
    }

    /**
     * @return Ime
     */

    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return OIB
     */

    public String getOIB() {
        return OIB;
    }


    public void setOIB(String oib) {
        this.OIB = oib;
    }


    public String getId() {
        return id;
    }


    public void setId(String id) {
        this.id = id;
    }




    static Map<String, String> getBasicInfo(Scanner sc) {
        Map<String, String> map = new HashMap<>();
        System.out.println("Unesite ime osobe");
        String ime = sc.nextLine();
        System.out.println("Unesite OIB osobe:");
        String oib = sc.nextLine();
        map.put("ime", ime);
        map.put("oib", oib);
        return map;
    }
    @JsonbTransient
    public String getSelectionLine() {
        return this.name;
    }

    /**
     * Ispisuje ime i OIB
     */
    abstract public void basicInformation();
}