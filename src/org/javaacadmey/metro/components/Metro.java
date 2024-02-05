package org.javaacadmey.metro.components;

import org.javaacadmey.metro.exceptions.ColorLineNotExistException;
import org.javaacadmey.metro.exceptions.DuplicateColorLineException;
import org.javaacadmey.metro.exceptions.DuplicateStationNameException;
import org.javaacadmey.metro.exceptions.LineIsNotEmptyException;

import java.time.Duration;
import java.util.HashSet;
import java.util.Set;

public class Metro {
    private City city;
    private Set<Line> lines = new HashSet<>();

    public Metro(City city) {
        this.city = city;
    }

    public void createLine(LineColor color) throws DuplicateColorLineException {
        if (isExistColorLine(color)) {
            throw new DuplicateColorLineException(color);
        }

        Line line = new Line(color, this);
        lines.add(line);
    }

    public Station createFirstStationLine(LineColor color, String name)
            throws ColorLineNotExistException, DuplicateStationNameException, LineIsNotEmptyException {
        return helpCreateFirstStationLine(color, name);
    }

    public Station createFirstStationLine(LineColor color, String name, Set<Station> transferStations)
            throws ColorLineNotExistException, DuplicateStationNameException, LineIsNotEmptyException {
        Station newStation = helpCreateFirstStationLine(color, name);
        newStation.addTransferStations(transferStations);
        return newStation;
    }

    public Station createLastStation(LineColor color, String name, Duration travelTime)
            throws ColorLineNotExistException, DuplicateStationNameException {
        return helpCreateLastStation(color, name, travelTime);
    }

    public Station createLastStation(LineColor color, String name, Duration travelTime, Set<Station> transferStations)
            throws ColorLineNotExistException, DuplicateStationNameException {
        Station newStation = helpCreateLastStation(color, name, travelTime);
        newStation.addTransferStations(transferStations);
        return newStation;
    }

    private Station helpCreateFirstStationLine(LineColor color, String name)
            throws LineIsNotEmptyException, ColorLineNotExistException, DuplicateStationNameException {
        Line targetLine = findLineByColorAndIsExistNameStation(color, name);

        if (!linesIsEmpty(targetLine)) {
            throw new LineIsNotEmptyException();
        }

        return createStation(targetLine, name);
    }

    private Station helpCreateLastStation(LineColor color, String name, Duration travelTime)
            throws ColorLineNotExistException, DuplicateStationNameException {
        Line targetLine = findLineByColorAndIsExistNameStation(color, name);

        if (linesIsEmpty(targetLine)) {
            System.out.println("не существует первой станции");
            //throw new exeption
        }

        if (travelTime.isZero()) {
            System.out.println("время перегона должно быть больше 0");
            //throw new exeption
        }

        Station stationWithoutNext = targetLine.getLastStation();
        Station newStation = createStation(targetLine, name);
        stationWithoutNext.setTravelTime(travelTime);
        stationWithoutNext.setNextStation(newStation);
        newStation.setPreviousStation(stationWithoutNext);
        return newStation;
    }

    private Line findLineByColorAndIsExistNameStation(LineColor color, String name)
            throws ColorLineNotExistException, DuplicateStationNameException {
        isExistNameStation(name);
        return findLine(color);
    }

    private Station createStation(Line line, String name) {
        Station station = new Station(name, line, this);
        line.addStation(station);
        return station;
    }

    private Line findLine(LineColor color) throws ColorLineNotExistException {
        return lines.stream()
                .filter(line -> line.getColor().equals(color))
                .findAny()
                .orElseThrow(() -> new ColorLineNotExistException(color));
    }

    private boolean linesIsEmpty(Line line) {
        return line.getStations().isEmpty();
    }

    private boolean isExistColorLine(LineColor color) {
        return lines.stream()
                .anyMatch(line -> line.getColor().equals(color));
    }

    private void isExistNameStation(String name) throws DuplicateStationNameException {
        boolean result  = lines.stream().
                anyMatch(line -> line.getStations().stream().
                        anyMatch(station -> station.getName().equals(name)));

        if (result) {
            throw new DuplicateStationNameException(name);
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
