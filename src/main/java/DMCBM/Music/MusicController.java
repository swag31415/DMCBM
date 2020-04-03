package DMCBM.Music;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

import org.apache.commons.io.FileUtils;

import DMCBM.App;
import DMCBM.Controller;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;

public class MusicController extends Controller {

    @FXML
    private ComboBox<String> cBox;

    @FXML
    private MediaView mView;

    @FXML
    private Button cButton;

    private static WebClient wClient;
    private static HtmlPage page;
    private static Map<String, URL> links;
    private static boolean isPlaying;
    private static String lastSelection;

    @Override
    protected void init() {
        MusicController.wClient = new WebClient(BrowserVersion.FIREFOX_60);
        try {
            MusicController.page = wClient.getPage("https://tabletopaudio.com/");
        } catch (FailingHttpStatusCodeException | IOException e) {
            e.printStackTrace();
        }

        MusicController.links = new HashMap<String, URL>();
        MusicController.page.querySelectorAll(".saveTrack").forEach(t -> {
            try {
                URL link = MusicController.page.getFullyQualifiedUrl(((HtmlAnchor) t).getAttribute("href"));
                String name = link.toString().replace("https://tabletopaudio.com/download.php?downld_file=", "");
                links.put(name, link);
                this.cBox.getItems().add(name);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        });

        MusicController.isPlaying = false;
        MusicController.lastSelection = "";
    }

    @FXML
    void cButton_clicked(ActionEvent event) throws URISyntaxException {
        if (!MusicController.isPlaying) {
            String selection = this.cBox.getSelectionModel().getSelectedItem();
            URL dLink = MusicController.links.get(selection);
            if (lastSelection != selection) {
                MediaPlayer player = new MediaPlayer(new Media(downloadSelection(selection).toURI().toString()));
                mView.setMediaPlayer(player);
                System.out.println(player.getStopTime());
                // player.play();
            }
            
        } else {

        }

        MusicController.isPlaying = !MusicController.isPlaying;
    }

    private File downloadSelection(String selection) {
        File track = null;
        URL dLink = MusicController.links.get(selection);
        String dir = App.getResource(".").getPath();

        try {
            track = new File(dir + "temp.mp3");
            FileUtils.copyURLToFile(dLink, track);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return track;
    }

}
