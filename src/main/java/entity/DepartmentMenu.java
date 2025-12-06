package entity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;
import java.util.Scanner;


public class DepartmentMenu {

    private static Logger logger= LoggerFactory.getLogger(DepartmentMenu.class);

    public static void DepartmentMenu(Department Department, Scanner sc)
    {

            String command;
            do {
                System.out.println("""
                                        Unesite redni broj komande koju zelite pozvati:
                                        1.) Dodati doktora:
                                        2.) Dodati pacijenta:
                                        3.) Dodati sobu:
                                        4.) Pretražiti doktore po specijalizaciji:
                                        5.) Pretraziti pacijente po dijagnozi:
                                        6.) Pretraziti pacijente po imenu:
                                        7.) Napraviti uputnicu:
                                        8.) Ispisati Uputnice:
                                        X) Kraj:
                                        """
                );

                command = sc.nextLine();
                switch (command) {
                    case "1" -> {
                            try {
                                Department.addDoctor(sc);
                            }catch(IllegalArgumentException e)
                            {
                                System.out.println(e.getMessage());
                                logger.error(e.getMessage(),e);
                            }

                    }
                    case "2" ->{
                            try {
                                Department.addPatient(sc);
                            }
                            catch (IndexOutOfBoundsException e) {
                                logger.error(e.getMessage(),e);
                                System.out.println(e.getMessage());
                            }
                            catch(PersonnelException e)
                            {

                                logger.error(e.getMessage(),e);
                                System.out.println(e.getMessage());
                            }
                            catch(IllegalArgumentException e)
                            {
                                logger.error(e.getMessage(),e);
                                System.out.println(e.getMessage());
                            }
                    }
                    case "3" ->{
                        Department.addRoom();
                    }
                    case "4"->{
                        Department.doctorSearchBySpecialty(sc);
                    }
                    case "5"->{
                        Department.patientSearchByDiagnosis(sc);
                    }
                    case "6"->{
                        System.out.println("Unesite ime trazenog pacijenta");
                        String name=sc.nextLine();
                        Optional<Patient> patient= Department.findPatientByName(name);
                        PatientMenu.PatientMenu(patient, Department,sc);

                    }
                    case "7"->{
                        System.out.println("Feature deleted");
                    }
                    case  "8"->{
                        System.out.println("Feature deleted");
                    }

                }
            } while (!"X".equalsIgnoreCase(command));
    }




}
