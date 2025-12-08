package entity;

import jakarta.json.bind.annotation.JsonbProperty;
import jakarta.json.bind.annotation.JsonbTransient;
import jakarta.xml.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

@XmlRootElement(name="department")
@XmlAccessorType(XmlAccessType.PROPERTY)
public class Department implements PrintableMenuSelection, Serializable {

    private String name;


    private List<String> doctors = new ArrayList<>();


    private List<String> rooms = new ArrayList<>();


    private List<String> patients = new ArrayList<>();


    private List<String> visitors = new ArrayList<>();

    private static final Logger logger = LoggerFactory.getLogger(Department.class);

    public Department(String name) {
        this.name = name;
    }

    public Department() {}

    @XmlElement(name = "name")
    @JsonbProperty("name")
    public String getName() {
        return name;
    }

    @JsonbProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    @XmlTransient
    @JsonbTransient
    public List<Doctor> getDoctors() {
        return PersonnelStorage.getDoctorsByIds(doctors);
    }

    @XmlTransient
    @JsonbTransient
    public void setDoctors(List<Doctor> list) {
        this.doctors = list.stream().map(Doctor::getId).toList();
    }

    @XmlElementWrapper(name = "doctors")
    @XmlElement(name = "doctorId")
    @JsonbProperty("doctorIds")
    public List<String> getDoctorIds() {
        return doctors;
    }

    @JsonbProperty("doctorIds")
    public void setDoctorIds(List<String> ids) {
        this.doctors = ids;
    }

    @XmlTransient
    @JsonbTransient
    public List<Room> getRooms() {
        return PersonnelStorage.getRoomsByIds(rooms);
    }

    @XmlTransient
    @JsonbTransient
    public void setRooms(List<Room> list) {
        this.rooms = list.stream().map(Room::getId).toList();
    }

    @XmlElementWrapper(name = "rooms")
    @XmlElement(name = "roomId")
    @JsonbProperty("roomIds")
    public List<String> getRoomIds() {
        return rooms;
    }

    @JsonbProperty("roomIds")
    public void setRoomIds(List<String> ids) {
        this.rooms = ids;
    }

    @XmlTransient
    @JsonbTransient
    public List<Patient> getPatients() {
        return PersonnelStorage.getPatientsByIds(patients);
    }

    @XmlTransient
    @JsonbTransient
    public void setPatients(List<Patient> list) {
        this.patients = list.stream().map(Patient::getId).toList();
    }

    @XmlElementWrapper(name = "patients")
    @XmlElement(name = "patientId")
    @JsonbProperty("patientIds")
    public List<String> getPatientIds() {
        return patients;
    }

    @JsonbProperty("patientIds")
    public void setPatientIds(List<String> ids) {
        this.patients = ids;
    }

    @XmlTransient
    @JsonbTransient
    public List<Visitor> getVisitors() {
        return PersonnelStorage.getVisitorsByIds(visitors);
    }

    @XmlTransient
    @JsonbTransient
    public void setVisitors(List<Visitor> list) {
        this.visitors = list.stream().map(Visitor::getId).toList();
    }

    @XmlElementWrapper(name = "visitors")
    @XmlElement(name = "visitorId")
    @JsonbProperty("visitorIds")
    public List<String> getVisitorIds() {
        return visitors;
    }

    @JsonbProperty("visitorIds")
    public void setVisitorIds(List<String> ids) {
        this.visitors = ids;
    }

    @XmlTransient
    public void addDoctor(Scanner sc) throws IllegalArgumentException {
        Doctor doctor = Doctor.generateDoctor(sc);
        doctors.add(doctor.getId());
        PersonnelStorage.doctorStorage.put(doctor.getId(), doctor);
        LogManager.logDoctorCreated(doctor);
        try {
            DataBaseManager.jsonSerialization(PersonnelStorage.doctorStorage, DataType.DOCTOR);
            DataBaseManager.jsonSerialization(DepartmentStorage.departments, DataType.DEPARTMENT);
        } catch (IOException e) {
            logger.error("Error while serializing doctor", e.getMessage(), e);
        }
    }

