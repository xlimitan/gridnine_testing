package com.gridnine.testing;

import java.util.List;
import java.util.stream.Collectors;

public class Flight {

    private final List<Segment> segments;

    Flight(final List<Segment> segments) {
        this.segments = segments;
    }

    List<Segment> getSegments() {
        return segments;
    }

    @Override
    public String toString() {
        return segments.stream().map(Object::toString)
                .collect(Collectors.joining(" "));
    }
}
