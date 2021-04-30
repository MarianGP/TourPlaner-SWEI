package org.garcia.model.enums;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum ViewName {
    HOME("homeView"),
    TOURS("toursView"),
    ADDTOUR("addTourDialog")
    ;

    private final String viewName;

    public String getViewName() {
        return viewName;
    }
}
