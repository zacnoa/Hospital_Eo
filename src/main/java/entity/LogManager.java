package entity;

import entity.*;
import jakarta.xml.bind.*;
import jakarta.xml.bind.annotation.XmlTransient;


import java.io.File;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class LogManager {

    private static final String LOG_FILE = "log.xml";


    private static JAXBContext createJAXBContext() throws JAXBException {
        return JAXBContext.newInstance(
                LogWrapper.class,
                LogEntry.class,
                Doctor.class,
                Patient.class,
                Room.class,
                Department.class,
                Visitor.class,
                Person.class,
                Employee.class,
                DataType.class
        );
    }

    // Dodaj novi log entry
    public static void logDoctorCreated(Doctor doctor) {
        LogEntry entry = new LogEntry(DataType.DOCTOR, doctor);
        appendLogEntry(entry);
    }

    public static void logPatientAdmitted(Patient patient) {
        LogEntry entry = new LogEntry(DataType.PATIENT, patient);
        appendLogEntry(entry);
    }

    public static void logRoomAdded(Room room) {
        LogEntry entry = new LogEntry(DataType.ROOM, room);
        appendLogEntry(entry);
    }

    public static void logDepartmentCreated(Department department) {
        LogEntry entry = new LogEntry(DataType.ROOM, department);
        appendLogEntry(entry);
    }

    public static void logInfo(String message) {
        LogEntry entry = new LogEntry(DataType.INFO, message);
        appendLogEntry(entry);
    }
    public static void logVisitorAdmitted(Visitor visitor) {
        LogEntry entry = new LogEntry(DataType.VISITOR, visitor);
        appendLogEntry(entry);
    }

    // Glavna metoda za append
    private static  void appendLogEntry(LogEntry newEntry) {
        try {
            File file = new File(LOG_FILE);
            LogWrapper wrapper;

            if (file.exists() && file.length() > 0) {
                // Učitaj postojeće logove
                JAXBContext context = createJAXBContext();
                Unmarshaller unmarshaller = context.createUnmarshaller();
                wrapper = (LogWrapper) unmarshaller.unmarshal(file);
            } else {
                // Kreiraj novi wrapper
                wrapper = new LogWrapper();
            }

            // Dodaj novi entry
            wrapper.addLogEntry(newEntry);

            // Spremi natrag
            JAXBContext context = createJAXBContext();

            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.marshal(wrapper, file);

        } catch (JAXBException e) {
            e.printStackTrace();
            System.err.println("Greška pri logiranju: " + e.getMessage());
        }
    }

    // Ispiši sve logove
    public static void printAllLogs() {
        try {
            File file = new File(LOG_FILE);

            if (!file.exists() || file.length() == 0) {
                System.out.println("Log datoteka je prazna ili ne postoji.");
                return;
            }

            // Učitaj logove
            JAXBContext context = JAXBContext.newInstance(LogWrapper.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            LogWrapper wrapper = (LogWrapper) unmarshaller.unmarshal(file);

            List<LogEntry> logs = wrapper.getLogEntries();

            if (logs.isEmpty()) {
                System.out.println("Nema log zapisa.");
                return;
            }



            for (int i = 0; i < logs.size(); i++) {
                LogEntry log = logs.get(i);
                printSingleLogEntry(log, i + 1);
                System.out.println();
            }


        } catch (JAXBException e) {
            e.printStackTrace();
            System.err.println("Greška pri čitanju logova: " + e.getMessage());

        }
    }

    // Ispiši pojedinačni log entry
    private static void printSingleLogEntry(LogEntry log, int index) {
        System.out.printf("%3d. [%s] %s\n",
                index,
                log.getType()
        );

        // Ovisno o tipu, ispiši odgovarajuće podatke
        switch (log.getType().getType()) {
            case "Doctor":
                if (log.getDoctor() != null) {
                    Doctor d = log.getDoctor();
                    System.out.printf("     Doktor: %s (%s) - %s\n",
                            d.getName(), d.getId(), d.getSpecialty());
                }
                break;

            case "Patient":
                if (log.getPatient() != null) {
                    Patient p = log.getPatient();
                    System.out.printf("     Pacijent: %s (%s) - Dijagnoza: %s\n",
                            p.getName(), p.getId(), p.getDiagnosis());
                }
                break;

            case "Room":
                if (log.getRoom() != null) {
                    Room r = log.getRoom();
                    System.out.printf("     Soba: %s",
                            r.getId());
                }
                break;

            case "Department":
                if (log.getDepartment() != null) {
                    Department d = log.getDepartment();
                    System.out.printf("     Odjel: %s\n",
                            d.getName());
                }
                break;

            default:
                if (log.getMessage() != null) {
                    System.out.printf("     %s\n", log.getMessage());
                }
                break;
        }
    }

}