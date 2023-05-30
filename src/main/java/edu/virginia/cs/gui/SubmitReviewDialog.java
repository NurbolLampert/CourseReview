package edu.virginia.cs.gui;

import edu.virginia.cs.hw7.Course;
import edu.virginia.cs.hw7.Student;
import edu.virginia.cs.hw7.DatabaseHelper;
import edu.virginia.cs.hw7.DatabaseManager;
import edu.virginia.cs.hw7.Review;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SubmitReviewDialog extends Dialog<Boolean> {
    private DatabaseManager database = new DatabaseHelper();
    private Student student;

    public SubmitReviewDialog(Student s) {
        this.student = s;
        setTitle("Submit a Review");

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(12);

        TextField courseField = new TextField();
        grid.add(new Label("Course (e.g., CS 3140):"), 0, 0);
        grid.add(courseField, 1, 0);

        TextField messageField = new TextField();
        grid.add(new Label("Message:"), 0, 1);
        grid.add(messageField, 1, 1);

        TextField ratingField = new TextField();
        grid.add(new Label("Rating (1-5):"), 0, 2);
        grid.add(ratingField, 1, 2);

        getDialogPane().setContent(grid);

        ButtonType submitButtonType = new ButtonType("Submit", ButtonBar.ButtonData.OK_DONE);
        getDialogPane().getButtonTypes().addAll(submitButtonType, ButtonType.CANCEL);

        setResultConverter(dialogButton -> {
            if (dialogButton == submitButtonType) {
                String course = courseField.getText();
                String message = messageField.getText();
                String ratingString = ratingField.getText();

                Pattern pattern = Pattern.compile("([A-Z]{1,4}) ([0-9]{4})");
                Matcher matcher = pattern.matcher(course);
                if (!matcher.matches()) {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Invalid course entered");
                    alert.showAndWait();
                    return false;
                }

                String subject = matcher.group(1);
                int catalogNumber = Integer.parseInt(matcher.group(2));
                Course courseObj = database.getCourse(catalogNumber, subject);

                if (courseObj == null) {
                    courseObj = database.addCourse(catalogNumber, subject);
                }

                if (database.checkForReview(student, courseObj)) {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "You have already reviewed this course");
                    alert.showAndWait();
                    return false;
                }

                int rating;
                try {
                    rating = Integer.parseInt(ratingString);
                } catch (NumberFormatException e) {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Invalid rating");
                    alert.showAndWait();
                    return false;
                }

                if (rating < 1 || rating > 5) {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Rating must be between 1 and 5");
                    alert.showAndWait();
                    return false;
                }

                /*Review review = new Review(student, courseObj, message, rating);
                boolean submitReviewSuccessful = false;
                */
                Review addR = new Review();
                addR.setReviewer(student);
                addR.setReviewedCourse(courseObj);
                addR.setReview(message);
                addR.setRating(rating);
                Review check = database.addReview2(addR);
                boolean submitReviewSuccessful = database.checkForReview(check.getReviewer(), check.getReviewedCourse());


                /*if(review.equals(database.addReview(rating, message, courseObj, student))){
                    submitReviewSuccessful = true;
                }*/

                if (!submitReviewSuccessful) {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Error submitting review or course already reviewed");
                    alert.showAndWait();
                }
                return submitReviewSuccessful;
            }
            return false;
        });
    }
}
