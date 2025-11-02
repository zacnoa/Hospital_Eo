package entity;

public class ArrayException extends Exception {
    public ArrayException(String message) {
        super(message);
    }
    public ArrayException()
    {
        super("Pokušaj manipulacije sa praznim poljem");
    }
}
