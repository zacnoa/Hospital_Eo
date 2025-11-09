package entity;


import java.util.Comparator;
import java.util.SequencedSet;
import java.util.TreeSet;

/**
 * Predstavlja osobu
 * Omogucuje obradu osobnih informacije osobe
 */
public abstract class Person {
    private String name;
    private String OIB;
    public static SequencedSet<Person> allPersons=new TreeSet<>(Comparator.comparing(Person::getName).thenComparing(Person::getOIB));


    /**
     * Ispisuje staticno polje allPersons
     */
    public static void printAllPersons(){

        allPersons.forEach(Person::basicInformation);
    }

    /**
     *
     * @param person Osoba
     */
    static void addPerson(Person person){
        allPersons.add(person);
    }

    /**
     *
     * @return Ime
     */
    public String getName(){
        return name;
    }

    /**
     *
     * @return OIB
     */
    public String getOIB(){
        return OIB;
    }


    /**
     *
     * @param name Ime
     * @param OIB OIB
     */
    Person(String name, String OIB) {
        this.name=name;
        this.OIB=OIB;
    }

    /**
     * Ispisuje ime i OIB
     */
    abstract public void basicInformation();


}
