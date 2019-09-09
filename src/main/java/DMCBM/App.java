package DMCBM;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class App extends Application {

    @FXML
    private TextField input_one;

    @FXML
    private TextField input_two;

    @FXML
    private TextField input_three;

    @FXML
    private TextField input_four;

    @FXML
    private Label output;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void init() throws Exception {

    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getClassLoader().getResource("gui.fxml"));
        AnchorPane pane = loader.<AnchorPane>load();

        primaryStage.setScene(new Scene(pane));
        primaryStage.show();
    }

    public double calculate(int base, int roll, int diff) {
        return calculate(base, roll, diff, 20);
    }

    public double calculate(int base, int roll, int diff, int range) {
        double val = 2 * (Math.random() - 0.5) * range;
        return (int) mGaussian(val, 0, roll, Math.max((base + roll) - diff, 0));
    }

    public double mGaussian(double x, double center, double deviation, double amplitude) {
        return amplitude * pdf((x - center) / deviation);
    }

    public double pdf(double x) {
        return Math.exp(-(x)*(x) / 2);
    }

    @FXML
    void input(ActionEvent event) {
        if (areInputsFull()) {
            String out;
            try {
                out = String.valueOf(calculate(Integer.parseInt(input_one.getText()), Integer.parseInt(input_two.getText()), Integer.parseInt(input_three.getText())));
            } catch (NumberFormatException e) {
                out = null;
            }

            clearInputs();
            input_one.requestFocus();
            output.setText((out == null) ? "NaN": out);
        } else {
            getNextEmpty().requestFocus();
        }
    }

    public boolean areInputsFull() {
        return (!input_one.getText().equals("")) && (!input_two.getText().equals("")) && (!input_three.getText().equals(""));
    }

    public TextField getNextEmpty() {
        if (input_one.getText().equals("")) {
            return input_one;
        }
        if (input_two.getText().equals("")) {
            return input_two;
        }
        if (input_three.getText().equals("")) {
            return input_three;
        }
        if (input_four.getText().equals("")) {
            return input_four;
        }
        return null;
    }

    public void clearInputs() {
        input_one.setText("");
        input_two.setText("");
        input_three.setText("");
        input_four.setText("");
    }
}
