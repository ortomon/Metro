package org.javaacadmey.metro.components;

import java.util.*;

public class Line {
    private LineColor color;
    private List<Station> stations = new LinkedList<>();
    private Metro metro;

    public Line(LineColor color, Metro metro) {
        this.color = color;
        this.metro = metro;
    }

    public void addStation(Station station) {
        stations.add(station);
    }

    public Station getLastStation() {
        return stations.get(stations.size() - 1);
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

    public List<Station> getStations() {
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
