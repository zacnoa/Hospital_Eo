package entity;

import java.util.*;

public  class DepartmentStorage
{
    public final static List<Department> departments =new ArrayList<>(List.of(new EmergencyRoom()));


    private  DepartmentStorage()
    {
        departments.add(new EmergencyRoom());
    }

    public static void  addDepartment(Scanner sc)
    {
        RegularDepartment regularDepartment = RegularDepartment.generateDepartment(sc);
        departments.add(regularDepartment);

    }
}
