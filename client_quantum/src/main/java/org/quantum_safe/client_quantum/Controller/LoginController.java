package org.quantum_safe.client_quantum.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import org.quantum_safe.client_quantum.Service.AuthService;
import org.quantum_safe.client_quantum.Session;

public class LoginController {

    @FXML private TextField emailField;
    @FXML private PasswordField passwordField;
    @FXML private Button loginButton;
    @FXML private Hyperlink registerLink;

    @FXML
    public void initialize() {
        loginButton.setOnAction(e -> handleLogin());
        registerLink.setOnAction(e -> openRegister());
    }

    private void handleLogin() {

        String email = emailField.getText().trim();
        String password = passwordField.getText().trim();

        if (email.isEmpty() || password.isEmpty()) {
            showError("Fields cannot be empty.");
            return;
        }

        try {
            // Call service
            String userId = AuthService.login(email, password);

            if (userId != null) {
                // Save userId globally
                Session.setUserId(userId);
                Session.setUserEmail(email);
                // Navigate to dashboard
                openDashboard();

            } else {
                showError("Wrong credentials!");
                clearFields();
            }

        } catch (Exception e) {
//            showError("Server error!");
            e.printStackTrace();
        }
    }

    private void clearFields() {
        emailField.clear();
        passwordField.clear();
    }

    private void showError(String msg) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Login Failed");
        alert.setContentText(msg);
        alert.showAndWait();
    }

    private void openDashboard() throws Exception {
        Parent root = FXMLLoader.load(getClass()
                .getResource("/org/quantum_safe/client_quantum/ui/dashboard.fxml"));

        Stage stage = (Stage) loginButton.getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.setTitle("Dashboard");
    }
    private void openRegister() {

        try {
            Parent root = FXMLLoader.load(getClass()
                    .getResource("/org/quantum_safe/client_quantum/ui/register.fxml"));

            Stage stage = (Stage) loginButton.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setFullScreen(true);
            stage.setTitle("Register");

        } catch (Exception e) {
            e.printStackTrace();
            showError("Unable to open Register page.");
        }
    }
}