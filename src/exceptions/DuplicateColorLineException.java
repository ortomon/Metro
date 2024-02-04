package exceptions;

public class DuplicateColorLineException extends Exception {
    public DuplicateColorLineException(String color) {
        super("Линия с цветом " + color + " уже существует.");
    }
}
