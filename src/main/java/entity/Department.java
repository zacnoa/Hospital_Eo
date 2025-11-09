package entity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.util.*;


/**
 * Predstavlja odjel u bolnici
 *
 * Klasa omogucava baratnje odjelima te pripadajucim doktorima,pacijentima, te uputnicama
 */

public class Department {

    private String name;
    private List<Doctor> doctors;
    private List<Room> rooms;
    private List<Appoitnment> appoitnments;

    private static final Logger logger = LoggerFactory.getLogger(Department.class);


    public Department(String name) {
        this.name = name;
        this.doctors = new ArrayList<>();
        this.rooms = new ArrayList<>();
        this.addRoom();
        appoitnments = new ArrayList<>();
    }


    /**
     * Vraca polje doktora
     *
     * @return Volje doktora odjela
     */
    public List<Doctor> getDoctor() {
        return doctors;
    }

    /**
     * Vraca ime odjela
     *
     * @return Ime odjela
     */
    public String getName() {
        return name;
    }

    /**
     * Inicijalizira doktora te ga dodaje polju doctors
     *
     * @param sc Inicijalizirani Scanner object
     * @throws IllegalArgumentException Biti će bacena ako se unese prazno ime
     */
    public void addDoctor(Scanner sc) throws IllegalArgumentException {
        doctors.add(Doctor.generateDoctor(sc));
    }

    /**
     * Stvara novi objekt tipa Department
     *
     * @param sc Inicijalizirani Scanner object
     * @return Inicijalzirani odjel objekt
     * @throws IllegalArgumentException Biti će bacena ako se unese prazno ime
     */
    public static Department generateDepartment(Scanner sc) throws IllegalArgumentException {
        System.out.println("Upišite ime novog Odjela:");
        String name = sc.nextLine();

        if (name.isEmpty()) {
            throw new IllegalArgumentException("Ime odjela nemože bit prazno");
        }
        logger.info("Stvoren je odjel sa imenom: {}", name);
        return new Department(name);
    }

    /**
     * Stvara novi pacijent objekt te ga dodaje polju patients
     *
     * @param sc Inicijalizirani Scanner object
     * @throws IndexOutOfBoundsException Biti ce baceno ako se izabere krivi index za doktora ili sobu
     * @throws PersonnelException        Biti ce bacena ako se pokusa stvoriti pacijent dok ne postoji doktora ili soba
     */
    public void addPatient(Scanner sc) throws IndexOutOfBoundsException, PersonnelException {
        if (doctors.isEmpty()) {
            throw new PersonnelException("Ne postoji doktor za zbrijnjavanje pacijenta");
        } else if (rooms.isEmpty()) {
            throw new PersonnelException("Ne postoji soba za smještaj pacijenta");
        }
        Patient patient = Patient.generatePatient(sc);
        System.out.println("Izaberite redni broj pored doktora koji je odgovoran za tog pacijenta:");
        for (Integer i = 0; i < doctors.size(); i++) {
            System.out.println(i + 1 + ".) " + doctors.get(i).getName());
        }
        Integer doctorSelection = sc.nextInt();
        sc.skip("\n");

        if (doctorSelection > doctors.size() || doctorSelection < 0) {
            throw new IndexOutOfBoundsException("Unesen je redni broj doktora koji ne postoji");

        }

        System.out.println("Izaberite redni broj pored sobe gdje ce se nalaziti  pacijent:");
        for (Integer i = 0; i < rooms.size(); i++) {
            System.out.println(i + 1 + ".)" + rooms.get(i).getId());
        }

        Integer roomSelection = sc.nextInt();
        sc.skip("\n");

        if (roomSelection > rooms.size() || roomSelection < 0) {
            throw new IndexOutOfBoundsException("Unesen je redni broj sobe koja ne postoji");
        }

        Person.addPerson(patient);
        logger.info("Kreiran je pacijent s imenom: {}", patient.getName());
        patient.addDoctor(doctors.get(doctorSelection - 1));
        doctors.get(doctorSelection - 1).addPatient(patient);
        rooms.get(roomSelection - 1).addPatient(patient);

    }

    /**
     * Dodaje sobu odjelu
     */
    public void addRoom() {
        rooms.add(new Room(rooms.size() + 1));
    }




    /**
     * Printa sve uputnice za odjel
     */
    public void printAppointments() {
        appoitnments.forEach(appointment -> appointment.appoitnmentTime());
    }


    /**
     * Trazi doktora po specijalizaciji
     *
     * @param sc Inicijalizirani Scanner objekt
     * @return Pronadeni doktor ako postoji ili null ako ne postoji
     */
    public List<Doctor> doctorSearchBySpecialty(Scanner sc) {
        System.out.println("Unesite trazenu specijalizaciju");
        String diagnosis = sc.nextLine();
        return doctors.stream().filter(doctor->diagnosis.equals(doctor.getSpecialty())).toList();
    }


    /**
     * Trazi pacijenta po njegovoj diagnozi
     *
     * @param sc Inicijalizirani Scanner objekt
     * @return Pronadenog pacijenta ako postoji ili null ako ne postoji
     */
    public List<Patient> patientSearchByDiagnosis(Scanner sc) {
        System.out.println("Unesite traženu dijagnozu");
        String diagnosis = sc.nextLine();

         return rooms.stream().filter(room->room.patients.containsKey(diagnosis))
                        .flatMap(room-> room.patients.get(diagnosis).stream()).toList();
    }
}


