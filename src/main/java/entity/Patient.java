package entity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Scanner;

/**
 * Predstavlja pacijenta
 */

public final class Patient extends Person {
    private Doctor doctor;
    private String diagnosis;
    private Integer id;
    public PatientStatus status;

    private final static Logger logger= LoggerFactory.getLogger(Patient.class);

    /**
     *
     * @return doktora
     */
    public Doctor getDoctor() {
        return doctor;
    }

    /**
     *
     * @return  diagnozu
     */
    public String getDiagnosis() {
        return diagnosis;
    }

    /**
     *
     * @return Id
     */
    public Integer getId() {
        return id;
    }

    @Override
    public void basicInformation()
    {
        System.out.println("Pacijent: "+ super.getName() + " OIB: " + super.getOIB());
    }


    /**
     *
     *
     * @param patientBuilder patientBuilder
     */
    private Patient(PatientBuilder patientBuilder) {
        super(patientBuilder.name,patientBuilder.OIB);
        this.diagnosis = patientBuilder.diagnosis;
        this.id=patientBuilder.id;
        this.status=patientBuilder.status;
    }

    /**
     *
     * @param sc Inicijalizirani Scanner objekt
     * @return inicijalizirani Patient objekt
     */
    public static Patient generatePatient(Scanner sc){

        System.out.println("Unesite ime pacijenta");
        String ime=sc.nextLine();

        System.out.println("Unesite OIB pacijenta;");
        String oib=sc.nextLine();

        System.out.println("Unesite dijagnozu  pacijenta");
        String dijagnoza=sc.nextLine();

        Patient patient=new Patient.PatientBuilder(ime,oib,dijagnoza,PatientStatus.HOSPITALIZED).build();

        return patient;
    }

    /**
     *
     * @param doctor doktor
     */
    public void addDoctor(Doctor doctor){
        this.doctor=doctor;
    }

    /**
     * PatternBuilder za klasu Patient
     */
    public static class PatientBuilder{
        String name;
        String OIB;
        String diagnosis;
        Integer id;
        PatientStatus status;

        Doctor doctor=null;

        /**
         *
         * @param name Ime
         * @param OIB OIB
         * @param diagnosis Dijagnoza
         * @param status status
         */
        public PatientBuilder(String name,String OIB,String diagnosis,PatientStatus status){
            this.name=name;
            this.OIB=OIB;
            this.diagnosis=diagnosis;
            this.status=status;

        }

        /**
         *
         * @param doctor doktor
         * @return referncu na ovaj objekt
         */
        public PatientBuilder doctor(Doctor doctor){
            this.doctor=doctor;
            return this;
        }

        /**
         *
         * @return inicijalizirani objekt Patient
         */
        public Patient build()
        {
            return new Patient(this);
        }
    }

}
