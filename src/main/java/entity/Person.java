package entity;


import java.util.*;

/**
 * Predstavlja osobu
 * Omogucuje obradu osobnih informacije osobe
 */
public abstract class Person  {
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
    public Person(String name, String OIB) {
        this.name=name;
        this.OIB=OIB;
    }
    public Person(){}

    static Map<String,String> getBasicInfo(Scanner sc)
    {
        Map<String,String> map=new HashMap<>();

        System.out.println("Unesite ime osobe");
        String ime=sc.nextLine();

        System.out.println("Unesite OIB osobe:");
        String oib=sc.nextLine();

        map.put("ime",ime);
        map.put("oib",oib);

        return map;

    }

    /**
     * Ispisuje ime i OIB
     */
    abstract public void basicInformation();


}
