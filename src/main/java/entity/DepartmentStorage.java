package entity;

import java.util.*;

public  class DepartmentStorage
{
    public  static List<Department> departments = new ArrayList<>();


    private  DepartmentStorage()
    {
    }

    public static void  addDepartment(Scanner sc)
    {
        Department department = Department.generateDepartment(sc);
        departments.add(department);

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
