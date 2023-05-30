package edu.virginia.cs.gui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;


public class ReviewApplication extends Application {  // Run this to start
    @Override
    public void start(Stage primaryStage) {
        BorderPane root = new BorderPane();
        Scene scene = new Scene(root, 800, 600); // scene
        
        LoginPane loginPane = new LoginPane(root);
        root.setCenter(loginPane);

        primaryStage.setTitle("Course Review Application");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
