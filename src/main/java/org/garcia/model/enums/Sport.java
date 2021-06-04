package org.garcia.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Getter
@AllArgsConstructor
public enum Sport {
    BIKE("Bike"),
    RUN("Run"),
    WALK("Walk"),
    HIKE("Hike");

    private final String sportActivity;

    public static List<String> getAllSport() {
        return Stream.of(Sport.values())
                .map(Sport::name)
                .collect(Collectors.toList());
    }
}
