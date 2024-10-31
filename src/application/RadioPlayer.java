package application;
//THIS IS AN ECLIPSE GITHUB TEST
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class RadioPlayer extends Application {

    private MediaPlayer mediaPlayer;
    private boolean isPlaying = false;
    
    private static final int NATIONALITYW = 80;
    private static final int NATIONALITYH = 20;
    private static final int BOXSIZE = 130;

    private StationData stationData;
    private List<Station> currentStations;

    private Label nationalityLabel;
    private Label stationLabel;
    private Label timeZoneTimeLabel;
    private Label timeLabel;
    private Button toggleButton;
    private Slider volumeSlider;

    public String nationalityTime = "Ireland";
    public int timeZoneModifier;
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Internet Radio Player");
        primaryStage.setResizable(false);
        stationData = new StationData();

        HBox nationalityBox = new HBox(10);
        nationalityBox.setPadding(new Insets(10));
        nationalityBox.setStyle("-fx-background-color: #e0e0e0;");

        String[] nationalities = {"Ireland", "England", "USA", "France", "Germany", "Japan"};
        Color[] colors = {Color.GREEN, Color.RED, Color.BLUE, Color.BLUE, Color.BLACK, Color.RED};

        for (int i = 0; i < nationalities.length; i++) {
            nationalityBox.getChildren().add(createNationalityRectangle(nationalities[i], colors[i]));
        }

        GridPane stationGrid = new GridPane();
        stationGrid.setPadding(new Insets(10));
        stationGrid.setHgap(70);
        stationGrid.setVgap(20);

        stationLabel = new Label("Select a station");
        toggleButton = new Button("Play");
        toggleButton.setOnAction(e -> togglePlayPause());

        volumeSlider = new Slider(0, 1, 0.5);
        volumeSlider.valueProperty().addListener((observable, oldValue, newValue) -> adjustVolume(newValue.doubleValue()));

        nationalityLabel = new Label("Nationality: Select Nationality");
        
        timeLabel = new Label("Time");
        timeLabel.setStyle("-fx-font-size: 12px;");
        
        timeZoneTimeLabel = new Label("Time2");
        timeZoneTimeLabel.setStyle("-fx-font-size: 12px;");

        HBox toolbar = new HBox(10, stationLabel, toggleButton, volumeSlider, timeLabel , timeZoneTimeLabel);
        toolbar.setPadding(new Insets(10));
        toolbar.setStyle("-fx-background-color: #f0f0f0;");

        BorderPane root = new BorderPane();
        root.setTop(nationalityBox);
        root.setCenter(stationGrid);
        root.setBottom(toolbar);

        Scene scene = new Scene(root, 555, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
        
        startClock();
        
        selectNationality("Ireland");
    }

    private Rectangle createNationalityRectangle(String name, Color color) {
        Rectangle rect = new Rectangle(NATIONALITYW, NATIONALITYH);
        rect.setFill(color);
        rect.setStroke(Color.BLACK);

        Label label = new Label(name);
        label.setTextFill(Color.WHITE);
        label.setStyle("-fx-font-size: 3px;");
        label.setTranslateY(10);
        
        rect.setOnMouseClicked(e -> selectNationality(name));

        HBox box = new HBox(rect, label);
        box.setSpacing(5);
        box.setPadding(new Insets(5));

        return rect;
    }

    private void selectNationality(String nationality) {
        currentStations = stationData.getStationsByCountry(nationality);
        nationalityLabel.setText("Stations for: " + nationality);
        selectTimeZone(nationality);
        displayStations((GridPane) ((BorderPane) ((Stage) toggleButton.getScene().getWindow()).getScene().getRoot()).getCenter(), currentStations);// javafx nonsense
    }
    
    private void selectTimeZone(String nationality) {
    	
    	nationalityTime = nationality; // this is for the clock that displays current location
    	
    	switch (nationality) {
        case "Ireland":
            timeZoneModifier =0;
            break;
        case "England":
            timeZoneModifier =0;
            break;
        case "USA":
        	timeZoneModifier = -4; //THIS IS NEW YORK TIME,-5/-6/-7 FOR LA
            break;
        case "France":
        	timeZoneModifier =1;
            break;
        case "Germany":
        	timeZoneModifier =1;
            break;
        case "Japan":
        	timeZoneModifier = 9;
            break;
        default:

            break;
    	}
    }

    private void displayStations(GridPane grid, List<Station> stations) {
        grid.getChildren().clear();
        int col = 0;
        int row = 0;
        for (Station station : stations) {
            createStationSquare(station, grid, row, col);
            col++;
            if (col >= 3) {
                col = 0;
                row++;
            }
        }
    }

    private void createStationSquare(Station station, GridPane grid, int row, int col) {
        Rectangle square = new Rectangle(BOXSIZE,BOXSIZE);
        square.setStroke(Color.BLACK);

        try {
            Image icon = new Image(getClass().getResourceAsStream(station.getIconPath()));
            square.setFill(new ImagePattern(icon));
        } catch (Exception e) {
            square.setFill(Color.LIGHTGRAY);
        }

        square.setOnMouseClicked(e -> switchStation(station));

        //Label label = new Label(station.getName());
       // label.setTextFill(Color.BLACK);
        //label.setStyle("-fx-font-size: 12px;");

        grid.add(square, col, row);
        //grid.add(label, col, row);
    }

    private void switchStation(Station station) {
        if (isPlaying) {
            stopRadio();
        }
        stationLabel.setText(station.getName());
        playRadio(station.getUrl());
    }

    private void togglePlayPause() {
        if (isPlaying) {
            stopRadio();
        } else if (mediaPlayer != null) {
            mediaPlayer.play();
            toggleButton.setText("Pause");
        }
    }

    private void playRadio(String url) {
        Media media = new Media(url);
        mediaPlayer = new MediaPlayer(media);
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
        
        LocalTime timeZoneOffset = now.plusHours(timeZoneModifier);
        String modifier; 
        String currentNationality = nationalityTime;
        
        if(timeZoneModifier>=0) {
        	modifier = "(+"+timeZoneModifier+")";
        }else {
        	modifier = "("+timeZoneModifier+")";
        }
        
        if(currentNationality.equals("USA")) {
        	currentNationality = "New York";
        }

        timeLabel.setText("Time: " + now.format(formatter));
        timeZoneTimeLabel.setText("Time in "+currentNationality +" "+ timeZoneOffset.format(formatter) +" "+ modifier);
    }
}
