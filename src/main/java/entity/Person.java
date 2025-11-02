package entity;

public abstract class Person {
    private String name;
    private String OIB;

    static Person[] allPersons =new Person[100];
    static Integer personCount=0;


    public static void printAllPersons(){
        for(int i=0;i<personCount;i++){
            if(allPersons[i] instanceof Doctor doktor)
            {

                doktor.basicInformation();
            }
            else if(allPersons[i] instanceof Patient patient)
            {
                patient.basicInformation();
            }
        }
    }


    static void addPerson(Person e){
        allPersons[personCount++]=e;
    }

    public String getName(){
        return name;
    }
    public String getOIB(){
        return OIB;
    }



    Person(String name, String OIB) {
        this.name=name;
        this.OIB=OIB;
    }

    abstract public void basicInformation();


}
