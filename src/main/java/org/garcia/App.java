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
import org.garcia.model.enums.ViewName;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

// --module-path "C:\Users\mgarc\IdeaProjects\javafx-sdk-15.0.1\lib" --add-modules javafx.controls,javafx.fxml

/**
 * App entry class
 */
public class App extends Application {

    private static Scene scene;
    private final Logger log;
    private final String initialView = ViewName.TOURS.getViewName();
    private static final int MAX_WIDTH = 640;
    private static final int MAX_HEIGHT = 800;


    /**
     * Add App class to log4j logger
     */
    public App() {
        Path configDirectory = Paths.get("src", "main", "java", "org", "garcia", "config");
        String absolutePath = configDirectory.toFile().getAbsolutePath();
        Configurator.initialize(null, absolutePath + "/log4j-config.xml");
        log = LogManager.getLogger(App.class);
    }

    @Override
    public void start(Stage stage) throws IOException {
        log.info("new scene created");
        scene = new Scene(loadFXML(initialView));
//        stage.getIcons().add(new Image(String.valueOf(getClass().getResource("img/dummy.png"))));
        stage.setMaxWidth(MAX_WIDTH);
        stage.setMaxHeight(MAX_HEIGHT);
        stage.setTitle("Tour Planer");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Allows changing views
     * @param fxml next view to change to
     * @throws IOException wrong view name or location
     */
    public static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }


    /**
     * Loads the specified view from the resources directory
     * @param fxml view to be loaded
     * @return Parent object to be added to the scene
     * @throws IOException wrong view name or location
     */
    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }


    /**
     * @param fxml view name to be displayed
     * @param title title to be displayed on the dialog window
     * @throws IOException wrong view name or location
     */
    public static void openDialog(final String fxml, final String title) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        Parent parent = fxmlLoader.load();
        Scene scene = new Scene(parent);

        Stage stage = new Stage();
        stage.setTitle(title);
        stage.initModality(Modality.APPLICATION_MODAL);

        stage.setScene(scene);
        stage.showAndWait();
    }

    /**
     * Start javafx application
     * @param args not needed. default configuration
     */
    public static void main(String[] args) {
        launch();
    }

}
