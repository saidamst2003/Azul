package Azul.example.Azul.exception;

public class PasswordIncorrectException extends RuntimeException {
    public PasswordIncorrectException(String message ) {
        super( message );
    }
}
