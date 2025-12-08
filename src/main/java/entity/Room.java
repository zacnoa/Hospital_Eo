package entity;

import jakarta.json.bind.annotation.JsonbProperty;
import jakarta.json.bind.annotation.JsonbTransient;
import jakarta.xml.bind.annotation.*;
import java.io.Serializable;
import java.util.*;

/**
 * Predstavlja sobu
 */
@XmlRootElement(name = "room")  // DODAJ OVO
@XmlAccessorType(XmlAccessType.PROPERTY)  // DODAJ OVO
public class Room implements PrintableMenuSelection, Serializable {

    private List<String> patients;
    private String id;

    /**
     * Konstruktor
     */
    public Room() {
        this.id = UUID.randomUUID().toString();
        this.patients = new ArrayList<>();
    }

    /**
     * @return Id
     */
    @XmlElement(name = "id")  // DODAJ OVO
    @JsonbProperty("id")
    public String getId() {
        return id;
    }

    @JsonbProperty("id")
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return Polje pacijenata (objekti)
     */
    @XmlTransient
    @JsonbTransient
    public List<Patient> getPatients() {
        return PersonnelStorage.getPatientsByIds(patients);
    }

    @XmlTransient
    @JsonbTransient
    public void setPatients(List<Patient> patients) {
        this.patients = patients.stream()
                .map(Patient::getId)
                .toList();
    }

    /**
     * @return Lista ID-ova pacijenata (za serijalizaciju)
     */
    @XmlElementWrapper(name = "patients")
    @XmlElement(name = "patientId")
    @JsonbProperty("patients")
    public List<String> getPatientsId() {
        return patients;
    }

    @JsonbProperty("patients")
    public void setPatientsId(List<String> patients) {
        this.patients = patients;
    }

    /**
     * Dodaje pacijenta u sobu
     * @param patient pacijent
     */
    public void addPatient(Patient patient) {
        patients.add(patient.getId());
    }

    @JsonbTransient
    @Override
    public String getSelectionLine() {
        return this.id;
    }
}