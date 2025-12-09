
package adapters;

import entity.PatientStatus;
import entity.Visitor;
import jakarta.json.*;
import entity.Patient;
import entity.UnderagePatient;
import jakarta.json.bind.adapter.JsonbAdapter;

public class PatientAdapter implements JsonbAdapter<Patient, JsonObject> {

    @Override
    public JsonObject adaptToJson(Patient p) {
        JsonObjectBuilder builder = Json.createObjectBuilder();

        // Polje za polimorfizam
        builder.add("type", p.getClass().getSimpleName());

        // Polja zajednička za Patient
        builder.add("name", p.getName() != null ? p.getName() : "")
                .add("OIB", p.getOIB() != null ? p.getOIB() : "")
                .add("diagnosis", p.getDiagnosis() != null ? p.getDiagnosis() : "")
                .add("status", p.getStatus() != null ? p.getStatus().name() : "")
                .add("doctorId", p.getDoctorId() != null ? p.getDoctorId() : "")
                .add("roomId", p.getRoomId() != null ? p.getRoomId() : "");

        // Ako je podklasa UnderagePatient, dodaj legalGuardian
        if (p instanceof UnderagePatient u) {
            if (u.getLegalGuardian() != null) {
                builder.add("legalGuardian", Json.createObjectBuilder()
                        .add("name", u.getLegalGuardian().getName() != null ? u.getLegalGuardian().getName() : "")
                        .add("OIB", u.getLegalGuardian().getOIB() != null ? u.getLegalGuardian().getOIB() : "")
                );
            }
        }

        return builder.build();
    }

    @Override
    public Patient adaptFromJson(JsonObject obj) {
        String type = obj.getString("type", "Patient"); // default Patient

        String name = obj.getString("name", "");
        String OIB = obj.getString("OIB", "");
        String diagnosis = obj.getString("diagnosis", "");
        String statusStr = obj.getString("status", "HOSPITALIZED");
        String doctorId = obj.getString("doctorId", null);
        String roomId = obj.getString("roomId", null);

        PatientStatus status = PatientStatus.valueOf(statusStr);

        switch (type) {
            case "UnderagePatient":
                // Deserijaliziraj legalGuardian ako postoji
                Visitor guardian = null;
                if (obj.containsKey("legalGuardian")) {
                    JsonObject guardianObj = obj.getJsonObject("legalGuardian");
                    String guardianName = guardianObj.getString("name", "");
                    String guardianOIB = guardianObj.getString("OIB", "");
                    guardian = new Visitor(guardianName, guardianOIB);
                }

                return new UnderagePatient.UnderagePatientBuilder(name, OIB, diagnosis, status)
                        .legalGuardian(guardian)
                        .build();

            case "Patient":
            default:
                Patient p = new Patient.PatientBuilder(name, OIB, diagnosis, status)
                        .build();
                p.setDoctorId(doctorId);
                p.setRoomId(roomId);
                return p;
        }
    }
}
