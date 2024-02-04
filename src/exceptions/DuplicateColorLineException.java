package exceptions;

import components.LineColor;

public class DuplicateColorLineException extends Exception {
    public DuplicateColorLineException(LineColor color) {
        super("Линия с цветом " + color + " уже существует.");
    }
}
