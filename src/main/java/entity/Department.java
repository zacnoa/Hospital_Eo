package entity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.util.Scanner;


/**
 * Predstavlja odjel u bolnici
 *
 * Klasa omogucava baratnje odjelima te pripadajucim doktorima,pacijentima, te uputnicama
 */

public class Department {

    private String name;
    private Doctor[] doctors;
    private Room[] rooms;
    private Integer roomCount;
    private Integer doctorCount;
    Appoitnment[] appoitnments;
    private Integer appoitnmentCount;

    private static final Logger logger= LoggerFactory.getLogger(Department.class);



    public Department(String name) {
        this.name = name;
        this.doctors = new Doctor[10];
        this.rooms =  new Room[10];
        this.roomCount=0;
        this.addRoom();
        this.doctorCount=0;
        appoitnments = new Appoitnment[10];
        this.appoitnmentCount=0;
    }


    /**
     * Vraca polje doktora
     * @return Volje doktora odjela
     */
    public Doctor[] getDoctor() {
        return doctors;
    }

    /**
     *  Vraca ime odjela
     * @return Ime odjela
     */
    public String getName() {
        return name;
    }

    /**
     *  Inicijalizira doktora te ga dodaje polju doctors
     * @param sc Inicijalizirani Scanner object
     * @throws IllegalArgumentException Biti će bacena ako se unese prazno ime
     */
    public  void addDoctor(Scanner sc) throws IllegalArgumentException
    {
        doctors[doctorCount]= Doctor.generateDoctor(sc);
        doctorCount++;
    }

    /**
     * Stvara novi objekt tipa Department
     * @param sc Inicijalizirani Scanner object
     * @return Inicijalzirani odjel objekt
     * @throws IllegalArgumentException Biti će bacena ako se unese prazno ime
     */
    public static Department generateDepartment(Scanner sc) throws IllegalArgumentException
    {
        System.out.println("Upišite ime novog Odjela:");
        String name=sc.nextLine();

        if(name.isEmpty())
        {
            throw new IllegalArgumentException("Ime odjela nemože bit prazno");
        }
        logger.info("Stvoren je odjel sa imenom: {}", name);
        return new Department(name);
    }

    /**
     * Stvara novi pacijent objekt te ga dodaje polju patients
     * @param sc Inicijalizirani Scanner object
     * @throws IndexOutOfBoundsException Biti ce baceno ako se izabere krivi index za doktora ili sobu
     * @throws PersonnelException Biti ce bacena ako se pokusa stvoriti pacijent dok ne postoji doktora ili soba
     */
    public void addPatient(Scanner sc) throws IndexOutOfBoundsException , PersonnelException
    {
        if(doctorCount==0)
        {
            throw  new PersonnelException("Ne postoji doktor za zbrijnjavanje pacijenta");
        }
        else if(roomCount==0)
        {
            throw new PersonnelException("Ne postoji soba za smještaj pacijenta");
        }
        Patient patient =Patient.generatePatient(sc);
        System.out.println("Izaberite redni broj pored doktora koji je odgovoran za tog pacijenta:");
        for( Integer i=0;i<doctorCount;i++)
        {
            System.out.println(i+1 + ".) "+ doctors[i].getName());
        }
        Integer doctorSelection=sc.nextInt();
        sc.skip("\n");

        if(doctorSelection>doctorCount || doctorSelection<0)
        {
            throw new IndexOutOfBoundsException("Unesen je redni broj doktora koji ne postoji");

        }

        System.out.println("Izaberite redni broj pored sobe gdje ce se nalaziti  pacijent:");
        for(Integer i=0;i<roomCount;i++)
        {
            System.out.println(i+1 + ".)"+ rooms[i].getId());
        }

        Integer roomSelection=sc.nextInt();
        sc.skip("\n");
        if(roomSelection>roomCount || roomSelection<0)
        {
            throw new IndexOutOfBoundsException("Unesen je redni broj sobe koja ne postoji");
        }

        Person.addPerson(patient);
        logger.info("Kreiran je pacijent s imenom: {}", patient.getName());
        patient.addDoctor(doctors[doctorSelection]);
        doctors[doctorSelection-1].addPatient(patient);
        rooms[roomSelection-1].addPatient(patient);

    }

    /**
     * Dodaje sobu odjelu
     */
    public void addRoom()
    {
        rooms[roomCount]=new Room(++roomCount);
    }


    /**
     * Stvara novu uputnicu
     * @param sc Inicijalizirani Scanner objekt
     */
    public void addAppointment(Scanner sc)
    {
        System.out.println("Izaberite redni broj pored pacijenta za kojeg radite uputnicu:");
        for(int i=0;i<Person.personCount;i++)
        {
            if(Person.allPersons[i] instanceof Patient patient) {
                System.out.println(i + 1 + ".) " + patient.getName());
            }
        }

        Integer appointmentSelection=sc.nextInt();
        sc.skip("\n");
        appointmentSelection--;
        Appoitnment appoitnment= new Appoitnment((Patient)Person.allPersons[appointmentSelection], LocalDate.now().plusWeeks(2));
        appoitnments[appoitnmentCount++]=appoitnment;
    }


    /**
     * Printa sve uputnice za odjel
     */
    public void printAppointments()
    {
        for(Integer i=0;i<appoitnmentCount;i++)
        {
            appoitnments[i].appoitnmentTime();
        }
    }


    /**
     *  Trazi doktora po specijalizaciji
     * @param sc Inicijalizirani Scanner objekt
     * @return Pronadeni doktor ako postoji ili null ako ne postoji
     */
    public Doctor doctorSearchBySpecialty(Scanner sc){
        System.out.println("Unesite traženu specijalizaciju dokotora");

        String specialty=sc.nextLine();

        for(Integer i=0;i<doctorCount;i++){
            if(doctors[i].getSpecialty().equals(specialty)){
                System.out.println("Nađen je doktor sa specijalizacijom:" + specialty);
                return doctors[i];
            }
        }
        System.out.println("Nije nađen doktor sa specijalizacijom:" + specialty);
        return null;
    }


    /**
     * Trazi pacijenta po njegovoj diagnozi
     * @param sc Inicijalizirani Scanner objekt
     * @return Pronadenog pacijenta ako postoji ili null ako ne postoji
     */
    public Patient patientSearchByDiagnosis(Scanner sc){
        System.out.println("Unesite traženu dijagnozu");
        String diagnosis=sc.nextLine();

        for(Integer i=0;i<roomCount;i++)
        {
            for(Integer j=0;j<rooms[i].getPatientCount();j++)
            {
                if(rooms[i].getPatients()[j].getDiagnosis().equals(diagnosis)){
                    System.out.println("Nađen je pacijent s dijagnozom" + diagnosis);
                    return rooms[i].getPatients()[j];
                }
            }

        }
        System.out.println("Nije nađen doktor sa specijalizacijom:" + diagnosis);
        return null;
    }

}
