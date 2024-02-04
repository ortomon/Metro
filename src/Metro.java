import components.LineColor;
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
            throw new DuplicateColorLineException(color);
        }

        Line line = new Line(color, this);
        lines.add(line);
    }

    public void createFirstStationLine(LineColor color, String name, Set<Station> transferStations)
            throws ColorLineNotExistException, DuplicateStationNameException, LineIsNotEmptyException {
        Line targetLine = findLine(color);
        isExistNameStation(name);

        if (!linesIsEmpty(targetLine)) {
            throw new LineIsNotEmptyException();
        }

        createStation(targetLine, name, transferStations);
    }

    public void createLastStation(LineColor color, String name, Set<Station> transferStations, Duration travelTime)
            throws ColorLineNotExistException, DuplicateStationNameException {
        Line targetLine = findLine(color);
        isExistNameStation(name);

        if (linesIsEmpty(targetLine)) {
            System.out.println("не существует первой станции");
            //throw new exeption
        }

        if (travelTime.isZero()) {
            System.out.println("время перегона должно быть больше 0");
            //throw new exeption
        }

        Station stationWithoutNext = targetLine.getStationWithoutNext();
        Station newStation = createStation(targetLine, name, transferStations);
        stationWithoutNext.setTravelTime(travelTime);
        stationWithoutNext.setNextStation(newStation);
        newStation.setPreviousStation(stationWithoutNext);
    }

    private Station createStation(Line line, String name, Set<Station> transferStations) {
        Station station = new Station(name, line, transferStations, this);
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