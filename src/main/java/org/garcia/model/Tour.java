package org.garcia.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Tour {
    private int id;
    private String title;
    private String description;
    private String origin;
    private String destination;
    private String author;
    private String img;
    private int distance;
    private int duration;
}
