package Main;

import entity.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import userLogs.LogManager;

import java.io.IOException;
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

        System.out.println("Dobro došli u našu bolnicu!!\n");
        try
        {
            DataBaseManager.loadFromLocalDataBase();
            DataBaseManager.createBackup();
        }catch (IOException e)
        {
            logger.error("Error while loading localDataBase",e.getMessage(),e);
        }



        String mainCommand;
        do {
            System.out.println("""
                    Unesite redni broj komande koju zelite pozvati (ili X za kraj programa):
                    1.) Dodati novi odjel:
                    2.) Lista Odjela:
                    3.) Ispis zaposlenika:
                    4.) Učitaj pomočnu kopiju
                    5.)Ispiši Log
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
                        } catch (IllegalArgumentException e) {
                            failedFlag = true;

                            logger.error(e.getMessage(), e);
                            System.out.println(e.getMessage());

                        }
                        catch (IOException e)
                        {
                            logger.error("Greska pri spremanju departmentStorage u bazu",e.getMessage(), e);
                        }
                    } while (failedFlag);
                    LogManager.addLog("Stvoren je novi odjel");
                }
                case "2" -> {
                    String secondCommand;

                    do {
                        Utility.printMenuSelection(DepartmentStorage.departments);
                        secondCommand = sc.nextLine();
                        if (!"X".equalsIgnoreCase(secondCommand)) {
                            Integer izabraniOdjel = Integer.parseInt(secondCommand);
                            izabraniOdjel--;
                            DepartmentMenu.DepartmentMenu(DepartmentStorage.departments.get(izabraniOdjel), sc);
                        }

                    } while (!"X".equalsIgnoreCase(secondCommand));
                }
                case "3" -> {
                    Person.printAllPersons();
                }
                case "4" ->{
                    try {
                        DataBaseManager.loadFromBackup();
                    }catch(IOException |ClassNotFoundException e)
                    {
                        logger.error("Greška pri ucitavanju pomocne kopije",e.getMessage(),e);
                    }
                }
                case "5" ->{
                    LogManager.printLogs();
                }
            }
        }while(!"X".equalsIgnoreCase(mainCommand));
    }


}


