package edu.virginia.cs.gui;

import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import edu.virginia.cs.hw7.Review;
import edu.virginia.cs.hw7.Course;
import edu.virginia.cs.hw7.DatabaseHelper;
import edu.virginia.cs.hw7.DatabaseManager;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.hibernate.boot.model.relational.Database;

public class SeeReviewsDialog extends Dialog<Void> {
    private DatabaseManager database = new DatabaseHelper();

    public SeeReviewsDialog() {
        setTitle("See Reviews");

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(12);

        TextField courseField = new TextField();
        grid.add(new Label("Course (e.g., CS 3140):"), 0, 0);
        grid.add(courseField, 1, 0);

        getDialogPane().setContent(grid);

        ButtonType seeReviewsButtonType = new ButtonType("See Reviews", ButtonBar.ButtonData.OK_DONE);
        getDialogPane().getButtonTypes().addAll(seeReviewsButtonType, ButtonType.CANCEL);

        setResultConverter(dialogButton -> {
            if (dialogButton == seeReviewsButtonType) {
                String course = courseField.getText();
                Pattern pattern = Pattern.compile("([A-Z]{1,4}) ([0-9]{4})");
                Matcher matcher = pattern.matcher(course);
                if (!matcher.matches()) {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Invalid course entered");
                    alert.showAndWait();
                }

                String subject = matcher.group(1);
                int catalogNumber = Integer.parseInt(matcher.group(2));


                if(!database.checkForCourse(catalogNumber, course)) {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Course does not exist");
                    alert.showAndWait();
                }

                Course courseObj = database.getCourse(catalogNumber, subject);
                
                List<Review> reviews = database.getCourseReviews(courseObj);

                if (reviews == null || reviews.isEmpty()) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION, "No reviews found for this course");
                    alert.showAndWait();
                } else {
                    StringBuilder reviewText = new StringBuilder();
                    double totalRating = 0;

                    for (Review review : reviews) {
                        reviewText.append(review.getReview()).append("\n");
                        totalRating += review.getRating();
                    }

                    double averageRating = totalRating / reviews.size();
                    reviewText.append(String.format("Course Average: %.2f/5", averageRating));

                    Alert alert = new Alert(Alert.AlertType.INFORMATION, reviewText.toString());
                    alert.setHeaderText("Reviews for " + course);
                    alert.showAndWait();
                }
            }
            return null;
        });
    }
}

