package DMCBM.StatBlocks;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import com.github.javafaker.Faker;

import DMCBM.App;
import DMCBM.Controller;
import DMCBM.App.fxmlFiles;
import DMCBM.StatBlocks.Builder.StatBuilder;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

public class StatController extends Controller {

    @FXML private WebView wView;
    @FXML private TextField name;
    @FXML private TextField trait_name;
    @FXML private TextArea trait_field;
    @FXML private TextField action_name;
    @FXML private TextArea action_field;

    private static String htm;
    private static Faker faker;
    private static StatBuilder statBlock;

    @Override
    protected void init() {
        StatController.htm = read("inlineTemp.txt");
        StatController.faker = new Faker();
        this.generate();
        this.update();
    }

    @FXML
    void generate_random(ActionEvent event) {
        this.generate();
        this.update();
    }

    @FXML
    void set_name(ActionEvent event) {
        StatController.statBlock.name(this.name.getText());
        this.name.setText("");
        this.name.requestFocus();
        this.update();
    }

    @FXML
    void add_action(ActionEvent event) {
        StatController.statBlock.addAction(this.action_name.getText(), this.action_field.getText());
        this.action_name.setText("");
        this.action_field.setText("");
        this.action_name.requestFocus();
        this.update();
    }

    @FXML
    void add_trait(ActionEvent event) {
        StatController.statBlock.addPassive(this.trait_name.getText(), this.trait_field.getText());
        this.trait_name.setText("");
        this.trait_field.setText("");
        this.trait_name.requestFocus();
        this.update();
    }

    @FXML
    void push(ActionEvent event) throws IOException {
        Stage tStage = new Stage();
        webPaneController cont = App.loadScene(fxmlFiles.webPaneController, tStage);
        tStage.show();
        cont.setHtml(htm.replace("LoremIpsum", StatController.statBlock.render()));
    }

    private void generate() {
        StatController.statBlock = new StatBuilder().max_random().name(StatController.faker.name().firstName());
    }

    private void update() {
        this.wView.getEngine().loadContent(htm.replace("LoremIpsum", StatController.statBlock.render()));
    }

    private String read(String file) {
        StringBuilder contentBuilder = new StringBuilder();
        InputStream in = this.getClass().getClassLoader().getResourceAsStream(file);
        try (BufferedReader br = new BufferedReader(new InputStreamReader(in))) {
            String sCurrentLine;
            while ((sCurrentLine = br.readLine()) != null) {
                contentBuilder.append(sCurrentLine).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return contentBuilder.toString();
    }
}