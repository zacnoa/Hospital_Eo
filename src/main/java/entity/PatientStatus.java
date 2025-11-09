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
}

