package com.example;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage stage) {
        try {
            System.out.println("Loading FXML...");
            
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/main.fxml"));
            
            // Set a basic error handler
            loader.setControllerFactory(type -> { // Handles controller class instantiation with error handling
                try {
                    System.out.println("Creating controller: " + type.getName());
                    return type.newInstance();
                } catch (Exception e) {
                    System.err.println("Failed to create controller: " + e.getMessage());
                    return null;
                }
            });
            
            Parent root = loader.load();
            System.out.println("FXML loaded successfully");
            
            Scene scene = new Scene(root, 600, 400); //Creates a 600×400 pixel container for the UI. The main application window
            stage.setTitle("Random Cards");
            stage.setScene(scene);
            stage.show();
            
        } catch (Exception e) {
            System.err.println("Fatal error in application start:");
            e.printStackTrace();
            System.exit(1);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}