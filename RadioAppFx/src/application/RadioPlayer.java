package application;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

/*TODO
 * MAKE THE TOP IMAGES THEIR RESPECTIVE FLAGS
 * ADD A CURRENT TIME AND A SELECTED COUNTRY TIME, FIGURE OUT WHO HAS DAYLIGHT SAVINGS
 * WAY OF GETTING CURRENTLY PLAYING METADATAS ?
 * 
 * 
 */


public class RadioPlayer extends Application {

    private MediaPlayer mediaPlayer;
    private boolean isPlaying = false;
    
    //----------------GUI Customization------------------------
    private static final int APPWIDTH = 800;
    private static final int APPHEIGHT = 500;
    private static final int STATIONSQUAREWIDTH=100;
    private static final int STATIONSQUAREGAPW=100;
    private static final int STATIONSQUAREGAPH=100;
    private static final int NATIONALITYW=100;
    private static final int NATIONALITYH=40;
    
    //--------------------------------------------------------------

    // nationality hashmaps, made up of name and url, add image later
    private final Map<String, String> irishStations = new HashMap<String, String>() {{
        put("RTE Radio 1	", "https://liveaudio.rte.ie/hls-radio/ieradio1/chunklist.m3u8");
        put("RTE Radio 2	", "https://liveaudio.rte.ie/hls-radio/ie2fm/chunklist.m3u8");
        put("RTE Lyric FM	", "https://liveaudio.rte.ie/hls-radio/lyric/chunklist.m3u8");
        put("Newstalk		", "https://www.goloudplayer.com/radio/newstalk"); // NOT WORKING
        put("Radio 1 Extra	", "https://22733.live.streamtheworld.com/RTE_RADIO_1_EXTRAAAC/HLS/e3f1be02-3bf3-4201-970e-6b3739c66bff/0/playlist.m3u8"); // NOT QWORKING
        put("Radio na Gaelta", "https://liveaudio.rte.ie/hls-radio/rnag/chunklist.m3u8");

    }};						//
    						//
    private final Map<String, String> britishStations = new HashMap<String, String>() {{
        put("BBC Radio 1	", "https://as-hls-ww-live.akamaized.net/pool_904/live/ww/bbc_radio_one/bbc_radio_one.isml/bbc_radio_one-audio%3d96000.norewind.m3u8");
        put("BBC Radio 2	", "https://as-hls-ww.live.cf.md.bbci.co.uk/pool_904/live/ww/bbc_radio_two/bbc_radio_two.isml/bbc_radio_two-audio%3d96000.norewind.m3u8");
        put("BBC Radio 3	", "https://as-hls-ww-live.akamaized.net/pool_904/live/ww/bbc_radio_three/bbc_radio_three.isml/bbc_radio_three-audio%3d96000.norewind.m3u8");
        put("BBC Radio 4	", "https://as-hls-ww-live.akamaized.net/pool_904/live/ww/bbc_radio_fourfm/bbc_radio_fourfm.isml/bbc_radio_fourfm-audio%3d96000.norewind.m3u8");
        put("Classic FM		", "https://classical.icecast.solhost.co.uk/classicfm"); //NOT WORKING
        put("Radio 1 Xtra	", "http://stream.live.vc.bbcmedia.co.uk/bbc_radio_one_xtra"); //NOT WORKING
    }};

