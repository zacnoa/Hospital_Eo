package entity;

import jakarta.json.bind.annotation.JsonbProperty;
import jakarta.json.bind.annotation.JsonbTransient;
import jakarta.xml.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@XmlRootElement(name = "doctor")
@XmlAccessorType(XmlAccessType.PROPERTY)
public final class Doctor extends Employee implements PrintableMenuSelection, Serializable {

    private String specialty;

    @XmlTransient
    @JsonbTransient
    private List<String> patients = new ArrayList<>();

    private final static Logger logger = LoggerFactory.getLogger(Doctor.class);

    public Doctor() {}

    private Doctor(DoctorBuilder doctorBuilder) {
        super(doctorBuilder.name, doctorBuilder.OIB, doctorBuilder.salary);
        this.specialty = doctorBuilder.specialty;
        this.patients = doctorBuilder.patients;
    }

    @XmlElement(name = "specialty")
    @JsonbProperty("specialty")
    public String getSpecialty() {
        return specialty;
    }

    @JsonbProperty("specialty")
    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }

    @XmlTransient
    @JsonbTransient
    public List<Patient> getPatients() {
        return PersonnelStorage.getPatientsByIds(patients);
    }

    @XmlTransient
    @JsonbTransient
    public void setPatients(List<Patient> patients) {
        this.patients = patients.stream()
                .map(Patient::getId)
                .toList();
    }

    @XmlElementWrapper(name = "patients")
    @XmlElement(name = "patientId")
    @JsonbProperty("patientIds")
    public List<String> getPatientIds() {
        return patients;
    }

    @JsonbProperty("patientIds")
    public void setPatientIds(List<String> patients) {
        this.patients = patients;
    }

    public void addPatient(Patient patient) {
        patients.add(patient.getId());
    }

    @JsonbTransient
    @Override
    public String getSelectionLine() {
        return super.getName();
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
        logger.info("Stvoren je doktor s imenom: {}", name);
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
