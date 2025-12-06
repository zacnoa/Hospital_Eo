package entity;


import java.util.*;

/**
 * Predstavlja sobu
 */
 public class Room implements PrintableMenuSelection {
    protected List<Patient> patients;
    private String id;

    /**
     *
     * @param id Id sobe
     */
    public Room(Integer id)
    {
        this.id =UUID.randomUUID().toString();
        patients =new ArrayList<>();

    }

    /**
     *
     * @return Id
     */
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;

    }


    /**
     *
     * @return Polje pacijenata
     */
    public List<Patient> getPatients() {
        return patients;
    }

    /**
     *  Dodaje pacijenta u sobu
     * @param patient pacijent
     */
    public void addPatient(Patient patient) {
        patients.add(patient);
    }

    @Override
    public String getSelectionLine()
    {
        return this.id;
    }
}
