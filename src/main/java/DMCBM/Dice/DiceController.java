package DMCBM.Dice;

import java.util.Random;

import javax.swing.JOptionPane;

import DMCBM.Controller;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;

public class DiceController extends Controller {

    @FXML
    private Label disp;

    @Override
    protected void init() {
        this.disp.requestFocus();
        this.disp.setText("xd");
    }

    @FXML
    void info_pressed(ActionEvent event) {
        JOptionPane.showMessageDialog(null, "press 1 for d100, 2 for d20, 4 for d4 and so on. 0 is d10");
        this.disp.requestFocus();
    }

    @FXML
    void key_pressed(KeyEvent event) {
        Random rand = new Random();
        switch(event.getCode()) {
            case DIGIT1:
                this.disp.setText("" + (rand.nextInt(100) + 1));
            break;
            case DIGIT2:
                this.disp.setText("" + (rand.nextInt(20) + 1));
            break;
            case DIGIT4:
                this.disp.setText("" + (rand.nextInt(4) + 1));
            break;
            case DIGIT6:
                this.disp.setText("" + (rand.nextInt(6) + 1));
            break;
            case DIGIT8:
                this.disp.setText("" + (rand.nextInt(8) + 1));
            break;
            case DIGIT0:
                this.disp.setText("" + (rand.nextInt(10) + 1));
            break;
            default:
                break;
        }
    }
}
