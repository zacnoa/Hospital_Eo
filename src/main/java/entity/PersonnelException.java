package entity;


/**
 * Iznimka koja bi trebala biti bacena kada se pokusava pristupiti polju koje je prazno.
 * Odnosi se kada se pokusava dodati pacijent odjelu koji nema doktore ili slobodne sobe
 *
 */

public class PersonnelException extends Exception {
    public PersonnelException(String message) {
        super(message);
    }
    public PersonnelException()
    {
        super("Pokušaj manipulacije sa praznim poljem");
    }
}
