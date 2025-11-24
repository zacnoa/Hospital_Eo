package entity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.Optional;
import java.util.Scanner;


/**
 * Predstavlja pacijenta
 */

public  class Patient extends Person {
    private Optional<Doctor> doctor;
    private String diagnosis;
    private Integer id;
    public PatientStatus status;
    private Optional<Room> room;

    private final static Logger logger= LoggerFactory.getLogger(Patient.class);

    /**
     *
     * @return doktora
     */
    public Optional<Doctor> getDoctor() {
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


    public void setRoom(Room room) {
        this.room =Optional.of(room);
    }
    public Optional<Room> getRoom()
    {
        return room;
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
    protected Patient(PatientBuilder patientBuilder) {
        super(patientBuilder.name,patientBuilder.OIB);
        this.diagnosis = patientBuilder.diagnosis;
        this.id=patientBuilder.id;
        this.status=patientBuilder.status;
    }



    static Map<String,String> getBasicInfo(Scanner sc)
    {
        Map<String,String> map=Person.getBasicInfo(sc);

        map.put("dijagnoza",generateDiagnosis(sc));

        return map;

    }

    public static String generateDiagnosis(Scanner sc)
    {
        System.out.println("Unesite dijagnozu  pacijenta");
        String dijagnoza=sc.nextLine();
        return dijagnoza;
    }


    /**
     *
     * @param sc Inicijalizirani Scanner objekt
     * @return inicijalizirani Patient objekt
     */
    public static Patient generatePatient(Scanner sc){

        System.out.println("""
                Da li je pacijent maloljetan(Izaberite redni broj)
                1.)Da
                2.)Ne
                """
        );
        if("1".equals(sc.nextLine()))
        {
            return  UnderagePatient.generateUnderagePatient(sc);
        }
        Map<String,String> map=getBasicInfo(sc);
        Patient patient=new Patient.PatientBuilder(map.get("ime"), map.get("oib"),map.get("dijagnoza"), PatientStatus.HOSPITALIZED).build();

        return patient;
    }

    /**
     *
     * @param doctor doktor
     */
    public void setDoctor(Doctor doctor){
        this.doctor= Optional.of(doctor);
    }

    /**
     * PatternBuilder za klasu Patient
     */
    public static class PatientBuilder<T extends PatientBuilder<T>>{
        String name;
        String OIB;
        String diagnosis;
        Integer id;
        PatientStatus status;

        Optional<Doctor> doctor;
        Optional<Room> room;

        /**
         *
         * @param name Ime
         * @param OIB OIB
         * @param diagnosis Dijagnoza
         * @param status status
         */
        public PatientBuilder (String name,String OIB,String diagnosis,PatientStatus status){
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
        public T doctor(Optional<Doctor> doctor){
            this.doctor=doctor;
            return (T) this;
        }
        public T room(Optional<Room> room)
        {
            this.room=room;
            return (T) this;
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
