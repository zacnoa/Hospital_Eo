package entity;

import java.util.Optional;
import java.util.Scanner;

public class PatientMenu {


    public static void PatientMenu(Optional<Patient> selectedPatient, Department selectedDepartment, Scanner sc)
    {
        if(!selectedPatient.isPresent())
        {
            System.out.println("Pacijenta nije naden");
            return;
        }
        String command;
        do {
            System.out.println("""
                    Unesite redni broj pored komande koju zelite izvrsiti(ili X za natrag)
                    1.) Osnovne informacije:
                    2.)Premjestiti u drugi odjel:
                    X.) Kraj:            
                    """);
             command = sc.nextLine();

            switch (command) {
                case "1" -> {
                    selectedPatient.orElseThrow().basicInformation(); // treba catch ? mozda ne posto provejravamo prije
                }
                case "2" -> {

                    Utility.movePatientToDepartment(selectedDepartment.getPatients(),
                            DepartmentStorage.emergencyRoom.getPatients(),
                            selectedDepartment.getPatients().indexOf(selectedPatient));
                }
            }
        }while("X".equalsIgnoreCase(command));
    }

}
