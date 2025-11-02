package entity;

import java.util.Scanner;

public final class Patient extends Person {
    private Doctor doctor;
    private String diagnosis;
    private Integer id;


    public Doctor getDoctor() {
        return doctor;
    }


    public String getDiagnosis() {
        return diagnosis;
    }

    public Integer getId() {
        return id;
    }

    @Override
    public void basicInformation()
    {
        System.out.println("Pacijent: "+ super.getName() + " OIB: " + super.getOIB());
    }




    public Patient(PatientBuilder patientBuilder) {
        super(patientBuilder.name,patientBuilder.OIB);
        this.diagnosis = patientBuilder.diagnosis;
        this.id=patientBuilder.id;
    }

    public static Patient generatePatient(Scanner sc){

        System.out.println("Unesite ime pacijenta");
        String ime=sc.nextLine();

        System.out.println("Unesite OIB pacijenta;");
        String oib=sc.nextLine();

        System.out.println("Unesite dijagnozu  pacijenta");
        String dijagnoza=sc.nextLine();

        Patient patient=new Patient.PatientBuilder(ime,oib,dijagnoza).build();
        Person.addPerson(patient);

        return patient;
    }

    public void addDoctor(Doctor doctor){
        this.doctor=doctor;
    }

    public static class PatientBuilder{
        String name;
        String OIB;
        String diagnosis;
        Integer id;

        Doctor doctor=null;

        public PatientBuilder(String name,String OIB,String diagnosis){
            this.name=name;
            this.OIB=OIB;
            this.diagnosis=diagnosis;

        }


        public PatientBuilder doctor(Doctor doctor){
            this.doctor=doctor;
            return this;
        }
        public Patient build()
        {
            return new Patient(this);
        }
    }

}
