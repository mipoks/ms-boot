package design.kfu.service.api.exception;

public class MusixException extends Exception {
    public MusixException(String message) {
        super(message);
    }

    public MusixException(String message, Throwable cause) {
        super(message, cause);
    }
}
