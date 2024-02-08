package org.javaacadmey.metro.metro;

import org.javaacadmey.metro.metro.components.City;
import org.javaacadmey.metro.metro.components.line.Line;
import org.javaacadmey.metro.metro.components.line.LineColor;
import org.javaacadmey.metro.metro.components.Station;

import java.time.Duration;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class Metro {
    private City city;
    private Set<Line> lines = new HashSet<>();

    public Metro(City city) {
        this.city = city;
    }

    public void createLine(LineColor color) {
        if (isExistColorLine(color)) {
            throw new RuntimeException("Линия с таким цветом уже существует");
        }

        Line line = new Line(color, this);
        lines.add(line);
    }

    public Station createFirstStationLine(LineColor color, String name) {
        return helpCreateFirstStationLine(color, name);
    }

    public Station createFirstStationLine(LineColor color, String name, Set<Station> transferStations) {
        Station newStation = helpCreateFirstStationLine(color, name);
        newStation.addTransferStations(transferStations);
        return newStation;
    }

    public Station createLastStation(LineColor color, String name, Duration travelTime) {
        return helpCreateLastStation(color, name, travelTime);
    }

    public Station createLastStation(LineColor color, String name, Duration travelTime, Set<Station> transferStations) {
        Station newStation = helpCreateLastStation(color, name, travelTime);
        newStation.addTransferStations(transferStations);
        return newStation;
    }

    public Optional<Station> findTransferStation(Line fromLine, Line toLine) throws Exception {
        return fromLine.getStations().stream()
                .flatMap(station -> station.getTransferStations().stream())
                .filter(station -> station != null && station.getLine().equals(toLine))
                .findFirst();
    }

    private Station helpCreateFirstStationLine(LineColor color, String name) {
        Line targetLine = findLineByColorAndIsExistNameStation(color, name);

        if (!lineIsEmpty(targetLine)) {
            throw new RuntimeException("Внутри линии уже есть станции");
        }

        return createStation(targetLine, name);
    }

    private Station helpCreateLastStation(LineColor color, String name, Duration travelTime) {
        Line targetLine = findLineByColorAndIsExistNameStation(color, name);

        if (lineIsEmpty(targetLine)) {
            throw new RuntimeException("Внутри линии нет первой станции");        }

        if (travelTime.isZero()) {
            throw new RuntimeException("время перегона должно быть больше 0");
        }

        Station stationWithoutNext = targetLine.getLastStation();
        Station newStation = createStation(targetLine, name);

        stationWithoutNext.setTravelTime(travelTime);
        stationWithoutNext.setNextStation(newStation);
        newStation.setPreviousStation(stationWithoutNext);

        return newStation;
    }

    private Line findLineByColorAndIsExistNameStation(LineColor color, String name) {
        isExistNameStation(name);
        return findLine(color);
    }

    private Station createStation(Line line, String name) {
        Station station = new Station(name, line, this);
        line.addStation(station);
        return station;
    }

    private Line findLine(LineColor color) {
        return lines.stream()
                .filter(line -> line.getColor().equals(color))
                .findAny()
                .orElseThrow(() -> new RuntimeException("Линия с цветом " + color + " не существует."));
    }

    private boolean lineIsEmpty(Line line) {
        return line.getStations().isEmpty();
    }

    private boolean isExistColorLine(LineColor color) {
        return lines.stream()
                .anyMatch(line -> line.getColor().equals(color));
    }

    private void isExistNameStation(String name) {
        boolean result  = lines.stream()
                .flatMap(line -> line.getStations().stream())
                .anyMatch(station -> station.getName().equals(name));

        if (result) {
            throw new RuntimeException("Станция с названием " + name + " уже существует.");
        }
    }

    @Override
    public String toString() {
        return "Metro{" +
                "city=" + city +
                ", lines=" + lines +
                '}';
    }
}
