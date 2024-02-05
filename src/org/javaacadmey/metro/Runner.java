package org.javaacadmey.metro;

import org.javaacadmey.metro.components.City;
import org.javaacadmey.metro.components.LineColor;
import org.javaacadmey.metro.components.Metro;
import org.javaacadmey.metro.components.Station;
import org.javaacadmey.metro.exceptions.ColorLineNotExistException;
import org.javaacadmey.metro.exceptions.DuplicateColorLineException;
import org.javaacadmey.metro.exceptions.DuplicateStationNameException;
import org.javaacadmey.metro.exceptions.LineIsNotEmptyException;

import java.time.Duration;
import java.util.Set;

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
            metro.createFirstStationLine(LineColor.RED, "Спортивная");
            metro.createLastStation(LineColor.RED, "Медведковская", Duration.ofMinutes(2).plusSeconds(21));
            metro.createLastStation(LineColor.RED, "Молодежная", Duration.ofMinutes(1).plusSeconds(58));
            Station perm1 = metro.createLastStation(LineColor.RED, "Пермь 1", Duration.ofMinutes(3));
            metro.createLastStation(LineColor.RED, "Пермь 2", Duration.ofMinutes(2).plusSeconds(10));
            metro.createLastStation(LineColor.RED, "Дворец Культуры", Duration.ofMinutes(4).plusSeconds(26));

            metro.createFirstStationLine(LineColor.BLUE, "Пацанская");
            metro.createLastStation(LineColor.BLUE, "Улица Кирова", Duration.ofMinutes(1).plusSeconds(30));
            Station tyazhmash = metro.createLastStation(LineColor.BLUE, "Тяжмаш", Duration.ofMinutes(1).plusSeconds(47), Set.of(perm1));
            metro.createLastStation(LineColor.BLUE, "Нижнекамская", Duration.ofMinutes(3).plusSeconds(19));
            metro.createLastStation(LineColor.BLUE, "Соборная", Duration.ofMinutes(1).plusSeconds(48));

            perm1.addTransferStations(Set.of(tyazhmash));
        } catch (ColorLineNotExistException | DuplicateStationNameException | LineIsNotEmptyException e) {
            System.out.println(e.getMessage());
        }
        System.out.println("добавила по первой станции: " + metro);
    }
}
