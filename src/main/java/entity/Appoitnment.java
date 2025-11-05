package entity;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 *  Predstavlja uputnicu za pacijenta
 *
 * @param patient pacijent za koji se radi uputnica
 * @param time datum i vrijeme uputnice
 *
 */

public record Appoitnment(Patient patient, LocalDate time) implements Referable {


    /**
     * Ispisuje datum i vrijeme uputnice u formatu dd/MM/yyyy
     */
    public void appoitnmentTime() {
        System.out.println("Uputnica za " + patient.getName() + " je: " + time.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
    }




}
