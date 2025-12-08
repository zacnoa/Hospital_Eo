package entity;
import jakarta.json.bind.*;
import jakarta.xml.bind.JAXBContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


public class DataBaseManager {

    private static Logger logger = LoggerFactory.getLogger(DataBaseManager.class);
    private static Jsonb jsonb = JsonbBuilder.create();

    public static  String jsonSerialization(Object object, DataType type) throws IOException {
        String fileToWrite = "LocalDataBase/";
        switch (type.getType()) {
            case "Patient" -> fileToWrite = fileToWrite.concat("patients.json");
            case "Room" -> fileToWrite = fileToWrite.concat("rooms.json");
            case "Doctor" -> fileToWrite = fileToWrite.concat("doctors.json");
            case "Department" -> fileToWrite = fileToWrite.concat("departments.json");
            case "Visitor" -> fileToWrite = fileToWrite.concat("visitors.json");

        }

        String json = jsonb.toJson(object);


        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(fileToWrite))) {
            writer.write(json);
            logger.info("JSON Serialization Successfull:" + json);

        }

        return json;
    }
    public static  void jsonDeserialization(DataType type) throws IOException {
        String fileToRead = "LocalDataBase/";
        switch (type.getType()) {
            case "Patient" -> fileToRead = fileToRead.concat("patients.json");
            case "Room" -> fileToRead = fileToRead.concat("rooms.json");
            case "Doctor" -> fileToRead = fileToRead.concat("doctors.json");
            case "Department" -> fileToRead = fileToRead.concat("departments.json");
            case "Visitor" -> fileToRead = fileToRead.concat("visitors.json");

        }


        String json=Files.readString(Paths.get(fileToRead));
        if(!json.isEmpty()) {
            switch (type.getType()) {
                case "Patient" -> {
                    PersonnelStorage.patientStorage = jsonb.fromJson(json, new HashMap<String, Patient>() {
                    }.getClass().getGenericSuperclass());
                }
                case "Room" -> {
                    PersonnelStorage.roomStorage = jsonb.fromJson(json, new HashMap<String, Room>() {
                    }.getClass().getGenericSuperclass());
                }
                case "Doctor" -> {
                    PersonnelStorage.doctorStorage = jsonb.fromJson(json, new HashMap<String, Doctor>() {
                    }.getClass().getGenericSuperclass());
                }
                case "Department" -> {
                    DepartmentStorage.departments = jsonb.fromJson(json, new ArrayList<Department>() {
                    }.getClass().getGenericSuperclass());
                }
                case "Visitor" ->
                        PersonnelStorage.visitorStorage = jsonb.fromJson(json, new HashMap<String, Visitor>() {
                        }.getClass().getGenericSuperclass());
            }
        }
    }

    public static void updateAllCollections() throws IOException {
        DataBaseManager.jsonSerialization(PersonnelStorage.patientStorage, DataType.PATIENT);
        DataBaseManager.jsonSerialization(PersonnelStorage.doctorStorage, DataType.DOCTOR);
        DataBaseManager.jsonSerialization(PersonnelStorage.roomStorage, DataType.ROOM);
        DataBaseManager.jsonSerialization(DepartmentStorage.departments,DataType.DEPARTMENT);
    }



    public static void loadFromLocalDataBase() throws IOException
    {
        jsonDeserialization(DataType.VISITOR);
        jsonDeserialization(DataType.ROOM);
        jsonDeserialization(DataType.PATIENT);
        jsonDeserialization(DataType.DOCTOR);
        jsonDeserialization(DataType.DEPARTMENT);


    }
    public static void createBackup() throws IOException
    {
        try(ObjectOutputStream writer =new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream("LocalDataBase/backup.bin"))))
        {
                for(Department department:DepartmentStorage.departments)
                {
                    writer.writeObject(department);
                }

        }
    }
    public static void loadFromBackup() throws IOException,ClassNotFoundException {

        List<Department> departmentsLocal = new ArrayList<>();
        try (ObjectInputStream reader = new ObjectInputStream(new BufferedInputStream(new FileInputStream("LocalDataBase/backup.bin")))){

            while (true) {
                try {
                    Department department = (Department) reader.readObject();
                    departmentsLocal.add(department);
                } catch (EOFException e) {
                    break;
                }
            }
            PersonnelStorage.doctorStorage=departmentsLocal.stream()
                    .flatMap(department->department.getDoctors().stream())
                    .collect(Collectors.toMap(Doctor::getId ,item->item,(first,second)->first));

            PersonnelStorage.visitorStorage=departmentsLocal.stream()
                    .flatMap(department->department.getVisitors().stream())
                    .collect(Collectors.toMap(Visitor::getId ,item->item,(first,second)->first));

            PersonnelStorage.patientStorage=departmentsLocal.stream()
                    .flatMap(department->department.getPatients().stream())
                    .collect(Collectors.toMap(Patient::getId ,item->item,(first,second)->first));

            PersonnelStorage.roomStorage=departmentsLocal.stream()
                    .flatMap(department->department.getRooms().stream())
                    .collect(Collectors.toMap(Room::getId ,item->item,(first,second)->first));

        }
        DepartmentStorage.setDepartments(departmentsLocal);
    }






}
