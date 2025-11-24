package entity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;
import java.util.Scanner;


public class DepartmentMenu {

    private static Logger logger= LoggerFactory.getLogger(DepartmentMenu.class);

    public static void DepartmentMenu(Department selectedDepartment, Scanner sc)
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
                        boolean failedFlag;
                        do {
                            failedFlag = false;
                            try {
                                selectedDepartment.addDoctor(sc);
                            }catch(IllegalArgumentException e)
                            {
                                System.out.println(e.getMessage());
                                logger.error(e.getMessage(),e);
                                failedFlag=true;
                            }
                        }while(failedFlag);
                    }
                    case "2" ->{
                        boolean failedFlag;
                        do {
                            failedFlag=false;
                            try {
                                selectedDepartment.addPatient(sc);
                            }
                            catch (IndexOutOfBoundsException e) {
                                failedFlag=true;
                                logger.error(e.getMessage(),e);
                                System.out.println(e.getMessage());
                            }
                            catch(PersonnelException e)
                            {
                                logger.error(e.getMessage(),e);
                                System.out.println(e.getMessage());
                            }
                        }while(failedFlag);
                    }
                    case "3" ->{
                        selectedDepartment.addRoom();
                    }
                    case "4"->{
                        selectedDepartment.doctorSearchBySpecialty(sc);
                    }
                    case "5"->{
                        selectedDepartment.patientSearchByDiagnosis(sc);
                    }
                    case "6"->{
                        System.out.println("Unesite ime trazenog pacijenta");
                        String name=sc.nextLine();
                        Optional<Patient> patient=selectedDepartment.findPatientByName(name);



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
