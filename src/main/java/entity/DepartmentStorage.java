package entity;

import java.io.IOException;
import java.util.*;

public  class DepartmentStorage
{
    public  static List<Department> departments= new ArrayList<>();


    private  DepartmentStorage()
    {

    }

    public static void  addDepartment(Scanner sc) throws IOException
    {
        Department department = Department.generateDepartment(sc);
        departments.add(department);

        DataBaseManager.jsonSerialization(departments,DataType.DEPARTMENT);

    }
    public static List<Department>  getDepartments()
    {
        return departments;
    }
    public static void setDepartments(List<Department> newDepartments)
    {
        departments = newDepartments;

    }

    public  static List<Patient> getAllPatients()
    {
        return DepartmentStorage.departments.stream()
                .flatMap(department->department.getPatients().stream())
                .toList();
    }
    public static List<Doctor> getAllDoctors()
    {
        return DepartmentStorage.departments.stream()
                .flatMap(department->department.getDoctors().stream())

                .toList();
    }
    public static List<Room> getAllRooms()
    {
        return DepartmentStorage.departments.stream()
                .flatMap(department->department.getRooms().stream())
                .toList();
    }


}
