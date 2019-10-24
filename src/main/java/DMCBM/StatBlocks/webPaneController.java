package DMCBM.StatBlocks;

import DMCBM.Controller;
import javafx.fxml.FXML;
import javafx.scene.web.WebView;

public class webPaneController extends Controller {

    @FXML private WebView wView;

    private static String html;

    @Override
    protected void init() {
        wView.getEngine().loadContent(webPaneController.html);
    }

    public void setHtml(String html) {
        webPaneController.html = html;
    }

}
