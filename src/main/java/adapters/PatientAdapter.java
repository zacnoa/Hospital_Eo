package adapters;

import entity.Doctor;
import entity.Patient;
import entity.PersonnelStorage;
import jakarta.json.bind.adapter.JsonbAdapter;

public class PatientAdapter implements JsonbAdapter<Patient, String> {

    @Override
    public String  adaptToJson(Patient patient)
    {
        return patient.getId();
    }

    @Override
    public Patient adaptFromJson(String id) {
        return PersonnelStorage.findPatient(id);
    }

}
