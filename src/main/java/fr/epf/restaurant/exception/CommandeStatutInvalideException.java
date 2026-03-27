package fr.epf.restaurant.exception;

public class CommandeStatutInvalideException extends RuntimeException {

    public CommandeStatutInvalideException(String message) {
        super(message);
    }

    public CommandeStatutInvalideException(String message, Throwable cause) {
        super(message, cause);
    }
}