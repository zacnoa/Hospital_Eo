package entity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class EmergencyRoom {

    protected static List<Patient> patients;
    private Set<Doctor> doctors;

    public EmergencyRoom()
    {
        patients = new ArrayList<>();
        doctors = new HashSet<>();
    }

    public List<Patient> getPatients() {
        return patients;
    }

    public Set<Doctor> getDoctors() {
        return doctors;
    }
    public void addDoctor(Doctor doctor)
    {
        this.doctors.add(doctor);
    }
    public void addPatient(ERPatient patient)
    {
        this.patients.add(patient);
    }
}
