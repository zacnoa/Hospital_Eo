package Main;

import entity.Department;
import entity.ArrayException;
import entity.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Scanner;


public class Main {

    static void main(String[] args) {


        Logger logger= LoggerFactory.getLogger(Main.class);



        Scanner sc = new Scanner(System.in);

        System.out.println("Dobro došli u našu bolnicu!!\nMolimo vas popunite našu evidenciju.");
        Department[] departments= new  Department[10];
        Integer departmentCount=0;











        String mainCommand;
        do {
            System.out.println("""
                    Unesite redni broj komande koju zelite pozvati (ili X za kraj programa):
                    1.) Dodati novi odjel:
                    2.) Lista Odjela:
                    3.) Ispis zaposlenika:
                    X) Kraj:
                    """

            );
            mainCommand = sc.nextLine();
            switch (mainCommand) {
                case "1" -> {
                    boolean failedFlag;
                    do {
                        failedFlag = false;
                        try {
                            departments[departmentCount] = Department.generateDepartment(sc);
                            departmentCount++;
                        }
                        catch (IllegalArgumentException e) {
                            failedFlag=true;

                            logger.error(e.getMessage(),e);
                            System.out.println(e.getMessage());

                        }
                    }while(failedFlag);
                }
                case "2" -> {
                    String secondCommand;
                    do {
                        System.out.println("Izaberite redni broj pored odjela kojemu zelite pristupiti (ili X za natrag:)");
                        for (Integer i = 0; i < departmentCount; i++) {
                            System.out.println((i + 1) + ".) " + departments[i].getName());
                        }
                        System.out.println("X.) Kraj");
                        secondCommand = sc.nextLine();


                        if(!"X".equalsIgnoreCase(secondCommand)) {
                            Integer izabraniOdjel= Integer.parseInt(secondCommand);
                            String thirdCommand;
                            do {
                                System.out.println("""
                                        Unesite redni broj komande koju zelite pozvati:
                                        1.) Dodati doktora:
                                        2.) Dodati pacijenta:
                                        3.) Dodati sobu:
                                        4.) Pretražiti doktore po specijalizaciji:
                                        5.) Pretraziti pacijente po dijagnozi:
                                        6.) Napraviti uputnicu:
                                        7.) Ispisati Uputnice:
                                        X) Kraj:
                                        """
                                );

                                thirdCommand = sc.nextLine();
                                switch (thirdCommand) {
                                    case "1" -> {
                                        boolean failedFlag;
                                        do {
                                            failedFlag = false;
                                            try {
                                                departments[izabraniOdjel - 1].addDoctor(sc);
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
                                                departments[izabraniOdjel - 1].addPatient(sc);
                                            }
                                            catch (IndexOutOfBoundsException e) {
                                                failedFlag=true;
                                                logger.error(e.getMessage(),e);
                                                System.out.println(e.getMessage());
                                            }
                                            catch(ArrayException e)
                                            {
                                                logger.error(e.getMessage(),e);
                                                System.out.println(e.getMessage());
                                            }
                                        }while(failedFlag);
                                    }
                                    case "3" ->{
                                        departments[izabraniOdjel - 1].addRoom();
                                    }
                                    case "4"->{
                                        departments[izabraniOdjel - 1].doctorSearchBySpecialty(sc);
                                    }
                                    case "5"->{
                                        departments[izabraniOdjel-1].patientSearchByDiagnosis(sc);
                                    }
                                    case "6"->{
                                        departments[izabraniOdjel-1].addAppointment(sc);
                                    }
                                    case "7"->{
                                        departments[izabraniOdjel-1].printAppointments();
                                    }

                                }
                            } while (!"X".equalsIgnoreCase(thirdCommand));
                        }

                    } while (!"X".equalsIgnoreCase(secondCommand));
                }
                case "3" -> {
                    Person.printAllPersons();
                }
            }
        }while(!"X".equalsIgnoreCase(mainCommand));





    }
}


