package entity;

import RunTimeLog.RuntimeLogger;
import userLogs.LogManager;

import java.io.IOException;
import java.util.Optional;
import java.util.Scanner;


public class DepartmentMenu {



    public static void DepartmentMenu(Department department, Scanner sc)
    {

            String command;
            do {
                System.out.println("""
                                        Unesite redni broj komande koju zelite pozvati:
                                        1.) Dodati doktora:
                                        2.) Dodati pacijenta:
                                        3.) Dodati sobu:
                                        4.) Dodati posjetitelja:
                                        5.) Pretražiti doktore po specijalizaciji:
                                        6.) Pretraziti pacijente po dijagnozi:
                                        7.) Pretraziti pacijente po imenu:
                                        8.) Napraviti uputnicu:
                                        9.) Ispisati Uputnice:
                                        X) Kraj:
                                        """
                );

                command = sc.nextLine();
                switch (command) {
                    case "1" -> {
                            try {
                                department.addDoctor(sc);
                            }catch(IllegalArgumentException e)
                            {
                                System.out.println(e.getMessage());
                                RuntimeLogger.logger.error(e.getMessage(),e);
                            }
                        LogManager.addLog("Dodan je doktor");

                    }
                    case "2" ->{
                            try {
                                department.addPatient(sc);
                            }
                            catch (IndexOutOfBoundsException e) {
                                RuntimeLogger.logger.error(e.getMessage(),e);
                                System.out.println(e.getMessage());
                            }
                            catch(PersonnelException e)
                            {

                                RuntimeLogger.logger.error(e.getMessage(),e);
                                System.out.println(e.getMessage());
                            }
                            catch(IllegalArgumentException e)
                            {
                                RuntimeLogger.logger.error(e.getMessage(),e);
                                System.out.println(e.getMessage());
                            }
                        LogManager.addLog("Dodan je pacijent");
                    }
                    case "3" -> {
                        try {
                            department.addRoom();
                        }catch (IOException e) {
                            RuntimeLogger.logger.error("Error while serializing room", e.getMessage(), e);
                        }
                        LogManager.addLog("Dodana je soba");
                    }
                    case "4"->{
                        department.doctorSearchBySpecialty(sc);
                    }
                    case "5"->{
                        try {
                            department.addVisitor(sc);
                        }catch(IOException e)
                        {
                            RuntimeLogger.logger.error(e.getMessage(),e);
                        }
                        LogManager.addLog("Dodan je posjetitelj");
                    }
                    case "6"->{
                        department.patientSearchByDiagnosis(sc);
                    }
                    case "7"->{
                        System.out.println("Unesite ime trazenog pacijenta");
                        String name=sc.nextLine();
                        Optional<Patient> patient= department.findPatientByName(name);
                        PatientMenu.PatientMenu(patient, department,sc);

                    }
                    case "8"->{
                        System.out.println("Feature deleted");
                    }
                    case  "9"->{
                        System.out.println("Feature deleted");
                    }

                }
            } while (!"X".equalsIgnoreCase(command));
    }




}
