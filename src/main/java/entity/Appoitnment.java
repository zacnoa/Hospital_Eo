package entity;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public record Appoitnment(Patient patient, LocalDate time) implements Referable {



    public void appoitnmentTime() {
        System.out.println("Uputnica za " + patient.getName() + " je: " + time.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
    }




}
