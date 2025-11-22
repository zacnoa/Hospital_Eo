package entity;

import java.util.List;

public class Utility {


    public static <T extends PrintableMenuSelection> void printMenuSelection(List<T> items)
    {
        for(int i=0;i<items.size();i++){
            System.out.println((i+1) + ".) " + items.get(i).getSelectionLine() );
        }
    }

    static  public void printCollection(List<? extends Person> list)
    {
        list.forEach(Person::basicInformation);
    }
    static public <T> void movePatientToDepartment(List<? extends T> source, List<? super T> destination,Integer patientIndex)
    {

        destination.add(source.get(patientIndex));
        source.remove(patientIndex);
    }








}
