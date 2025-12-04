package entity;

import java.util.*;

public class EmergencyRoom extends Department {

    protected static List<Patient> patients;
    private Set<Doctor> doctors;

    public EmergencyRoom()
    {
        super("Emergency Room");
        patients = new ArrayList<>();
        doctors = new HashSet<>();
    }

    public List<Patient> getPatients() {
        return patients;
    }

    @Override
    public void addDoctor(Scanner sc) {

    }

    @Override
    public void addPatient(Scanner sc) throws IndexOutOfBoundsException, PersonnelException {

    }

    @Override
    public void addRoom() {

    }

    @Override
    public List<Doctor> doctorSearchBySpecialty(Scanner sc) {
        return List.of();
    }

    @Override
    public List<Patient> patientSearchByDiagnosis(Scanner sc) {
        return List.of();
    }

    @Override
    public Optional<Patient> findPatientByName(String name) {
        return Optional.empty();
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
