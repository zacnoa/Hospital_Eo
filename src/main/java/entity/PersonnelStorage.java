package entity;

import java.util.HashMap;
import java.util.Map;

public class PersonnelStorage {
    public static  Map<String,Doctor> doctorStorage=new HashMap<>();
    public static Map<String,Patient> patientStorage=new HashMap<>();
    public static Map<String,Room> roomStorage=new HashMap<>();

    public static Doctor findDoctor(String id)
    {
        return doctorStorage.get(id);
    }
    public static Patient findPatient(String id)
    {
        return patientStorage.get(id);
    }
    public static Room findRoom(String id)
    {
        return roomStorage.get(id);
    }


}


