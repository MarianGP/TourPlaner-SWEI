package org.garcia.appVisitor;

import org.garcia.layerView.controller.*;
import org.garcia.layerView.viewModel.IViewModel;

public interface IVisitor {
    void visit(ToursController controller, IViewModel viewModel);
    void visit(ReportFormController controller, IViewModel viewModel);
    void visit(TourFormController controller, IViewModel viewModel);
    void visit(TourLogFormController addTourLogController, IViewModel viewModel);
}
