package org.garcia.layerView.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ViewName {
    HOME("homeView", "Home"),
    TOURS("toursView", "Tour Planner"),
    ADD_TOUR("tourFormDialog", "Add New Tour"),
    EDIT_TOUR("tourFormDialog", "Edit Tour"),
    ADD_LOG("tourLogFormDialog", "Add New Log"),
    EDIT_LOG("tourLogFormDialog", "Edit Log"),
    SAVE_TOUR_REPORT("reportFormDialog", "Save Tour Report");

    private final String viewName;
    private final String viewTitle;
}
