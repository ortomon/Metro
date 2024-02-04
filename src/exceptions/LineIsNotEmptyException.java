package exceptions;

public class LineIsNotEmptyException extends Exception{
    public LineIsNotEmptyException() {
        super("Внутри линии уже есть станции");
    }
}
