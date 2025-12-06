package entity;

import java.util.List;

public class Utility {


    public static <T extends PrintableMenuSelection> void printMenuSelection(List<T> items)
    {
        System.out.println("Unesite redni broj pored zeljene opcije(ili X ako zelite natrag)");
        for(int i=0;i<items.size();i++){
            System.out.println((i+1) + ".) " + items.get(i).getSelectionLine() );
        }
        System.out.println("X) Kraj");
    }

    static  public void printCollection(List<? extends Person> list)
    {
        list.forEach(Person::basicInformation);
    }
    static public void movePatientToDepartment(List<? extends Patient> source, List<? super Patient> destination,Integer patientIndex)
    {

        destination.add(source.get(patientIndex));
        source.remove(patientIndex);
    }



}
