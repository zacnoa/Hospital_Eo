package entity;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

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

    public  static List<String> getAllPatientsId()
    {
        return DepartmentStorage.departments.stream()
                .flatMap(department->department.getPatients().stream())
                .map(n->n.getId())
                .toList();
    }
    public static List<String> getAllDoctorsId()
    {
        return DepartmentStorage.departments.stream()
                .flatMap(department->department.getDoctors().stream())
                .map(n->n.getId())
                .toList();
    }
    public static List<String> getAllRoomsId()
    {
        return DepartmentStorage.departments.stream()
                .flatMap(department->department.getRooms().stream())
                .map(n->n.getId())
                .toList();
    }
    public static Visitor addLegalGuardian(Scanner sc)
    {
        Map<String,String> map=Person.getBasicInfo(sc);
        Visitor visitor=new Visitor(map.get("ime"),map.get("oib"));
        return visitor;
    }




}
