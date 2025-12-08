package entity;

public enum DataType {

    PATIENT("Patient"),
    DOCTOR("Doctor"),
    ROOM("Room"),
    DEPARTMENT("Department"),
    VISITOR("Visitor"),
    INFO("Info");


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
