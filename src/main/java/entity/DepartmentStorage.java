package entity;

import java.util.*;

public  class DepartmentStorage
{
    public  static List<Department> departments = new ArrayList<>(List.of(new EmergencyRoom()));


    private  DepartmentStorage()
    {
        departments.add(new EmergencyRoom());
    }

    public static void  addDepartment(Scanner sc)
    {
        RegularDepartment regularDepartment = RegularDepartment.generateDepartment(sc);
        departments.add(regularDepartment);

    }
    public List<Department>  getDepartments()
    {
        return departments;
    }
    public void setDepartments(List<Department> departments)
    {
        this.departments = departments;
    }
}
