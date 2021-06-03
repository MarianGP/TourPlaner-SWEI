package org.garcia.layerView.controller;

import javafx.fxml.Initializable;
import org.garcia.layerBusiness.appmanager.IAppManager;
import org.garcia.layerView.viewModel.IViewModel;
import org.garcia.model.Tour;
import org.garcia.visitor.IVisitor;

import java.net.URL;
import java.util.ResourceBundle;

public class EditTourLogController implements Initializable, IController {

//    private EditTourVIewModel viewModel;
//
//
//
//    public void closeDialog(@NotNull ActionEvent actionEvent) {
//        Node source = (Node) actionEvent.getSource();
//        Stage stage = (Stage) source.getScene().getWindow();
//        stage.close();
//    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
//        viewModel = EditTourVIewModel.getInstance();
    }

    public void initViewModel(IAppManager appManager, Tour tour) {
//        viewModel.init(appManager,tour);
    }


    @Override
    public void accept(IVisitor visitor, IViewModel viewModel) {
//        visitor.visit(this, viewModel);
    }
}
