package com.example;

import java.io.IOException;
import java.net.URL;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/*
 * This is the main entry point for all JavaFX applications
   It's called after the JavaFX runtime is initialized
 * 
 * 
 */

public class ConverterApp extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        // Use getResource with the correct path
        URL fxmlLocation = getClass().getResource("/com/example/converter-view.fxml");
        if (fxmlLocation == null) {
            System.err.println("Error: FXML file not found");  // Checks if the FXML file was found. Prints error message and exits if not found
            return;
        }
        
        FXMLLoader loader = new FXMLLoader(fxmlLocation); // Creates an FXMLLoader with the FXML file location
        Parent root = loader.load();
        
        Scene scene = new Scene(root, 400, 300);
        stage.setTitle("Unit Converter");
        stage.setScene(scene);
        stage.show();

        /*
         * Creates a Scene (container for all UI content) with:
           The loaded FXML as root node
           Initial dimensions of 400×300 pixels
           Sets the window title to "Unit Converter"
           Assigns the scene to the primary stage
           Makes the window visible with show()
         */
    }

    public static void main(String[] args) {
        launch(args);
    }
}