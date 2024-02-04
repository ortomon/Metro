import java.time.Duration;
import java.util.HashSet;
import java.util.Set;

public class Station {
    private String name;
    private Station previousStation = null;
    private Station nextStation = null;
    private Duration travelTime; // время перегона только до следующей станции
    private Line line;
    private Set<Station> transferStations = new HashSet<>();
    private Metro metro;

    public Station(String name, Line line, Set<Station> transferStations) {
        this.name = name;
        this.line = line;
        if (transferStations != null) {
            this.transferStations.addAll(transferStations);
        }
    }

    public String getName() {
        return name;
    }

    public Station getNextStation() {
        return nextStation;
    }

    public void setNextStation(Station nextStation) {
        this.nextStation = nextStation;
    }

    public Duration getTravelTime() {
        return travelTime;
    }

    public void setTravelTime(Duration travelTime) {
        this.travelTime = travelTime;
    }

    private String changeLinesColor() {
        StringBuilder colors = new StringBuilder();

        if (this.transferStations != null && !this.transferStations.isEmpty()) {
            for (Station station : this.transferStations) {
                if (station.line != null) {
                    colors.append(station.line.getColor()).append(". ");
                }
            }
        }

        return colors.toString();
    }

    @Override
    public String toString() {
        return "Station{" +
                "name='" + name + '\'' +
                "changeLines='" + changeLinesColor() + '\'' +
                '}';
    }
}
