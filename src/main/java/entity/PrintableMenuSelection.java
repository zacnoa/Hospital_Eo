package entity;


import java.util.List;

/**
 * Sucelje za ispis polja
 */

public sealed  interface PrintableMenuSelection permits Doctor,Room,Department {



     String getSelectionLine();

}
