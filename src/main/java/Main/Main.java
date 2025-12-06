package Main;

import entity.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Scanner;

/**
 * Klasa za pokretanje programa
 */


public class Main {

    /**
     * metoda za pokretanje programan
     * @param args argumenti
     */
    static void main(String[] args) {


        Logger logger= LoggerFactory.getLogger(Main.class);



        Scanner sc = new Scanner(System.in);

        System.out.println("Dobro došli u našu bolnicu!!\nMolimo vas popunite našu evidenciju.");
        DepartmentStorage.addDepartment(sc);



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
                            DepartmentStorage.addDepartment(sc);
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
                        Utility.printMenuSelection(DepartmentStorage.departments);
                        secondCommand = sc.nextLine();
                        if(!"X".equalsIgnoreCase(secondCommand))
                        {
                            Integer izabraniOdjel = Integer.parseInt(secondCommand);
                            izabraniOdjel--;
                            DepartmentMenu.DepartmentMenu(DepartmentStorage.departments.get(izabraniOdjel),sc);
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