    @XmlTransient
    public void addVisitor(Scanner sc) throws IOException {
        Visitor visitor = Visitor.generateVisitor(sc);
        visitors.add(visitor.getId());
        PersonnelStorage.visitorStorage.put(visitor.getId(), visitor);
        LogManager.logVisitorAdmitted(visitor);
        DataBaseManager.jsonSerialization(visitor, DataType.VISITOR);
    }

    @XmlTransient
    public static Department generateDepartment(Scanner sc) throws IllegalArgumentException {
        System.out.println("Upišite ime novog Odjela:");
        String name = sc.nextLine();
        if (name.isEmpty()) throw new IllegalArgumentException("Ime odjela ne može biti prazno");
        logger.info("Stvoren je odjel sa imenom:", name);
        return new Department(name);
    }

    @XmlTransient
    public void addPatient(Scanner sc) throws IndexOutOfBoundsException, PersonnelException, IllegalArgumentException {
        if (doctors.isEmpty()) throw new PersonnelException("Ne postoji doktor za zbrinjavanje pacijenta");
        if (rooms.isEmpty()) throw new PersonnelException("Ne postoji soba za smještaj pacijenta");

        Patient patient = Patient.generatePatient(sc);

        System.out.println("Izaberite doktora:");
        Utility.printMenuSelection(getDoctors());
        int doctorSel = sc.nextInt(); sc.skip("\n");
        if (doctorSel <= 0 || doctorSel > doctors.size())
            throw new IndexOutOfBoundsException("Nepostojeći doktor");
        doctorSel--;

        System.out.println("Izaberite sobu:");
        Utility.printMenuSelection(getRooms());
        int roomSel = sc.nextInt(); sc.skip("\n");
        if (roomSel <= 0 || roomSel > rooms.size())
            throw new IndexOutOfBoundsException("Nepostojeća soba");
        roomSel--;

        patient.setRoom(PersonnelStorage.findRoom(rooms.get(roomSel)));
        patient.setDoctor(PersonnelStorage.findDoctor(doctors.get(doctorSel)));

        PersonnelStorage.findDoctor(doctors.get(doctorSel)).addPatient(patient);
        PersonnelStorage.findRoom(rooms.get(roomSel)).addPatient(patient);

        patients.add(patient.getId());
        PersonnelStorage.patientStorage.put(patient.getId(), patient);
        LogManager.logPatientAdmitted(patient);
        try {
            DataBaseManager.updateAllCollections();
        } catch(IOException e) {
            logger.error("Error while serializing patient", e.getMessage(), e);
        }

        logger.info("Kreiran je pacijent s imenom: {}", patient.getName());
    }

    public void addRoom() {
        Room newRoom = new Room();
        rooms.add(newRoom.getId());
        PersonnelStorage.roomStorage.put(newRoom.getId(), newRoom);
        LogManager.logRoomAdded(newRoom);
        try {
            DataBaseManager.jsonSerialization(PersonnelStorage.roomStorage, DataType.ROOM);
            DataBaseManager.jsonSerialization(DepartmentStorage.departments, DataType.DEPARTMENT);
        } catch (IOException e) {
            logger.error("Error while serializing room", e.getMessage(), e);
        }
    }

    @XmlTransient
    public List<Doctor> doctorSearchBySpecialty(Scanner sc) {
        System.out.println("Unesite traženu specijalizaciju");
        String specialty = sc.nextLine();
        return getDoctors().stream().filter(d -> specialty.equals(d.getSpecialty())).toList();
    }

    @XmlTransient
    public List<Patient> patientSearchByDiagnosis(Scanner sc) {
        System.out.println("Unesite traženu dijagnozu");
        String diagnosis = sc.nextLine();
        return getPatients().stream().filter(p -> diagnosis.equals(p.getDiagnosis())).toList();
    }

    public Map<String,List<Patient>> groupByDoctor() {
        return getPatients().stream().collect(Collectors.groupingBy(p -> p.getDoctor().getName()));
    }

    @JsonbTransient
    @Override
    public String getSelectionLine() {
        return name;
    }

    public Optional<Patient> findPatientByName(String name) {
        return Optional.of(getPatients().stream()
                .filter(p -> name.equalsIgnoreCase(p.getName()))
                .findAny()
                .orElseThrow(NoSuchElementException::new));
    }
}
