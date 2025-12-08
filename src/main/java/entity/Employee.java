package entity;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlTransient;

@XmlRootElement(name="employee")
public abstract class Employee extends Person {

    private Double salary;

    public Employee(String name, String OIB, Double salary) {
        super(name, OIB);
        this.salary = salary;
    }

    public Employee(){}

    @XmlElement(name = "salary")
    public Double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }
}



