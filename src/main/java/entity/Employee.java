package entity;

 abstract public class Employee extends Person {

    Double salary;

    public Employee(String name,String OIB,Double salary)
    {
        super(name,OIB);
        this.salary=salary;
    }
    public Employee(){};

    public Double getSalary()
    {
        return salary;
    }
    public void setSalary(Double salary)
    {
        this.salary=salary;
    }






}
