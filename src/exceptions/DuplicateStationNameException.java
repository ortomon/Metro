package exceptions;

public class DuplicateStationNameException extends Exception {
    public DuplicateStationNameException(String name) {
        super("Станция с названием " + name + " уже существует.");
    }
}
