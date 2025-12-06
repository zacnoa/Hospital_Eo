package adapters;

import entity.Doctor;
import jakarta.json.bind.adapter.JsonbAdapter;

public class DoctorAdapter implements JsonbAdapter<Doctor, String> {

    @Override
    public String adaptToJson(Doctor doctor)
    {
        return doctor.getId();
    }
    @Override
    public Doctor adaptFromJson(String id)
    {
        return null;
    }


}
