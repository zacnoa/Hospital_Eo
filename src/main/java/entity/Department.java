package entity;

import jakarta.json.bind.annotation.JsonbTransient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;


public class Department implements PrintableMenuSelection, Serializable {

    private String name;


    private List<String> doctorIds = new ArrayList<>();
    private List<String> roomIds = new ArrayList<>();
    private List<String> patientIds = new ArrayList<>();
    private List<String> visitorIds = new ArrayList<>();

    private static final Logger logger = LoggerFactory.getLogger(Department.class);

    public Department(String name) {
        this.name = name;
    }

    public Department() {}


    @JsonbTransient
    public List<Doctor> getDoctors() {
        return PersonnelStorage.getDoctorsByIds(doctorIds);
    }

    @JsonbTransient
    public void setDoctors(List<Doctor> list) {
        this.doctorIds = list.stream().map(Doctor::getId).toList();
    }
    @JsonbTransient
    public List<Room> getRooms() {
        return PersonnelStorage.getRoomsByIds(roomIds);
    }

    @JsonbTransient
    public void setRooms(List<Room> list) {
        this.roomIds = list.stream().map(Room::getId).toList();
    }
    @JsonbTransient
    public List<Patient> getPatients() {
        return PersonnelStorage.getPatientsByIds(patientIds);
    }

    @JsonbTransient
    public void setPatients(List<Patient> list) {
        this.patientIds = list.stream().map(Patient::getId).toList();
    }


    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }




    public List<String> getDoctorIds() {
        return doctorIds;
    }


    public void setDoctorIds(List<String> ids) {
        this.doctorIds = ids;
    }


    public List<String> getRoomIds() {
        return roomIds;
    }


    public void setRoomIds(List<String> ids) {
        this.roomIds = ids;
    }



    public List<String> getPatientIds() {
        return patientIds;
    }


    public void setPatientIds(List<String> ids) {
        this.patientIds = ids;
    }


    public List<Visitor> getVisitors() {
        return PersonnelStorage.getVisitorsByIds(visitorIds);
    }


    public void setVisitors(List<Visitor> list) {
        this.visitorIds = list.stream().map(Visitor::getId).toList();
    }


    public List<String> getVisitorIds() {
        return visitorIds;
    }


    public void setVisitorIds(List<String> ids) {
        this.visitorIds = ids;
    }


    public void addDoctor(Scanner sc) throws IllegalArgumentException {
        Doctor doctor = Doctor.generateDoctor(sc);
        doctorIds.add(doctor.getId());
        PersonnelStorage.doctorStorage.put(doctor.getId(), doctor);

        try {
            DataBaseManager.jsonSerialization(PersonnelStorage.doctorStorage, DataType.DOCTOR);
            DataBaseManager.jsonSerialization(DepartmentStorage.departments, DataType.DEPARTMENT);
        } catch (IOException e) {
            logger.error("Error while serializing doctor", e.getMessage(), e);
        }
    }


    public void addVisitor(Scanner sc) throws IOException {
        Visitor visitor = Visitor.generateVisitor(sc);
        visitorIds.add(visitor.getId());
        PersonnelStorage.visitorStorage.put(visitor.getId(), visitor);

        DataBaseManager.jsonSerialization(visitor, DataType.VISITOR);
    }


    public static Department generateDepartment(Scanner sc) throws IllegalArgumentException {
        System.out.println("Upišite ime novog Odjela:");
        String name = sc.nextLine();
        if (name.isEmpty()) throw new IllegalArgumentException("Ime odjela ne može biti prazno");
        logger.info("Stvoren je odjel sa imenom:", name);
        return new Department(name);
    }


    public void addPatient(Scanner sc) throws IndexOutOfBoundsException, PersonnelException, IllegalArgumentException {
        if (doctorIds.isEmpty()) throw new PersonnelException("Ne postoji doktor za zbrinjavanje pacijenta");
        if (roomIds.isEmpty()) throw new PersonnelException("Ne postoji soba za smještaj pacijenta");

        Patient patient = Patient.generatePatient(sc);

        System.out.println("Izaberite doktora:");
        Utility.printMenuSelection(getDoctors());
        int doctorSel = sc.nextInt(); sc.skip("\n");
        if (doctorSel <= 0 || doctorSel > doctorIds.size())
            throw new IndexOutOfBoundsException("Nepostojeći doktor");
        doctorSel--;

        System.out.println("Izaberite sobu:");
        Utility.printMenuSelection(getRooms());
        int roomSel = sc.nextInt(); sc.skip("\n");
        if (roomSel <= 0 || roomSel > roomIds.size())
            throw new IndexOutOfBoundsException("Nepostojeća soba");
        roomSel--;

        patient.setRoom(PersonnelStorage.findRoom(roomIds.get(roomSel)));
        patient.setDoctor(PersonnelStorage.findDoctor(doctorIds.get(doctorSel)));


        PersonnelStorage.findDoctor(doctorIds.get(doctorSel)).addPatient(patient);
        PersonnelStorage.findRoom(roomIds.get(roomSel)).addPatient(patient);

        patientIds.add(patient.getId());


            PersonnelStorage.patientStorage.put(patient.getId(), patient);


        try {
            DataBaseManager.updateAllCollections();
        } catch(IOException e) {
            logger.error("Error while serializing patient", e.getMessage(), e);
        }

        logger.info("Kreiran je pacijent s imenom: {}", patient.getName());
    }

    public void addRoom() {
        Room newRoom = new Room();
        roomIds.add(newRoom.getId());
        PersonnelStorage.roomStorage.put(newRoom.getId(), newRoom);

        try {
            DataBaseManager.jsonSerialization(PersonnelStorage.roomStorage, DataType.ROOM);
            DataBaseManager.jsonSerialization(DepartmentStorage.departments, DataType.DEPARTMENT);
        } catch (IOException e) {
            logger.error("Error while serializing room", e.getMessage(), e);
        }
    }


    public List<Doctor> doctorSearchBySpecialty(Scanner sc) {
        System.out.println("Unesite traženu specijalizaciju");
        String specialty = sc.nextLine();
        return getDoctors().stream().filter(d -> specialty.equals(d.getSpecialty())).toList();
    }


    public List<Patient> patientSearchByDiagnosis(Scanner sc) {
        System.out.println("Unesite traženu dijagnozu");
        String diagnosis = sc.nextLine();
        return getPatients().stream().filter(p -> diagnosis.equals(p.getDiagnosis())).toList();
    }

    public Map<String,List<Patient>> groupByDoctor() {
        return getPatients().stream().collect(Collectors.groupingBy(p -> p.getDoctor().getName()));
    }

    @JsonbTransient
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