    private final Map<String, String> usaStations = new HashMap<String, String>() {{
        put("NPR			", "https://npr-ice.streamguys1.com/live");
        put("KEXP			", "http://live-streaming.kexp.org/kexp/128k");
        put("WSB Radio		", "http://wsbradio.com/live");
        put("WNYC			", "https://www.wnyc.org/stream");
        put("KQED			", "https://www.kqed.org/radio/live");
        put("Radio Paradise	", "https://stream.radioparadise.com/mp3-192");
    }};						//
    						//
    private final Map<String, String> frenchStations = new HashMap<String, String>() {{
        put("France Inter	", "http://direct.franceinter.fr/live/franceinter-midfi.mp3");
        put("NRJ			", "http://stream.nrj.fr/nrj");
        put("Europe 1		", "http://europe1-vod.cdn.dvmr.fr/europe1.mp3");
        put("Radio France	", "http://www.radiofrance.fr/live");
        put("Fun Radio		", "http://www.funradio.fr/stream");
        put("RMC			", "http://rmc.bfmtv.com/live");
    }};						//
    						//	
    private final Map<String, String> germanStations = new HashMap<String, String>() {{
        put("Deutschlandfunk", "https://stream.dlf.de/dlf/streams/dlf.m3u");
        put("Radio Eins     ", "https://www.radioeins.de/live/radioeins.m3u");
        put("HR3            ", "https://www.hr3.de/streams/hr3.m3u");
        put("WDR 2			", "https://wdr2.de/live/wdr2.m3u");
        put("SWR3			", "https://www.swr3.de/streams/swr3.m3u");
        put("Antenne Bayern	", "https://www.antenne.de/live/antenne-bayern.m3u");
    }};                     //
    						//
    private final Map<String, String> japaneseStations = new HashMap<String, String>() {{
        put("NHK World Radio", "https://www3.nhk.or.jp/nhkworld/en/radio/");
        put("Tokyo FM		", "https://www.tokyofm.co.jp/");
        put("FM Yokohama	", "https://www.fmyokohama.co.jp/");
        put("J-Wave			", "http://www.j-wave.co.jp/");
        put("InterFM897		", "https://www.interfm.co.jp/");
        put("FM802			", "http://fm802.com/");
    }};
    
    private final Map<String, String> timeZones = new HashMap<String, String>() {{
        put("Ireland", "Europe/Dublin");
        put("UK", "Europe/London");
        put("USA", "America/New_York");
        put("France", "Europe/Paris");
        put("Germany", "Europe/Berlin");
        put("Japan", "Asia/Tokyo");
    }};
    

    // main station map, allows acess to current radios
    private Map<String, String> currentStations;

    private Label nationalityLabel;
    private Label stationLabel;
    private Label timeLabel;
    private Label timeZoneLabel;
    private Button toggleButton;
    private Slider volumeSlider;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Internet Radio Player");

        //this is the TOP BOX, where the nationalitys are stored
        HBox nationalityBox = new HBox(10);
        nationalityBox.setPadding(new Insets(10));
        nationalityBox.setStyle("-fx-background-color: #e0e0e0;");

        // each nationalitys label(name and color, image eventually)
        String[] nationalities = {"Ireland", "England", "USA", "France", "Germany", "Japan"};
        Color[] colors = {Color.GREEN, Color.RED, Color.BLUE, Color.BLUE, Color.BLACK, Color.RED};

        for (int i = 0; i < nationalities.length; i++) {
            nationalityBox.getChildren().add(createNationalityRectangle(nationalities[i], colors[i]));
        }

        // create grid for station squares
        GridPane stationGrid = new GridPane();
        stationGrid.setPadding(new Insets(10));
        stationGrid.setHgap(50);
        stationGrid.setVgap(10);

        //create the bottom toolbar , add a current time / time in SELECTED COUNTRY later
        stationLabel = new Label("Station: Select a station");//default
        toggleButton = new Button("Play");
        toggleButton.setOnAction(e -> togglePlayPause());

        volumeSlider = new Slider(0, 1, 0.5); // volume range from 0.0 to 1.0
        volumeSlider.setShowTickLabels(true);
        volumeSlider.setShowTickMarks(true);
        volumeSlider.setMajorTickUnit(0.25);
        volumeSlider.setBlockIncrement(0.1);
        volumeSlider.valueProperty().addListener((observable, oldValue, newValue) -> adjustVolume(newValue.doubleValue()));

        nationalityLabel = new Label("Nationality: Select Nationality");
        
        timeLabel = new Label("Current Time: ");
        timeLabel.setStyle("-fx-font-size: 14px;");

        
        timeZoneLabel = new Label("Time Zone: None");
        timeZoneLabel.setStyle("-fx-font-size: 14px;");
        
        
        // toolbar layout
        HBox toolbar = new HBox(10, stationLabel, toggleButton, volumeSlider,nationalityLabel,timeLabel);
        toolbar.setPadding(new Insets(10));
        toolbar.setStyle("-fx-background-color: #f0f0f0;"); //toolbar background color

