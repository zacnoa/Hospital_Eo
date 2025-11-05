package entity;


/**
 * Predstavlja osobu
 * Omogucuje obradu osobnih informacije osobe
 */
public abstract class Person {
    private String name;
    private String OIB;

    static Person[] allPersons =new Person[100];
    static Integer personCount=0;

    /**
     * Ispisuje staticno polje allPersons
     */
    public static void printAllPersons(){
        for(int i=0;i<personCount;i++){
            if(allPersons[i] instanceof Doctor doctor)
            {

                doctor.basicInformation();
            }
            else if(allPersons[i] instanceof Patient patient)
            {
                patient.basicInformation();
            }
        }
    }

    /**
     *
     * @param person Osoba
     */
    static void addPerson(Person person){
        allPersons[personCount++]= person;
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
