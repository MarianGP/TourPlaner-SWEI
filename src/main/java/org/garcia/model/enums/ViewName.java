package org.garcia.model.enums;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum ViewName {
    HOME("homeView"),
    TOURS("toursView"),
    ADD_TOUR("addTourDialog"),
    ADD_LOG("addTourLogDialog")
    ;

    private final String viewName;

    public String getViewName() {
        return viewName;
    }
}
