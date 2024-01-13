package com.gridnine.testing;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

public class CreateFlightsTest {
    @Test
    void createFlights() {
        List<Flight> flightList = FlightBuilder.createFlights();
        Filter filter = new Filter(flightList);

        Assertions.assertAll(
                () -> Assertions.assertFalse(flightList.isEmpty(), "Список рейсов пуст"),
                () -> Assertions.assertTrue(flightList.stream()
                                .allMatch(flight -> flight.getSegments().stream()
                                        .allMatch(segment -> segment.getArrivalDate().isAfter(segment.getDepartureDate()))),
                        "Дата прибытия позже даты отъезда"),
                () -> Assertions.assertTrue(flightList.stream()
                                .allMatch(flight -> flight.getSegments().stream()
                                        .allMatch(segment -> segment.getDepartureDate().isAfter(LocalDateTime.now())))
                        , "Вылет до текущего момента времени"),
                () -> Assertions.assertEquals(filter.lessHrsWait(2).getFlightList().size(), flightList.size()
                        , "Отказаться от рейсов с ожиданием на земле более двух часов."));
    }
}
