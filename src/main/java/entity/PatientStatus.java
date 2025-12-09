package entity;

public enum PatientStatus {

    HOSPITALIZED("Hospitaliziran"),
    DISCHARGED("Otpušten"),
    UNDER_DIAGNOSIS("Na pregledu");


    private final String status;

    PatientStatus(String status){
        this.status=status;
    }

    public String getStatus(){
        return status;
    }



    public static PatientStatus fromStatus(String statusStr) {
        for (PatientStatus ps : values()) {
            if (ps.getStatus().equalsIgnoreCase(statusStr)) {
                return ps;
            }
        }
        throw new IllegalArgumentException("Nepoznat PatientStatus: " + statusStr);
    }
}

