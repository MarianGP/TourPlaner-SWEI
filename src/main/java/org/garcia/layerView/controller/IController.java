package org.garcia.layerView.controller;

import org.garcia.layerView.viewModel.IViewModel;
import org.garcia.appVisitor.IVisitor;

public interface IController {
    void accept(IVisitor visitor, IViewModel viewModel);
}
