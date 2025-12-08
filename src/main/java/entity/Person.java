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
@XmlAccessorType(XmlAccessType.PROPERTY)  // DODAJ OVO
public abstract class Person implements Serializable {

    private String name;
    private String OIB;
    private String id;

    // OVO NE SERIJALIZIRAJ U XML/JSON - to je samo za runtime
    @XmlTransient
    @JsonbTransient
    public static SequencedSet<Person> allPersons = new TreeSet<>(
            Comparator.comparing(Person::getName).thenComparing(Person::getOIB)
    );

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
    @XmlElement(name = "name")  // DODAJ OVO
    @JsonbProperty("name")
    public String getName() {
        return name;
    }

    @JsonbProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return OIB
     */
    @XmlElement(name = "oib")
    @JsonbProperty("oib")
    public String getOIB() {
        return OIB;
    }

    @JsonbProperty("oib")
    public void setOIB(String oib) {
        this.OIB = oib;
    }

    @XmlElement(name = "id")
    @JsonbProperty("id")
    public String getId() {
        return id;
    }

    @JsonbProperty("id")
    public void setId(String id) {
        this.id = id;
    }

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

    // PRAZAN KONSTRUKTOR za JAXB
    public Person() {}

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

    /**
     * Ispisuje ime i OIB
     */
    abstract public void basicInformation();
}