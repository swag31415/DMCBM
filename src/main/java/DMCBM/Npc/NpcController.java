package DMCBM.Npc;

import java.io.IOException;
import java.net.MalformedURLException;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

import DMCBM.Controller;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

public class NpcController extends Controller {

    @FXML private TextArea disp;
    private static WebClient wClient;

	@Override
	protected void init() {
        NpcController.wClient = new WebClient(BrowserVersion.CHROME);
        this.generate(null);
	}

    @FXML
    void generate(ActionEvent event) {
        try {
            this.disp.setText(this.getNpcData());
		} catch (FailingHttpStatusCodeException | IOException e) {
			e.printStackTrace();
        }
    }

    private String getNpcData() throws FailingHttpStatusCodeException, MalformedURLException, IOException {
        HtmlPage page = NpcController.wClient.getPage("http://www.npcgenerator.com/");
        return String.join("\n\n", page.querySelector("div#downloadData").querySelectorAll(".panel").stream().map(d -> d.asText()).toArray(String[]::new));
    }

}
