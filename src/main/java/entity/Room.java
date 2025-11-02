package entity;

import java.util.Scanner;

public class Room {
    private Integer patientCount;
    private Patient[] patients;
    private Integer id;


    public Room(Integer id)
    {
        this.id=id;
        this.patientCount =0;
        patients = new Patient[10];
    }

    public Integer getId() {
        return id;
    }


    public Integer getPatientCount() {
        return patientCount;
    }


    public Patient[] getPatients() {
        return patients;
    }

    public void addPatient(Patient patient) {
        this.patients[patientCount++] = patient;
    }


}
