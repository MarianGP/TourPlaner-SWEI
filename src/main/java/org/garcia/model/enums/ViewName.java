package org.garcia.model.enums;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum ViewName {
    HOME("homeView"),
    TOURS("toursView"),
    ADD_TOUR("addTourDialog"),
    ADD_LOG("addTourLogDialog"),
    SAVE_TOUR_REPORT("saveReportDialog")
    ;

    private final String viewName;

    public String getViewName() {
        return viewName;
    }
}
