package entity;

import java.util.Scanner;

public final class Doctor extends Person implements Printable  {
    private String specialty;
    private Patient[] patients;
    private Integer patientCount;



    public Doctor(DoctorBuilder doctorBuilder) {
        super(doctorBuilder.name,doctorBuilder.OIB);
        this.specialty=doctorBuilder.specialty;
        this.patients=doctorBuilder.patients;
        this.patientCount=doctorBuilder.patientCount;

    }


    public String getSpecialty() {
        return specialty;
    }

    public void addPatient(Patient p){
        patients[patientCount++]=p;
    }


    @Override
    public void print(){
        System.out.println(super.getName() + ": " + specialty);
        for(int i=0;i<patientCount;i++){
            System.out.println((i+1) + ".) " + patients[i].getName() );
        }
    }

    static Doctor generateDoctor(Scanner sc){


        System.out.println("Unesite ime doktora:");
        String name=sc.nextLine();

        System.out.println("Unesite OIB doktora:");
        String OIB=sc.nextLine();

        System.out.println("Unesite specijalizaciju doktora:");
        String specialty=sc.nextLine();
        Doctor doctor=new Doctor.DoctorBuilder(name,OIB,specialty).build();

        Person.addPerson(doctor);

        return doctor;

    }


    @Override
    public void basicInformation() {
        System.out.println("Doktor:"+ super.getName() +" specijalizacija doktora: " + specialty);
    }

    public static class DoctorBuilder{
        private String name;
        private String OIB;
        private String specialty;

        private Patient[] patients=new Patient[1];
        private Integer patientCount=0;

        public DoctorBuilder(String name, String OIB, String specialty) {
            this.name=name;
            this.OIB=OIB;
            this.specialty=specialty;
        }
        public DoctorBuilder patients(Patient[] p){
            this.patients=p;
            return this;
        }
        public DoctorBuilder patientCount(Integer p){
            this.patientCount=p;
            return this;
        }
        public Doctor build(){
            return new Doctor(this);
        }
    }
}
