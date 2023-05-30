package edu.virginia.cs.gui;

import edu.virginia.cs.hw7.DatabaseHelper;
import edu.virginia.cs.hw7.DatabaseManager;
import edu.virginia.cs.hw7.Student;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class MainMenuPane extends VBox {
    DatabaseManager database = new DatabaseHelper();
    private Student student;

    public MainMenuPane(BorderPane root, Student s) {
        //this.student = database.getStudent(username);
        setSpacing(20);
        setAlignment(Pos.CENTER);

        Button submitReviewButton = new Button("Submit a review for a course");
        submitReviewButton.setOnAction(e -> {
            // Implement submit review logic
            SubmitReviewDialog submitReviewDialog = new SubmitReviewDialog(s);
            submitReviewDialog.showAndWait().ifPresent(result -> {
                if (result) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION, "Review submitted successfully");
                    alert.showAndWait();
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Error submitting review");
                    alert.showAndWait();
                }
            });
        });

        Button seeReviewsButton = new Button("See reviews for a course");
        seeReviewsButton.setOnAction(e -> {
            // Implement see reviews logic
            SeeReviewsDialog seeReviewsDialog = new SeeReviewsDialog();
            seeReviewsDialog.showAndWait();
        });

        Button logoutButton = new Button("Log-out");
        logoutButton.setOnAction(e -> {
            LoginPane loginPane = new LoginPane(root);
            root.setCenter(loginPane);
        });

        getChildren().addAll(submitReviewButton, seeReviewsButton, logoutButton);
    }
}
