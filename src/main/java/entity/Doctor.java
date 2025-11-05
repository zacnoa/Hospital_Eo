package entity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Scanner;

/**
 * Predstvja doktora
 * Klasa omogucava upravaljanje doktora i njegovih pacijenta
 * @u
 */

public final class Doctor extends Person implements PrintableArray {
    private String specialty;
    private Patient[] patients;
    private Integer patientCount;

    private final static Logger logger= LoggerFactory.getLogger(Doctor.class);


    private Doctor(DoctorBuilder doctorBuilder) {
        super(doctorBuilder.name,doctorBuilder.OIB);
        this.specialty=doctorBuilder.specialty;
        this.patients=doctorBuilder.patients;
        this.patientCount=doctorBuilder.patientCount;

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
        patients[patientCount++]= patient;
    }



    @Override
    public void print(){
        System.out.println(super.getName() + ": " + specialty);
        for(int i=0;i<patientCount;i++){
            System.out.println((i+1) + ".) " + patients[i].getName() );
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

        private Patient[] patients=new Patient[1];
        private Integer patientCount=0;

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
        public DoctorBuilder patients(Patient[] patients){
            this.patients=patients;
            return this;
        }


        public DoctorBuilder patientCount(Integer patientCount){
            this.patientCount= patientCount;
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
