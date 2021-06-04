package org.garcia.visitor;

import org.garcia.layerView.controller.*;
import org.garcia.layerView.viewModel.IViewModel;

public class InitControllerVisitor implements IVisitor {
    @Override
    public void visit(ToursController toursController, IViewModel viewModel) {
        // TODO: maybe
    }

    @Override
    public void visit(SaveReportController controller, IViewModel viewModel) {
        controller.initViewModel(viewModel);
    }

    @Override
    public void visit(AddTourController controller, IViewModel viewModel) {
        controller.initViewModel(viewModel);
    }

    @Override
    public void visit(EditTourController controller, IViewModel viewModel) {
        // TODO: maybe
    }

    @Override
    public void visit(AddTourLogController controller, IViewModel viewModel) {
        controller.initViewModel(viewModel);
    }

}
