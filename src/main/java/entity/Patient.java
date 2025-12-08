package entity;

import jakarta.json.bind.annotation.JsonbProperty;
import jakarta.json.bind.annotation.JsonbTransient;
import jakarta.xml.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;
import java.util.UUID;

import static entity.Utility.addLegalGuardian;

@XmlRootElement(name = "patient")

public class Patient extends Person implements PrintableMenuSelection, Serializable {

    private String doctorId;
    private String diagnosis;
    public PatientStatus status;
    private String roomId;

    private final static Logger logger = LoggerFactory.getLogger(Patient.class);


    protected Patient(PatientBuilder patientBuilder) {
        super(patientBuilder.name, patientBuilder.OIB);
        this.diagnosis = patientBuilder.diagnosis;
        this.status = patientBuilder.status;
    }

    public Patient() {}


    @XmlTransient
    @JsonbTransient
    public Doctor getDoctor() {
        return PersonnelStorage.findDoctor(doctorId);
    }

    @XmlTransient
    @JsonbTransient
    public void setDoctor(Doctor doctor) {
        this.doctorId = doctor != null ? doctor.getId() : null;
    }


    @XmlElement(name = "doctorId")  // DODAJ OVO
    @JsonbProperty("doctorId")
    public String getDoctorId() {
        return doctorId;
    }

    @JsonbProperty("doctorId")
    public void setDoctorId(String id) {
        this.doctorId = id;
    }


    @XmlTransient
    @JsonbTransient
    public Room getRoom() {
        return PersonnelStorage.findRoom(roomId);
    }

    @XmlTransient
    @JsonbTransient
    public void setRoom(Room room) {
        this.roomId = room != null ? room.getId() : null;
    }


    @XmlElement(name = "roomId")
    @JsonbProperty("roomId")
    public String getRoomId() {
        return roomId;
    }

    @JsonbProperty("roomId")
    public void setRoomId(String id) {
        this.roomId = id;
    }


    @XmlElement(name = "diagnosis")
    @JsonbProperty("diagnosis")
    public String getDiagnosis() {
        return diagnosis;
    }

    @JsonbProperty("diagnosis")
    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }


    @XmlElement(name = "status")
    @JsonbProperty("status")
    public PatientStatus getStatus() {
        return status;
    }

    @JsonbProperty("status")
    public void setStatus(PatientStatus status) {
        this.status = status;
    }




    // OSTALE METODE
    @Override
    public void basicInformation() {
        System.out.println("Pacijent: " + super.getName() + " OIB: " + super.getOIB());
    }

    @JsonbTransient
    @Override
    public String getSelectionLine() {
        return this.getName();
    }


    static Map<String, String> getBasicInfo(Scanner sc) {
        Map<String, String> map = Person.getBasicInfo(sc);
        map.put("dijagnoza", generateDiagnosis(sc));
        return map;
    }

    public static String generateDiagnosis(Scanner sc) {
        System.out.println("Unesite dijagnozu pacijenta");
        return sc.nextLine();
    }

    public static Patient generatePatient(Scanner sc) throws IllegalArgumentException {
        Map<String, String> map = getBasicInfo(sc);
        System.out.println("""
                Da li je pacijent maloljetan(Izaberite redni broj)
                1.)Da
                2.)Ne
                """);

        if (map.containsValue("")) {
            throw new IllegalArgumentException("Niti jedno polje nesmije biti prazno");
        }

        if ("1".equals(sc.nextLine())) {
            return new UnderagePatient.UnderagePatientBuilder(
                    map.get("ime"),
                    map.get("oib"),
                    map.get("dijagnoza"),
                    PatientStatus.HOSPITALIZED)
                    .legalGuardian(addLegalGuardian(sc))
                    .build();
        }

        return new Patient.PatientBuilder(
                map.get("ime"),
                map.get("oib"),
                map.get("dijagnoza"),
                PatientStatus.HOSPITALIZED)
                .build();
    }


    public static class PatientBuilder<T extends PatientBuilder<T>> {
        String name;
        String OIB;
        String diagnosis;
        PatientStatus status;
        Optional<Doctor> doctor;
        Optional<Room> room;

        public PatientBuilder(String name, String OIB, String diagnosis, PatientStatus status) {
            this.name = name;
            this.OIB = OIB;
            this.diagnosis = diagnosis;
            this.status = status;
        }

        public T doctor(Optional<Doctor> doctor) {
            this.doctor = doctor;
            return (T) this;
        }

        public T room(Optional<Room> room) {
            this.room = room;
            return (T) this;
        }

        public Patient build() {
            return new Patient(this);
        }
    }
}