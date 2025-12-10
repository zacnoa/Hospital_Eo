package entity;

import RunTimeLog.RuntimeLogger;
import jakarta.json.bind.annotation.JsonbTransient;

import java.io.IOException;
import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;


public class Department implements PrintableMenuSelection, Serializable {

    private String name;


    private transient List<String> doctorIds = new ArrayList<>();
    private transient List<String> roomIds = new ArrayList<>();
    private transient List<String> patientIds = new ArrayList<>();
    private transient List<String> visitorIds = new ArrayList<>();



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
            RuntimeLogger.logger.error("Error while serializing doctor", e.getMessage(), e);
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
        RuntimeLogger.logger.info("Department created: {}", name);
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
            RuntimeLogger.logger.error("Error while serializing patient", e.getMessage(), e);
        }

        RuntimeLogger.logger.info("Kreiran je pacijent s imenom: {}", patient.getName());
    }

    public void addRoom() throws IOException {
        Room newRoom = new Room();
        roomIds.add(newRoom.getId());
        PersonnelStorage.roomStorage.put(newRoom.getId(), newRoom);


            DataBaseManager.jsonSerialization(PersonnelStorage.roomStorage, DataType.ROOM);
            DataBaseManager.jsonSerialization(DepartmentStorage.departments, DataType.DEPARTMENT);

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




    private void writeObject(java.io.ObjectOutputStream out) throws IOException {
        // 1. Zapiši obična polja
        out.defaultWriteObject(); // zapisuje 'name'

        // 2. Zapiši prave objekte umjesto ID-jeva
        out.writeInt(doctorIds.size());
        for(String id : doctorIds) {
            Doctor doctor = PersonnelStorage.findDoctor(id);
            out.writeObject(doctor);
        }

        out.writeInt(patientIds.size());
        for(String id : patientIds) {
            Patient patient = PersonnelStorage.findPatient(id);
            out.writeObject(patient);
        }

        out.writeInt(roomIds.size());
        for(String id : roomIds) {
            Room room = PersonnelStorage.findRoom(id);
            out.writeObject(room);
        }

        out.writeInt(visitorIds.size());
        for(String id : visitorIds) {
            Visitor visitor = PersonnelStorage.findVisitor(id);
            out.writeObject(visitor);
        }
    }




    private void readObject(java.io.ObjectInputStream in) throws IOException, ClassNotFoundException {
        // 1. Pročitaj obična polja
        in.defaultReadObject();

        // 2. Učitaj stvarne objekte i kreiraj ID-jeve i storage
        int doctorSize = in.readInt();
        doctorIds = new ArrayList<>();
        for(int i=0; i<doctorSize; i++) {
            Doctor doctor = (Doctor) in.readObject();
            doctorIds.add(doctor.getId());
            PersonnelStorage.doctorStorage.put(doctor.getId(), doctor);
        }

        int patientSize = in.readInt();
        patientIds = new ArrayList<>();
        for(int i=0; i<patientSize; i++) {
            Patient patient = (Patient) in.readObject();
            patientIds.add(patient.getId());
            PersonnelStorage.patientStorage.put(patient.getId(), patient);
        }

        int roomSize = in.readInt();
        roomIds = new ArrayList<>();
        for(int i=0; i<roomSize; i++) {
            Room room = (Room) in.readObject();
            roomIds.add(room.getId());
            PersonnelStorage.roomStorage.put(room.getId(), room);
        }

        int visitorSize = in.readInt();
        visitorIds = new ArrayList<>();
        for(int i=0; i<visitorSize; i++) {
            Visitor visitor = (Visitor) in.readObject();
            visitorIds.add(visitor.getId());
            PersonnelStorage.visitorStorage.put(visitor.getId(), visitor);
        }
    }



}
