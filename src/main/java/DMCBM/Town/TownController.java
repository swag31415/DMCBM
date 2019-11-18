package DMCBM.Town;

import DMCBM.Controller;
import javafx.fxml.FXML;
import javafx.scene.web.WebView;

public class TownController extends Controller {

    @FXML
    private WebView wView;

    @Override
    protected void init() {
        this.wView.getEngine().load("http://fantasycities.watabou.ru/");
        System.out.println(wView.getEngine().getLocation());
    }

}
