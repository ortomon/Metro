import exceptions.ColorLineNotExistException;
import exceptions.DuplicateColorLineException;
import exceptions.DuplicateStationNameException;
import exceptions.LineIsNotEmptyException;

public class Runner {
    public static void main(String[] args) {
        Metro metro = new Metro(City.PERM);


        try {
            metro.createLine(LineColor.RED);
            metro.createLine(LineColor.BLUE);
        } catch (DuplicateColorLineException e) {
            System.out.println(e.getMessage());
        }
        System.out.println(metro);

        try {
            metro.createFirstStationLine(LineColor.RED, "Спортивная", null);
            metro.createFirstStationLine(LineColor.RED, "Медведковская", null);
            metro.createFirstStationLine(LineColor.RED, "Молодежная", null);
            metro.createFirstStationLine(LineColor.RED, "Пермь 1", null);
            metro.createFirstStationLine(LineColor.RED, "Спортивная", null);

            metro.createFirstStationLine(LineColor.BLUE, "Пацанская", null);
        } catch (ColorLineNotExistException | DuplicateStationNameException | LineIsNotEmptyException e) {
            System.out.println(e.getMessage());
        }
        System.out.println("добавила по первой станции: " + metro);


    }
}
