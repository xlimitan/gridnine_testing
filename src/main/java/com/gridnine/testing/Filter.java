package com.gridnine.testing;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class Filter {

    private static Logger log = Logger.getLogger(Filter.class.getName());
    private List<Flight> flightList;

    public Filter(List<Flight> flightList) {
        this.flightList = flightList;
    }

    /**
     * Вылет до текущего момента времени
     */
    public Filter departNotInPast() {
        Filter sortFilter = new Filter(flightList.stream()
                .filter(flight -> flight.getSegments().stream()
                        .allMatch(segment -> segment.getDepartureDate().isAfter(LocalDateTime.now())))
                .collect(Collectors.toList()));

        log.info("Filter: \"departNotInPast\" \n" + sortFilter.getFlightList() + "\n");
        return sortFilter;
    }

    /**
     * Рейсы с сегментами, где дата прибытия раньше даты вылета.
     */
    public Filter arrNotEarlierDept() {
        Filter sortFilter = new Filter(flightList.stream()
                .filter(flight -> flight.getSegments().stream()
                        .allMatch(segment -> segment.getArrivalDate().isAfter(segment.getDepartureDate())))
                .collect(Collectors.toList()));

        log.info("Filter: \"ArrNotEarlierDept\" \n" + sortFilter.getFlightList() + "\n");
        return sortFilter;
    }

    /**
     * Рейсы с ожиданием на земле более «n» часов.
     */
    public Filter lessHrsWait(int hours) {
        List<Flight> trueList = new ArrayList<>();
        for (Flight flight : flightList) {
            int count = 0;
            if (flight.getSegments().size() > 1) {
                for (int i = 0; i < flight.getSegments().size() - 1; i++) {
                    if (flight.getSegments().get(i).getArrivalDate().plusHours(hours).isAfter(flight.getSegments().get(i + 1).getDepartureDate())) {
                        count++;
                    }
                    if (count == flight.getSegments().size() - 1) {
                        trueList.add(flight);
                    }
                }
            } else {
                trueList.add(flight);
            }
        }

        Filter sortFilter = new Filter(trueList);
        log.info("Filter: \"LessHrsWait\" \n" + sortFilter.getFlightList() + "\n");
        return sortFilter;
    }

    public List<Flight> getFlightList() {
        return flightList;
    }
}
