package org.javaacadmey.metro;

import org.javaacadmey.metro.metro.helpers.CheckedConsumer;
import org.javaacadmey.metro.metro.helpers.CheckedSupplier;
import org.javaacadmey.metro.metro.components.City;
import org.javaacadmey.metro.metro.components.line.LineColor;
import org.javaacadmey.metro.metro.Metro;
import org.javaacadmey.metro.metro.components.Station;
import org.javaacadmey.metro.exceptions.ColorLineNotExistException;
import org.javaacadmey.metro.exceptions.DuplicateColorLineException;
import org.javaacadmey.metro.exceptions.DuplicateStationNameException;
import org.javaacadmey.metro.exceptions.LineIsNotEmptyException;

import java.time.Duration;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.function.Predicate;

public class Runner {
    public static void main(String[] args) {
        // время перегона для redLine
        Duration travelTimeSport = Duration.ofMinutes(2).plusSeconds(21);
        Duration travelTimeMed = Duration.ofMinutes(1).plusSeconds(58);
        Duration travelTimeMolod = Duration.ofMinutes(3);
        Duration travelTimePerm1 = Duration.ofMinutes(2).plusSeconds(10);
        Duration travelTimePerm2 = Duration.ofMinutes(4).plusSeconds(26);

        // время перегона для blueLine
        Duration travelTimePatsan = Duration.ofMinutes(1).plusSeconds(30);
        Duration travelTimeKir = Duration.ofMinutes(1).plusSeconds(47);
        Duration travelTimeTyazh = Duration.ofMinutes(3).plusSeconds(19);
        Duration travelTimeNizhn = Duration.ofMinutes(1).plusSeconds(48);

        Metro metro = new Metro(City.PERM);
        executeSafely(() -> metro.createLine(LineColor.RED));
        executeSafely(() -> metro.createLine(LineColor.BLUE));

        executeSafely(() -> metro.createFirstStationLine(LineColor.RED, "Спортивная"));
        executeSafely(() -> metro.createLastStation(LineColor.RED, "Медведковская", travelTimeSport));
        executeSafely(() -> metro.createLastStation(LineColor.RED, "Молодежная", travelTimeMed));
        Station perm1 = executeSafely(() -> metro.createLastStation(LineColor.RED, "Пермь 1", travelTimeMolod));
        executeSafely(() -> metro.createLastStation(LineColor.RED, "Пермь 2", travelTimePerm1));
        executeSafely(() -> metro.createLastStation(LineColor.RED, "Дворец Культуры", travelTimePerm2));

        executeSafely(() -> metro.createFirstStationLine(LineColor.BLUE, "Пацанская"));
        executeSafely(() -> metro.createLastStation(LineColor.BLUE, "Улица Кирова", travelTimePatsan));

        Predicate<Station> isStationNull = Objects::isNull;

        if (!isStationNull.test(perm1)) {
            Station tyazhmash = executeSafely(() -> metro.createLastStation(LineColor.BLUE, "Тяжмаш", travelTimeKir, Set.of(perm1)));

            if (!isStationNull.test(tyazhmash)) {
                perm1.addTransferStations(Set.of(tyazhmash));
            }
        }

        executeSafely(() -> metro.createLastStation(LineColor.BLUE, "Нижнекамская", travelTimeTyazh));
        executeSafely(() -> metro.createLastStation(LineColor.BLUE, "Соборная", travelTimeNizhn));

        System.out.println(metro);
    }

    private static <T extends Station> T executeSafely(CheckedSupplier<T> supplier) {
        try {
            return supplier.get();
        } catch (ColorLineNotExistException | DuplicateStationNameException | LineIsNotEmptyException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    private static void executeSafely(CheckedConsumer consumer) {
        try {
            consumer.accept();
        } catch (DuplicateColorLineException e) {
            System.out.println(e.getMessage());
        }
    }
}
