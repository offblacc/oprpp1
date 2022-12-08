package hr.fer.oprpp1.hw05.shell;

public class ShellIOException extends Exception {
    public ShellIOException() {
        super();
    }

    public ShellIOException(String message) {
        super(message);
    }

    public ShellIOException(String message, Throwable cause) {
        super(message, cause);
    }

    public ShellIOException(Throwable cause) {
        super(cause);
    }
}
