package entity;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public abstract class Department implements PrintableMenuSelection {

    protected String name;

      public Department(String name)
    {
        this.name=name;
    }

    @Override
    public String getSelectionLine()
    {
        return name;
    }
    public abstract List<Patient> getPatients();

    public abstract void addDoctor(Scanner sc);

    public abstract void addPatient(Scanner sc) throws IndexOutOfBoundsException, PersonnelException;

    public abstract void addRoom();

    public abstract List<Doctor> doctorSearchBySpecialty(Scanner sc);

    public abstract List<Patient> patientSearchByDiagnosis(Scanner sc);

    public abstract Optional<Patient> findPatientByName(String name);
}
