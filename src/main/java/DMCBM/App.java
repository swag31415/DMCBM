package DMCBM;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class App extends Application {

    private static Stage pStage;
    private static App instance;

    public enum fxmlFiles {
        StatController("StatBlocks/StatGUI.fxml"),
        webPaneController("StatBlocks/webPaneGUI.fxml");

        private String file;

        private fxmlFiles(String file) {
            this.file = file;
        }

        public String get() {
            return this.file;
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        App.pStage = primaryStage;

        primaryStage.setResizable(false);

        loadScene(fxmlFiles.StatController, App.pStage);
        primaryStage.show();
    }

    @Override
    public void init() {
        App.instance = this;
    }

    public static <T> T loadScene(fxmlFiles file, Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(instance.getClass().getClassLoader().getResource(file.get()));
        Pane pane = loader.<Pane>load();
        stage.setScene(new Scene(pane));
        return loader.getController();
    }
}