        // main layout
        BorderPane root = new BorderPane();
        root.setTop(nationalityBox); //add the nationality bar
        root.setCenter(stationGrid);
        root.setBottom(toolbar);

        // app currently starts with irish radio open by default
        currentStations = irishStations;
        displayStations(stationGrid, currentStations);

        Scene scene = new Scene(root, 700, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
        
        startClock();
    }

    private Rectangle createNationalityRectangle(String name, Color color) {
        Rectangle rect = new Rectangle(NATIONALITYW,NATIONALITYH); // this is the nationality rectangle
        rect.setFill(color);
        rect.setStroke(Color.BLACK);

        Label label = new Label(name);
        label.setTextFill(Color.WHITE);
        label.setStyle("-fx-font-size: 14px;");
        label.setTranslateY(10); // center text vertically in the rectangle
        
        rect.setOnMouseClicked(e -> selectNationality(name));

        HBox box = new HBox(rect, label);
        box.setSpacing(5);
        box.setPadding(new Insets(5));

        return rect;
    }

    private void selectNationality(String nationality) { // this is the selector of which radio to show
        switch (nationality) {
            case "Ireland":
                currentStations = irishStations;
                break;
            case "England":
                currentStations = britishStations;
                break;
            case "USA":
                currentStations = usaStations;
                break;
            case "France":
                currentStations = frenchStations;
                break;
            case "Germany":
                currentStations = germanStations;
                break;
            case "Japan":
                currentStations = japaneseStations;
                break;
            default:
                currentStations = irishStations; // Default to Irish stations
                break;
        }
        nationalityLabel.setText("Stations for: " + nationality);
        displayStations((GridPane) ((BorderPane) ((Stage) toggleButton.getScene().getWindow()).getScene().getRoot()).getCenter(), currentStations);
    }

    private void displayStations(GridPane grid, Map<String, String> stations) {
        grid.getChildren().clear(); // clear funcutins
        int row = 0;
        int col = 0;

        // SQUARE CREATOR
        for (Entry<String, String> entry : stations.entrySet()) {
            createStationSquare(entry.getKey(), entry.getValue(), grid, row, col);
            col++;
            if (col >= 3) { // 3 columns for now
                col = 0;
                row++;
            }
        }
    }

    private void createStationSquare(String name, String url, GridPane grid, int row, int col) {
        Rectangle square = new Rectangle(100, 100);
        square.setStroke(Color.BLACK);

        //if the image cant load, make it this light blue square
        square.setFill(Color.LIGHTBLUE);

        square.setOnMouseClicked(e -> switchStation(name, url));

        Label label = new Label(name);
        label.setTextFill(Color.BLACK);
        label.setStyle("-fx-font-size: 14px;");
        label.setTranslateY(60);

        grid.add(square, col, row);
        grid.add(label, col, row);
    }

    private void switchStation(String name, String url) {
        if (isPlaying) {
            stopRadio();
        }
        stationLabel.setText("Station: " + name);
        playRadio(url);
    }

    private void togglePlayPause() {
        if (isPlaying) {
            stopRadio();
        } else {
            playRadio(mediaPlayer.getMedia().getSource());
        }
    }

    private void playRadio(String url) {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
        }
        Media media = new Media(url);
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setOnEndOfMedia(() -> {
            mediaPlayer.seek(mediaPlayer.getStartTime());
            mediaPlayer.play();
        });
        mediaPlayer.play();
        isPlaying = true;
        toggleButton.setText("Pause");
    }

    private void stopRadio() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            isPlaying = false;
            toggleButton.setText("Play");
        }
    }

    private void adjustVolume(double value) {
        if (mediaPlayer != null) {
            mediaPlayer.setVolume(value);
        }
    }
    
    private void startClock() {
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), e -> updateTime()));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play(); 
    }

    private void updateTime() {
        LocalTime now = LocalTime.now(); 
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        timeLabel.setText("Current Time: " + now.format(formatter)); //



    }
}
