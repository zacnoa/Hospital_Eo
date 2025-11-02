package entity;

import java.time.LocalDate;
import java.util.Scanner;


public class Department {

    private String name;
    private Doctor[] doctors;
    private Room[] rooms;
    private Integer roomCount;
    private Integer doctorCount;
    Appoitnment[] appoitnments;
    private Integer appoitnmentCount;






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

    public int getDoctorCount() {
        return doctorCount;
    }

    public int getRoomCount() {
        return roomCount;
    }

    public Room[] getRooms() {
        return rooms;
    }

    public Doctor[] getDoctor() {
        return doctors;
    }

    public String getName() {
        return name;
    }

    public  void addDoctor(Scanner sc)
    {
        doctors[doctorCount++]= Doctor.generateDoctor(sc);
    }

    public static Department generateDepartment(Scanner sc) throws IllegalArgumentException
    {
        System.out.println("Upišite ime novog Odjela:");
        String name=sc.nextLine();

        if(name.isEmpty())
        {
            throw new IllegalArgumentException("Ime odjela nemože bit prazno");
        }

        return new Department(name);
    }

    public void addPatient(Scanner sc) throws IndexOutOfBoundsException , ArrayException
    {
        if(doctorCount==0)
        {
            throw  new ArrayException("Ne postoji doktor za zbrijnjavanje pacijenta");
        }
        else if(roomCount==0)
        {
            throw new ArrayException("Ne postoji soba za smještaj pacijenta");
        }
        Patient patient =Patient.generatePatient(sc);
        System.out.println("Izaberite redni broj pored doktora koji je odgovoran za tog pacijenta:");
        for( Integer i=0;i<doctorCount;i++)
        {
            System.out.println(i+1 + ".) "+ doctors[i].getName());
        }
        Integer doctorSelection=sc.nextInt();

        if(doctorSelection-1>doctorCount || doctorSelection<0)
        {
            throw new IndexOutOfBoundsException("Unesen je redni broj doktora koji ne postoji");
        }
        sc.skip("\n");

        System.out.println("Izaberite redni broj pored sobe gdje ce se nalaziti  pacijent:");
        for(Integer i=0;i<roomCount;i++)
        {
            System.out.println(i+1 + ".)"+ rooms[i].getId());
        }

        Integer roomSelection=sc.nextInt();
        if(roomSelection-1>roomCount || roomSelection<0)
        {
            throw new IndexOutOfBoundsException("Unesen je redni broj sobe koja ne postoji");
        }
        sc.skip("\n");

        patient.addDoctor(doctors[doctorSelection]);
        doctors[doctorSelection-1].addPatient(patient);
        rooms[roomSelection-1].addPatient(patient);

    }




    public void addRoom()
    {
        rooms[roomCount]=new Room(++roomCount);
    }





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





    public void printAppointments()
    {
        for(Integer i=0;i<appoitnmentCount;i++)
        {
            appoitnments[i].appoitnmentTime();
        }
    }




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
