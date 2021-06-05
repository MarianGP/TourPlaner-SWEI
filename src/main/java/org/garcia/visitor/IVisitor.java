package org.garcia.visitor;

import org.garcia.layerView.controller.*;
import org.garcia.layerView.viewModel.IViewModel;

public interface IVisitor {
    void visit(ToursController controller, IViewModel viewModel);
    void visit(SaveReportController controller, IViewModel viewModel);
    void visit(AddTourController controller, IViewModel viewModel);
    void visit(AddTourLogController addTourLogController, IViewModel viewModel);
}
