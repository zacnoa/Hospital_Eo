package entity;

import jakarta.xml.bind.annotation.*;
import java.time.LocalDateTime;

@XmlRootElement(name = "logEntry")
@XmlAccessorType(XmlAccessType.PROPERTY)
public class LogEntry {

    private DataType type;
    private Doctor doctor;
    private Patient patient;
    private Room room;
    private Department department;
    private Visitor visitor;
    private String message;

    // Prazan konstruktor za JAXB
    public LogEntry() {
    }

    // Konstruktor za jednostavne logove
    public LogEntry(DataType type, String message) {
        this.type = type;
        this.message = message;
    }

    // Konstruktor za doktora
    public LogEntry(DataType type, Doctor doctor) {
        this.type = type;
        this.doctor = doctor;
        this.message = "Kreiran doktor: " + (doctor != null ? doctor.getName() : "N/A");
    }

    // Konstruktor za pacijenta
    public LogEntry(DataType type, Patient patient) {
        this.type = type;
        this.patient = patient;
        this.message = "Primljen pacijent: " + (patient != null ? patient.getName() : "N/A");
    }

    // Konstruktor za sobu
    public LogEntry(DataType type, Room room) {
        this.type = type;
        this.room = room;
        this.message = "Dodana soba: " + (room != null ? room.getId() : "N/A");
    }

    // Konstruktor za odjel
    public LogEntry(DataType type, Department department) {
        this.type = type;
        this.department = department;
        this.message = "Kreiran odjel: " + (department != null ? department.getName() : "N/A");
    }

    // Konstruktor za posjetitelja
    public LogEntry(DataType type, Visitor visitor) {
        this.type = type;
        this.visitor = visitor;
        this.message = "Dodan posjetitelj: " + (visitor != null ? visitor.getName() : "N/A");
    }

    // GETTERI sa @XmlElement anotacijama
    @XmlElement(name = "type")
    public DataType getType() {
        return type;
    }

    public void setType(DataType type) {
        this.type = type;
    }

    @XmlElement(name = "doctor")
    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    @XmlElement(name = "patient")
    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    @XmlElement(name = "room")
    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    @XmlElement(name = "department")
    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    @XmlElement(name = "visitor")
    public Visitor getVisitor() {
        return visitor;
    }

    public void setVisitor(Visitor visitor) {
        this.visitor = visitor;
    }

    @XmlElement(name = "message")
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}