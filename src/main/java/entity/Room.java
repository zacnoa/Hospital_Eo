package entity;


/**
 * Predstavlja sobu
 */
public class Room {
    private Integer patientCount;
    private Patient[] patients;
    private Integer id;

    /**
     *
     * @param id Id sobe
     */
    public Room(Integer id)
    {
        this.id=id;
        this.patientCount =0;
        patients = new Patient[10];
    }

    /**
     *
     * @return Id
     */
    public Integer getId() {
        return id;
    }


    public Integer getPatientCount() {
        return patientCount;
    }

    /**
     *
     * @return Polje pacijenata
     */
    public Patient[] getPatients() {
        return patients;
    }

    /**
     *  Dodaje pacijenta u sobu
     * @param patient pacijent
     */
    public void addPatient(Patient patient) {
        this.patients[patientCount++] = patient;
    }


}
