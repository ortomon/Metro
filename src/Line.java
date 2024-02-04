import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

public class Line {
    private LineColor color;
    private Set<Station> stations = new LinkedHashSet<>();
    private Metro metro; // ссылкa на метро в которой линия находится.

    public Line(LineColor color, Metro metro) {
        this.color = color;
        this.metro = metro;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Line line = (Line) object;
        return color == line.color && Objects.equals(stations, line.stations) && Objects.equals(metro, line.metro);
    }

    @Override
    public int hashCode() {
        return Objects.hash(color, stations, metro);
    }

    public LineColor getColor() {
        return color;
    }

    public Set<Station> getStations() {
        return stations;
    }

    @Override
    public String toString() {
        return "Line{" +
                "color=" + color +
                ", stations=" + stations +
                '}';
    }
}
