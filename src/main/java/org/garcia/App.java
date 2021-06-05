package org.garcia;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.Configurator;
import org.garcia.appVisitor.InitControllerVisitor;
import org.garcia.layerView.controller.IController;
import org.garcia.layerView.enums.ViewName;
import org.garcia.layerView.viewModel.IViewModel;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

// --module-path "C:\Users\mgarc\IdeaProjects\javafx-sdk-15.0.1\lib" --add-modules javafx.controls,javafx.fxml

/**
 * App entry class
 */
public class App extends Application {

    private static final int MAX_WIDTH = 1000;
    private static final int MIN_WIDTH = 400;
    private static final int MAX_HEIGHT = 1000;
    private static final int MIN_HEIGHT = 500;
    private static final String APP_TITLE = "Tour Planer";

    private static final InitControllerVisitor visitor = new InitControllerVisitor();
    private static final Logger logger = LogManager.getLogger(App.class);
    private final String initialView = ViewName.TOURS.getViewName();
    private static Scene scene;

    /**
     * Initializes log4j config
     */
    public App() {
        Path configDirectory = Paths.get("src", "main", "java", "org", "garcia", "config");
        String absolutePath = configDirectory.toFile().getAbsolutePath();
        Configurator.initialize(null, absolutePath + "/log4j-config.xml");
    }

    @Override
    public void start(Stage stage) throws IOException {
        logger.info("start app");
        scene = new Scene(loadFXML(initialView));
        stage.setMaxWidth(MAX_WIDTH);
        stage.setMaxHeight(MAX_HEIGHT);
        stage.setMinHeight(MIN_HEIGHT);
        stage.setMinWidth(MIN_WIDTH);
        stage.setTitle(APP_TITLE);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Allows changing views
     *
     * @param fxml next view to change to
     * @throws IOException wrong view name or location
     */
    public static void setRoot(String fxml) throws IOException {
        logger.info("change stage/view");
        scene.setRoot(loadFXML(fxml));
    }

    /**
     * Loads the specified view from the resources directory
     *
     * @param fxml view to be loaded
     * @return Parent object to be added to the scene
     */
    private static Parent loadFXML(String fxml) {
        var fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        Parent loader = null;
        try {
            loader = fxmlLoader.load();
        } catch (IOException e) {
            logger.error(fxml + " file not found." + e);
        }
        return loader;
    }

    /**
     * Opens new dialog and initializes viewModel.
     * The visitors pattern is used to call the initView() method controllers, which implement the IController interface
     * @param fxml  view name to be displayed
     * @param title title to be displayed on the dialog window
     * @param viewModel from the previous view to initialize new controller's view
     */
    public static void openDialog(final String fxml, final String title, IViewModel viewModel) {
        var fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        Parent loader = null;
        try {
            loader = fxmlLoader.load();
        } catch (IOException e) {
            logger.error(fxml + " file not found." + e);
        }
        assert loader != null;
        scene = new Scene(loader);
        Stage stage = new Stage();

        IController controller =  fxmlLoader.getController();
        if (controller == null)
            logger.error("Controller is null");
        else
            controller.accept(visitor, viewModel);
        logger.info("open dialog");
        stage.setTitle(title);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        stage.showAndWait();
    }


    /**
     * Start javafx application
     *
     * @param args not needed. default configuration
     */
    public static void main(String[] args) {
        launch();
    }

}
