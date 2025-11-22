package entity;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Predstavlja sobu
 */
non-sealed public class Room implements PrintableMenuSelection {
    protected Map<String, List<Patient>> patients;
    private Integer id;

    /**
     *
     * @param id Id sobe
     */
    public Room(Integer id)
    {
        this.id =id;
        patients =new HashMap<>();
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
    public Map<String,List<Patient>> getPatients() {
        return patients;
    }

    /**
     *  Dodaje pacijenta u sobu
     * @param patient pacijent
     */
    public void addPatient(Patient patient) {
        if(patients.containsKey(patient.getDiagnosis()))
        {
            patients.get(patient.getDiagnosis()).add(patient);
        }
        else
        {
            List<Patient> list=new ArrayList<>();
            list.add(patient);
            patients.put(patient.getDiagnosis(),list);
        }
    }

    @Override
    public String getSelectionLine()
    {
        return Integer.toString(this.id);
    }
}
