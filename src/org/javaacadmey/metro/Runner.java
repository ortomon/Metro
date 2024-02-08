package org.javaacadmey.metro;

import org.javaacadmey.metro.metro.components.City;
import org.javaacadmey.metro.metro.components.line.LineColor;
import org.javaacadmey.metro.metro.Metro;
import org.javaacadmey.metro.metro.components.Station;

import java.time.Duration;
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
        metro.createLine(LineColor.RED);
        metro.createLine(LineColor.BLUE);

        metro.createFirstStationLine(LineColor.RED, "Спортивная");
        metro.createLastStation(LineColor.RED, "Медведковская", travelTimeSport);
        metro.createLastStation(LineColor.RED, "Молодежная", travelTimeMed);
        Station perm1 = metro.createLastStation(LineColor.RED, "Пермь 1", travelTimeMolod);
        metro.createLastStation(LineColor.RED, "Пермь 2", travelTimePerm1);
        metro.createLastStation(LineColor.RED, "Дворец Культуры", travelTimePerm2);

        metro.createFirstStationLine(LineColor.BLUE, "Пацанская");
        metro.createLastStation(LineColor.BLUE, "Улица Кирова", travelTimePatsan);
        Station tyazhmash = metro.createLastStation(LineColor.BLUE, "Тяжмаш", travelTimeKir, Set.of(perm1));
        metro.createLastStation(LineColor.BLUE, "Нижнекамская", travelTimeTyazh);
        metro.createLastStation(LineColor.BLUE, "Соборная", travelTimeNizhn);

        perm1.addTransferStations(Set.of(tyazhmash));
    }
}
