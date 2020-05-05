package ui.exceptions;

/**
 * This exception is used only when checking for an incorrect user input.
 */
public class IncorrectInputException extends RuntimeException {
    public IncorrectInputException(String errorMessage) {
        super(errorMessage);
    }
}