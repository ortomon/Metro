package exceptions;

import components.LineColor;

public class ColorLineNotExistException extends Exception{
    public ColorLineNotExistException(LineColor color) {
        super("Линия с цветом " + color + " не существует.");
    }
}
