package entity;

public enum DataType {

    PATIENT("Patient"),
    DOCTOR("Doctor"),
    ROOM("Room"),
    REGULAR_DEPARTMENT("RegularDepartment"),
    UNDERAGE_PATIENT("UnderagePatient"),
    VISITOR("Visitor"),
    ER_PATIENT("ERpatient");


    private final String type;

    DataType(String type)
    {
        this.type = type;
    }
    public String getType()
    {
        return type;
    }
}
