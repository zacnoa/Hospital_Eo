package entity;
import jakarta.json.bind.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;


public class JSONSerialization  {

    private static Logger logger = LoggerFactory.getLogger(JSONSerialization.class);
    private  static Jsonb jsonb=JsonbBuilder.create();
    public static  <T> String JsonSerialization(List<T> object,DataType type) throws IOException
    {
        String fileToWrite="LocalDataBase/";
        switch(type.getType())
        {
            case "Patient" -> fileToWrite=fileToWrite.concat("patients.json");
            case "Room" -> fileToWrite=fileToWrite.concat("rooms.json");
            case "Doctor" -> fileToWrite=fileToWrite.concat("doctors.json");
            case "Department" -> fileToWrite=fileToWrite.concat("departments.json");
            case "Visitor" -> fileToWrite=fileToWrite.concat("visitors.json");

        }

        String json=jsonb.toJson(object);



        try(BufferedWriter writer= new BufferedWriter(new FileWriter(fileToWrite)))
        {
            writer.write(json);
            logger.info("JSON Serialization Successfull:"+ json);

        }

        return json;
    }
    public static void UpdateLocalDataBase(Department department) throws IOException
    {
        JSONSerialization.JsonSerialization(department.getPatients(), DataType.PATIENT);
        JSONSerialization.JsonSerialization(department.getDoctors(), DataType.DOCTOR);
        JSONSerialization.JsonSerialization(department.getRooms(), DataType.ROOM);
        JSONSerialization.JsonSerialization(DepartmentStorage.departments, DataType.DEPARTMENT);
    }

}
