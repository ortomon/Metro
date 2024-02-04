import components.LineColor;
import exceptions.ColorLineNotExistException;
import exceptions.DuplicateColorLineException;
import exceptions.DuplicateStationNameException;
import exceptions.LineIsNotEmptyException;

import java.awt.*;
import java.time.Duration;
import java.util.HashSet;

public class Runner {
    public static void main(String[] args) {
        Metro metro = new Metro(City.PERM);

        try {
            metro.createLine(LineColor.RED);
            metro.createLine(LineColor.BLUE);
        } catch (DuplicateColorLineException e) {
            System.out.println(e.getMessage());
        }

        try {
            metro.createFirstStationLine(LineColor.RED, "Спортивная", null);
            metro.createLastStation(LineColor.RED, "Медведковская", null, Duration.ofMinutes(2).plusSeconds(21));
            metro.createLastStation(LineColor.RED, "Молодежная", null, Duration.ofMinutes(1).plusSeconds(58));
            metro.createLastStation(LineColor.RED, "Пермь 1", null, Duration.ofMinutes(3));
            metro.createLastStation(LineColor.RED, "Пермь 2", null, Duration.ofMinutes(2).plusSeconds(10));
            metro.createLastStation(LineColor.RED, "Дворец Культуры", null, Duration.ofMinutes(4).plusSeconds(26));

            metro.createFirstStationLine(LineColor.BLUE, "Пацанская", null);
            metro.createLastStation(LineColor.BLUE, "Улица Кирова", null, Duration.ofMinutes(1).plusSeconds(30));
            metro.createLastStation(LineColor.BLUE, "Тяжмаш", null, Duration.ofMinutes(1).plusSeconds(47));
            metro.createLastStation(LineColor.BLUE, "Нижнекамская", null, Duration.ofMinutes(3).plusSeconds(19));
            metro.createLastStation(LineColor.BLUE, "Соборная", null, Duration.ofMinutes(1).plusSeconds(48));

        } catch (ColorLineNotExistException | DuplicateStationNameException | LineIsNotEmptyException e) {
            System.out.println(e.getMessage());
        }
        System.out.println("добавила по первой станции: " + metro);
    }
}
