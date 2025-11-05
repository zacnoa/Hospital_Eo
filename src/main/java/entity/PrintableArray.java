package entity;

/**
 * Sucelje za ispis polja
 */
public sealed interface PrintableArray permits Doctor {

    /**
     * Ispisuje polje
     */
    void print ();
}
