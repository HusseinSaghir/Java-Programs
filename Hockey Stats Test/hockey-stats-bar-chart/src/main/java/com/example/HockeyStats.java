package com.example;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class HockeyStats extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/hockey-stats-view.fxml")); //Creates an FXMLLoader to load the UI layout from an FXML file
        Parent root = loader.load();
        
        Scene scene = new Scene(root, 800, 600); // Creates scene and Sets initial window dimensions to 800×600 pixels
        stage.setTitle("NHL Team Goals 2018-19"); // Sets the window title
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}