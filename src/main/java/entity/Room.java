package entity;


import java.util.*;

/**
 * Predstavlja sobu
 */
non-sealed public class Room implements PrintableMenuSelection {
    protected List<Patient> patients;
    private Integer id;

    /**
     *
     * @param id Id sobe
     */
    public Room(Integer id)
    {
        this.id =id;
        patients =new ArrayList<>();

    }

    /**
     *
     * @return Id
     */
    public Integer getId() {
        return id;
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
        return Integer.toString(this.id);
    }
}
