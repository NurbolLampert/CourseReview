package edu.virginia.cs.gui;

import edu.virginia.cs.hw7.DatabaseHelper;
import edu.virginia.cs.hw7.DatabaseManager;
import edu.virginia.cs.hw7.Student;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

public class CreateUserDialog extends Dialog<Boolean> {
    private DatabaseManager database = new DatabaseHelper();
    public CreateUserDialog() {
        setTitle("Create a new user");

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(12);

        TextField usernameField = new TextField();
        grid.add(new Label("Username:"), 0, 0);
        grid.add(usernameField, 1, 0);

        PasswordField passwordField = new PasswordField();
        grid.add(new Label("Password:"), 0, 1);
        grid.add(passwordField, 1, 1);

        PasswordField confirmPasswordField = new PasswordField();
        grid.add(new Label("Confirm password:"), 0, 2);
        grid.add(confirmPasswordField, 1, 2);

        getDialogPane().setContent(grid);

        ButtonType createButtonType = new ButtonType("Create", ButtonBar.ButtonData.OK_DONE);
        getDialogPane().getButtonTypes().addAll(createButtonType, ButtonType.CANCEL);

        setResultConverter(dialogButton -> {
            if (dialogButton == createButtonType) {
                String username = usernameField.getText();
                String password = passwordField.getText();
                String confirmPassword = confirmPasswordField.getText();
                boolean userExists = database.checkForStudent(username);
                if (userExists) {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Username already exists");
                    alert.showAndWait();
                    return false;
                }

                //Change DatabaseHelper
                if (password.equals(confirmPassword)) {
                    database.addStudent(username, password);
                    boolean createUserSuccessful = database.checkForStudent(username);
                    return createUserSuccessful;
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Passwords do not match");
                    alert.showAndWait();
                    return false;
                }
            }
            return false;
        });
    }
}

