package entity;

import jakarta.json.bind.annotation.JsonbTransient;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public final class Doctor extends Employee implements PrintableMenuSelection, Serializable {

    private String specialty;
    private List<String> patientIds = new ArrayList<>();


    public Doctor() {}

    private Doctor(DoctorBuilder doctorBuilder) {
        super(doctorBuilder.name, doctorBuilder.OIB, doctorBuilder.salary);
        this.specialty = doctorBuilder.specialty;
        this.patientIds = doctorBuilder.patients;
    }



    @JsonbTransient
    public List<Patient> getPatients() {
        return PersonnelStorage.getPatientsByIds(patientIds);
    }

    @JsonbTransient
    public void setPatients(List<Patient> patients) {
        this.patientIds = patients.stream()
                .map(Patient::getId)
                .toList();
    }

    public String getSpecialty() {
        return specialty;
    }


    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }

    public List<String> getPatientIds() {
        return patientIds;
    }

    public void setPatientIds(List<String> patients) {
        this.patientIds = patients;
    }

    public void addPatient(Patient patient) {
        patientIds.add(patient.getId());
    }


    public static Doctor generateDoctor(Scanner sc) {
        System.out.println("Unesite ime doktora:");
        String name = sc.nextLine();
        if (name.isEmpty()) {
            throw new IllegalArgumentException("Ime doktora ne smije biti prazno");
        }

        System.out.println("Unesite OIB doktora:");
        String OIB = sc.nextLine();

        System.out.println("Unesite specijalizaciju doktora:");
        String specialty = sc.nextLine();

        System.out.println("Unesite placu doktora:");
        Double salary = sc.nextDouble();
        sc.skip("\n");

        Doctor doctor = new Doctor.DoctorBuilder(name, OIB, specialty, salary).build();
        return doctor;
    }

    @Override
    public void basicInformation() {
        System.out.println("Doktor: " + super.getName() + " specijalizacija: " + specialty);
    }

    public static class DoctorBuilder {
        private String name;
        private String OIB;
        private String specialty;
        private Double salary;
        private List<String> patients = new ArrayList<>();

        public DoctorBuilder(String name, String OIB, String specialty, Double salary) {
            this.name = name;
            this.OIB = OIB;
            this.specialty = specialty;
            this.salary = salary;
        }

        public DoctorBuilder patients(List<Patient> patients) {
            this.patients = patients.stream()
                    .map(Patient::getId)
                    .toList();
            return this;
        }

        public Doctor build() {
            return new Doctor(this);
        }
    }
}
