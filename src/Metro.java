import exceptions.ColorLineNotExistException;
import exceptions.DuplicateColorLineException;
import exceptions.DuplicateStationNameException;
import exceptions.LineIsNotEmptyException;

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
            throw new DuplicateColorLineException(color.getName());
        }

        Line line = new Line(color, this);
        lines.add(line);
    }

    // -Линия с таким цветом существует
    // -Станции с таким именем не существует во всех линиях.
    // -Внутри линии нет станций
    public void createFirstStationLine(LineColor color, String name, Set<Station> transferStations) throws ColorLineNotExistException, DuplicateStationNameException, LineIsNotEmptyException {
        Line targetLine = null;

        // - Линия с таким цветом существует
        for (Line line : lines) {
            if (line.getColor().equals(color)) {
                targetLine = line;
                break;
            }
        }

        // -Линия с таким цветом не нашлась
        if (targetLine == null) {
            throw new ColorLineNotExistException(color.getName());
        }


        if (isExistNameStation(name)) {
            throw new DuplicateStationNameException(name);
        }

        if (!linesIsEmpty(color)) {
            throw new LineIsNotEmptyException();
        }

        Station firstStation = new Station(name, targetLine, transferStations);
        targetLine.getStations().add(firstStation);
    }

    public void createLastStation(LineColor color, String name, Duration travelTime, Set<Station> transferStations) throws ColorLineNotExistException, DuplicateStationNameException {
        Line targetLine = null;

        for (Line line : lines) {
            if (line.getColor().equals(color)) {
                targetLine = line;
                break;
            } else {
                throw new ColorLineNotExistException(color.getName());
            }
        }

        if (isExistNameStation(name)) {
            throw new DuplicateStationNameException(name);
        }

        if (linesIsEmpty(color)) {
            System.out.println("не существует первой станции");
        }

        if (travelTime.isZero()) {
            System.out.println("время перегона меньше или равно 0");

            //            throw new DuplicateStationNameException(name);
        }

        if (targetLine != null) {
            for (Station previousStation : targetLine.getStations()) {
                if (previousStation.getNextStation() == null) {
                    previousStation.setTravelTime(travelTime);
                    Station lastStation = new Station(name, targetLine, transferStations);
                    targetLine.getStations().add(lastStation);
                    previousStation.setNextStation(lastStation);
                }
            }
        }
    }

    private boolean linesIsEmpty(LineColor color) {
        for (Line line : lines) {
            if (line.getColor().equals(color)) {
                if (line.getStations().isEmpty()) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean isExistColorLine(LineColor color) {
        for (Line line : lines) {
            if (line.getColor().equals(color)) {
                return true;
            }
        }
        return false;
    }

    private boolean isExistNameStation(String name) {
        for (Line line : lines) {
            for (Station station : line.getStations()) {
                if (station.getName().equals(name)) {
                    return true;
                }
            }
        }

        return false;
    }

    @Override
    public String toString() {
        return "Metro{" +
                "city=" + city +
                ", lines=" + lines +
                '}';
    }
}