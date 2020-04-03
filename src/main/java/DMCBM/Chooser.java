package DMCBM;

import java.io.IOException;

import DMCBM.App.fxmlFiles;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.stage.Stage;

public class Chooser extends Controller {

    @Override
    protected void init() {

    }

    @FXML
    void dice_button(ActionEvent event) throws IOException {
        Stage tStage = new Stage();
        App.loadScene(fxmlFiles.DiceController, tStage);
        tStage.show();
    }

    @FXML
    void dungeon_button(ActionEvent event) {

    }

    @FXML
    void monster_button(ActionEvent event) throws IOException {
        Stage tStage = new Stage();
        App.loadScene(fxmlFiles.StatController, tStage);
        tStage.show();
    }

    @FXML
    void npc_button(ActionEvent event) throws IOException {
        Stage tStage = new Stage();
        App.loadScene(fxmlFiles.NpcController, tStage);
        tStage.show();
    }

    @FXML
    void town_button(ActionEvent event) throws IOException {
        Stage tStage = new Stage();
        tStage.setResizable(false);
        App.loadScene(fxmlFiles.TownController, tStage);
        tStage.show();
    }

    @FXML
    void music_button(ActionEvent event) throws IOException {
        Stage tStage = new Stage();
        tStage.setResizable(false);
        App.loadScene(fxmlFiles.MusicController, tStage);
        tStage.show();
    }

    @FXML
    void close_button(ActionEvent event) {

    }

}
