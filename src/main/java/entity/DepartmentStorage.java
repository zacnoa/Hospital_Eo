package entity;

import java.util.*;

public  class DepartmentStorage
{
    public final static List<Department> departments=new ArrayList<>();
    public final static  EmergencyRoom emergencyRoom=new EmergencyRoom();

    private  DepartmentStorage()
    {

    }

    public static void  addDepartment(Scanner sc)
    {
        Department department=Department.generateDepartment(sc);
        departments.add(department);

    }
}
