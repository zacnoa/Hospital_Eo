package entity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Optional;
import java.util.Scanner;


public class DepartmentMenu {

    private static Logger logger= LoggerFactory.getLogger(DepartmentMenu.class);

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
                                logger.error(e.getMessage(),e);
                            }

                    }
                    case "2" ->{
                            try {
                                department.addPatient(sc);
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
                        department.addRoom();
                    }
                    case "4"->{
                        department.doctorSearchBySpecialty(sc);
                    }
                    case "5"->{
                        try {
                            department.addVisitor(sc);
                        }catch(IOException e)
                        {
                            logger.error(e.getMessage(),e);
                        }
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
