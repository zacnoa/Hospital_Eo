package entity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Predstavja doktora
 * Klasa omogucava upravaljanje doktora i njegovih pacijenta
 *
 */

public final class Doctor extends Person implements PrintableArray {
    private String specialty;
    private List<Patient> patients;


    private final static Logger logger= LoggerFactory.getLogger(Doctor.class);


    private Doctor(DoctorBuilder doctorBuilder) {
        super(doctorBuilder.name,doctorBuilder.OIB);
        this.specialty=doctorBuilder.specialty;
        this.patients=doctorBuilder.patients;

    }

    /**
     *
     * @return Vraca specijalizaciju doktora
     */
    public String getSpecialty() {
        return specialty;
    }

    /**
     *
     * @param patient Dodaje objekt tipa Patient u polje patients
     */
    public void addPatient(Patient patient){
        patients.add(patient);
    }



    @Override
    public void print(){
        System.out.println(super.getName() + ": " + specialty);
        for(int i=0;i<patients.size();i++){
            System.out.println((i+1) + ".) " + patients.get(i).getName() );
        }
    }

    /**
     *
     * @param sc Inicijalizirani Scanner objekt
     * @return Vraca inicijaliziranog doktora
     * @throws IllegalArgumentException biti ce bacena ako se unese prazno ime
     */
    static Doctor generateDoctor(Scanner sc) throws IllegalArgumentException{

            System.out.println("Unesite ime doktora:");
            String name = sc.nextLine();
            if(name.isEmpty())
            {
                throw new IllegalArgumentException("Ime doktora ne smije biti prazno");
            }

            System.out.println("Unesite OIB doktora:");
            String OIB = sc.nextLine();

            System.out.println("Unesite specijalizaciju doktora:");
            String specialty = sc.nextLine();

            Doctor doctor = new Doctor.DoctorBuilder(name, OIB, specialty).build();


        Person.addPerson(doctor);
        logger.info("Stvoren je doktor s imenom: {}",name);
        return doctor;

    }


    @Override
    public void basicInformation() {
        System.out.println("Doktor:"+ super.getName() +" specijalizacija doktora: " + specialty);
    }

    /**
     * BuilderPattern za klasu doktor
     */
    public static class DoctorBuilder{
        private String name;
        private String OIB;
        private String specialty;

        private  List<Patient> patients=new ArrayList<>();

        /**
         *
         * @param name Ime doktora
         * @param OIB OIB doktora
         * @param specialty Specijalizacija doktora
         */
        public DoctorBuilder(String name, String OIB, String specialty) {
            this.name=name;
            this.OIB=OIB;
            this.specialty=specialty;
        }

        /**
         *
         * @param patients polje pacijenata
         * @return Referncu na trenutni objekt
         */
        public DoctorBuilder patients(List<Patient> patients){
            this.patients=patients;
            return this;
        }

        /**
         *
         * @return Inicijalizirani objekt Doctor
         */
        public Doctor build(){
            return new Doctor(this);
        }
    }
}
