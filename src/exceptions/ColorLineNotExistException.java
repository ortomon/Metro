package exceptions;

public class ColorLineNotExistException extends Exception{
    public ColorLineNotExistException(String color) {
        super("Линия с цветом " + color + " не существует.");
    }
}
