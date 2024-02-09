package org.javaacadmey.metro.metro.components;

import org.javaacadmey.metro.metro.Metro;
import org.javaacadmey.metro.metro.components.line.Line;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

public class Station {
    private String name;
    private Station previousStation;
    private Station nextStation;
    private Duration travelTime;
    private Line line;
    private Set<Station> transferStations;
    private Metro metro;
    private TicketOffice ticketOffice = new TicketOffice();;

    public Station(String name, Line line, Metro metro) {
        this.name = name;
        this.line = line;
        this.metro = metro;
    }

    public void addTransferStations(Set<Station> transferStations) {
        if (transferStations != null && !transferStations.isEmpty()) {
            this.transferStations = new HashSet<>();
            this.transferStations.addAll(transferStations);
        }
    }

    public void sellTicket(LocalDateTime dateTime, String startStationName, String endStationName) {
        int countRuns = metro.countRunsBetweenStations(startStationName, endStationName);
        double price = countRuns * 5 + 20;
        ticketOffice.addCheck(dateTime, price);
    }

    /**
     * 3.1 В классе станция должна быть функция продажи проездного билета на месяц.
     * На вход: дата продажи (java.util.LocalDate). Стоимость безлимитного проездного
     * 3000 рублей на месяц. Метро генерирует номер проездного формата [a][0000-9999].
     *
     * Пример: первый номер абонемента - а0000, тысяча первый билет - a1001.
     * Метро хранит список проданных абонементов и их дату окончания.
     * Доход от продажи засчитывается в кассу продавшей станции.
     */

    public void sellAbonnement(LocalDateTime dateTime) {
        double price = 3000;

        ticketOffice.addCheck(dateTime, price);

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

    public void setPreviousStation(Station previousStation) {
        this.previousStation = previousStation;
    }

    public Set<Station> getTransferStations() {
        return transferStations;
    }

    public Station getPreviousStation() {
        return previousStation;
    }

    public Line getLine() {
        return line;
    }

    private String changeLinesColor() {
        StringBuilder colors = new StringBuilder();

        if (transferStations != null && !transferStations.isEmpty()) {
            for (Station station : transferStations) {
                colors.append(station.line.getColor().getName()).append('\'');
            }
        } else {
            colors.append("null");
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
