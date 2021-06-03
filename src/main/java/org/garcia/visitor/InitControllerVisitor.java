package org.garcia.visitor;

import org.garcia.layerView.controller.AddTourController;
import org.garcia.layerView.controller.EditTourController;
import org.garcia.layerView.controller.SaveReportController;
import org.garcia.layerView.controller.ToursController;
import org.garcia.layerView.viewModel.IViewModel;

public class InitControllerVisitor implements IVisitor {
    @Override
    public void visit(ToursController toursController, IViewModel viewModel) {
        // TODO: maybe
    }

    @Override
    public void visit(SaveReportController controller, IViewModel viewModel) {
        controller.initViewModel(viewModel.getAppManager(), viewModel.getCurrentTour());
    }

    @Override
    public void visit(AddTourController controller, IViewModel viewModel) {
        controller.initViewModel(viewModel.getAppManager());
    }

    @Override
    public void visit(EditTourController editTourController, IViewModel viewModel) {
        // TODO: maybe
    }

}
