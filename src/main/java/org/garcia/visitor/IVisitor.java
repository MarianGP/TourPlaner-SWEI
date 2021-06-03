package org.garcia.visitor;

import org.garcia.layerView.controller.AddTourController;
import org.garcia.layerView.controller.EditTourController;
import org.garcia.layerView.controller.SaveReportController;
import org.garcia.layerView.controller.ToursController;
import org.garcia.layerView.viewModel.IViewModel;

public interface IVisitor {
    void visit(ToursController controller, IViewModel viewModel);
    void visit(SaveReportController controller, IViewModel viewModel);
    void visit(AddTourController controller, IViewModel viewModel);
    void visit(EditTourController editTourController, IViewModel viewModel);
}
