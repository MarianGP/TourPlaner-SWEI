package org.garcia.layerView.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ViewName {
    HOME("homeView", "Home"),
    TOURS("toursView", "Tour Planner"),
    ADD_TOUR("addTourDialog", "Add New Tour"),
    EDIT_TOUR("editTourDialog", "Edit Tour"),
    ADD_LOG("addTourLogDialog", "Add New Log"),
    SAVE_TOUR_REPORT("saveReportDialog", "Save Tour Report");

    private final String viewName;
    private final String viewTitle;
}
