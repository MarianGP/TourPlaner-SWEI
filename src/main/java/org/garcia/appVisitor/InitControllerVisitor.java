package org.garcia.appVisitor;

import org.garcia.layerView.controller.*;
import org.garcia.layerView.viewModel.IViewModel;

public class InitControllerVisitor implements IVisitor {
    @Override
    public void visit(ToursController controller, IViewModel viewModel) {
        controller.initViewModel(viewModel);
    }

    @Override
    public void visit(ReportFormController controller, IViewModel viewModel) {
        controller.initViewModel(viewModel);
    }

    @Override
    public void visit(TourFormController controller, IViewModel viewModel) {
        controller.initViewModel(viewModel);
    }

    @Override
    public void visit(TourLogFormController controller, IViewModel viewModel) {
        controller.initViewModel(viewModel);
    }

}
