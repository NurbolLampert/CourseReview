package edu.virginia.cs.gui;

import edu.virginia.cs.hw7.DatabaseHelper;
import edu.virginia.cs.hw7.DatabaseManager;
import edu.virginia.cs.hw7.Student;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class LoginPane extends VBox {
    private DatabaseManager database = new DatabaseHelper();

    public LoginPane(BorderPane root) {
        setSpacing(20);
        setAlignment(Pos.CENTER);

        Text title = new Text("Course Review Application");
        title.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        getChildren().add(title);

        GridPane loginGrid = new GridPane();
        loginGrid.setHgap(10);
        loginGrid.setVgap(12);
        loginGrid.setAlignment(Pos.CENTER);

        Label usernameLabel = new Label("Username:");
        loginGrid.add(usernameLabel, 0, 1);

        TextField usernameField = new TextField();
        loginGrid.add(usernameField, 1, 1);

        Label passwordLabel = new Label("Password:");
        loginGrid.add(passwordLabel, 0, 2);

        PasswordField passwordField = new PasswordField();
        loginGrid.add(passwordField, 1, 2);

        Button loginButton = new Button("Login");
        loginGrid.add(loginButton, 1, 3);
        loginButton.setOnAction(e -> {
            String username = usernameField.getText();
            String password = passwordField.getText();
	//Need actually implemented DatabaseHelper for this
            boolean loginSuccessful = database.checkForStudent(username) && database.checkPassword(password, database.getStudentFromUsername(username));
            Student s = database.getStudentFromUsername(username);
            if (loginSuccessful) {
                MainMenuPane mainMenuPane = new MainMenuPane(root, s);
                root.setCenter(mainMenuPane);
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Invalid username or password");
                alert.showAndWait();
            }
        });

        getChildren().add(loginGrid);

        Button newUserButton = new Button("Sign Up");
        newUserButton.setOnAction(e -> {
            CreateUserDialog createUserDialog = new CreateUserDialog();
            createUserDialog.showAndWait().ifPresent(result -> {
                if (result) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION, "User created successfully");
                    alert.showAndWait();
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Error creating user");
                    alert.showAndWait();
                }
            });
        });

        getChildren().add(newUserButton);
    }
}
