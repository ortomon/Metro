package org.javaacadmey.metro.metro;

import org.javaacadmey.metro.City;
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
            throw new IllegalArgumentException("Линия с таким цветом уже существует");
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

    private Station findTransferStation(Line fromLine, Line toLine) {
        return fromLine.getStations().stream()
                .flatMap(station -> station.getTransferStations().stream())
                .filter(station -> station != null && station.getLine().equals(toLine))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("не существует станции для пересадки"));
    }

    public int countRunsBetweenStations(String startStationName, String endStationName) {
        Station startStation = findStationByName(startStationName)
                .orElseThrow(() -> new IllegalArgumentException("не сущетсвует станции с названием: " + startStationName));
        Station endStation = findStationByName(endStationName)
                .orElseThrow(() -> new IllegalArgumentException("не сущетсвует станции с названием: " + endStationName));

        if (startStation.equals(endStation)) {
            throw new IllegalArgumentException("начальная станция не может совпадать с конечной.");
        }

        Line startLine = startStation.getLine();
        Line endLine = endStation.getLine();

        if (startLine.equals(endLine)) {
            return countRunsBetweenStations(startStation, endStation);
        }

        // станция для пересадки в начальной линии
        Station transferStartStation = findTransferStation(startStation.getLine(), endStation.getLine());

        // станция для пересадки в конечной линии
        Station transferEndStation = findTransferStation(endStation.getLine(), startStation.getLine());

        int countStartLine = countRunsBetweenStations(startStation, transferStartStation);
        int countEndLine = countRunsBetweenStations(transferEndStation, endStation);
        return countStartLine + countEndLine;
    }

    private int countRunsBetweenStations(Station startStation, Station endStation) {
        int forward = countRunsForNextStation(startStation, endStation);

        if (forward != -1) {
            return forward;
        }

        int back = countRunsForPreviousStation(startStation, endStation);

        if (back != -1) {
            return back;
        }

        throw new IllegalArgumentException("Нет пути из станции " + startStation.getName() + " в станцию " + endStation.getName());
    }

    private int countRunsForNextStation(Station startStation, Station endStation) {
        int count = 0;
        Station currentStation = startStation.getNextStation();

        while (currentStation != null && !currentStation.equals(endStation)) {
            currentStation = currentStation.getNextStation();
            count++;
        }

        if (currentStation == null) {
            return -1;
        }

        return count + 1;
    }

    private int countRunsForPreviousStation(Station startStation, Station endStation) {
        int count = 0;
        Station currentStation = startStation.getPreviousStation();

        while (currentStation != null && !currentStation.equals(endStation)) {
            currentStation = currentStation.getPreviousStation();
            count++;
        }

        if (currentStation == null) {
            return -1;
        }

        return count + 1;
    }

    private Station helpCreateFirstStationLine(LineColor color, String name) {
        Line targetLine = helpCreateStation(color, name);

        if (!lineIsEmpty(targetLine)) {
            throw new RuntimeException("Внутри линии уже есть станции");
        }

        return createStation(targetLine, name);
    }

    private Station helpCreateLastStation(LineColor color, String name, Duration travelTime) {
        Line targetLine = helpCreateStation(color, name);

        if (lineIsEmpty(targetLine)) {
            throw new RuntimeException("Внутри линии нет первой станции");
        }

        if (travelTime.isZero()) {
            throw new IllegalArgumentException("время перегона должно быть больше 0");
        }

        Station stationWithoutNext = targetLine.getLastStation();
        Station newStation = createStation(targetLine, name);

        stationWithoutNext.setTravelTime(travelTime);
        stationWithoutNext.setNextStation(newStation);
        newStation.setPreviousStation(stationWithoutNext);

        return newStation;
    }

    private Line helpCreateStation(LineColor color, String name) {
        if (findStationByName(name).isPresent()) {
            throw new IllegalArgumentException("Станция с названием " + name + " уже существует.");
        }
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
                .orElseThrow(() -> new IllegalArgumentException("Линия с цветом " + color + " не существует."));
    }

    private boolean lineIsEmpty(Line line) {
        return line.getStations().isEmpty();
    }

    private boolean isExistColorLine(LineColor color) {
        return lines.stream()
                .anyMatch(line -> line.getColor().equals(color));
    }

    private Optional<Station> findStationByName(String name) {
        return lines.stream()
                .flatMap(line -> line.getStations().stream())
                .filter(station -> station.getName().equals(name))
                .findFirst();
    }

    @Override
    public String toString() {
        return "Metro{" +
                "city=" + city +
                ", lines=" + lines +
                '}';
    }
}
