package org.quantum_safe.client_quantum.Controller;

import javafx.fxml.FXML;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.quantum_safe.client_quantum.Crypto.QuantumKeyGenerator;
import org.quantum_safe.client_quantum.Crypto.QuantumKeyPair;
import org.quantum_safe.client_quantum.Service.AuthService;
import org.quantum_safe.client_quantum.Service.KeyStorageUtil;

public class RegisterController
{
    @FXML private TextField nameField;
    @FXML private TextField emailField;

    @FXML private PasswordField passwordField;
    @FXML private Button registerButton;
    @FXML private Hyperlink loginLink;
    @FXML
    public void initialize()
    {
        registerButton.setOnAction(e->handleRegister());
        loginLink.setOnAction(e-> {
            try {
                openLogin();
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });
    }
    private void handleRegister() {

        String name = nameField.getText().trim();
        String email = emailField.getText().trim();
        String password = passwordField.getText().trim();


        if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
            showStatus("All fields are required.");
            return;
        }

        try {
            // 1️⃣ Generate Quantum Key Pair
            QuantumKeyPair keyPair =
                    QuantumKeyGenerator.generateKeyPair();

            // 2️⃣ Save Private Key Locally
            KeyStorageUtil.savePrivateKey(email, keyPair.getPrivateKey());

            // 3️⃣ Send Public Key to Server
            AuthService.register(name, email, password, keyPair.getPublicKey());

            showSuccess("Registered Successfully!");
            openLogin();

        } catch (Exception e) {
            e.printStackTrace();
            showStatus("Registration Failed!");
        }
    }
    private void showStatus(String msg) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Failed");
        alert.setContentText(msg);
        alert.showAndWait();
    }
    private void showSuccess(String msg) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Success");
        alert.setContentText(msg);
        alert.showAndWait();
    }
    private void openLogin() throws Exception {

        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/org/quantum_safe/client_quantum/ui/login1.fxml")
        );

        Parent root = loader.load();

        Stage stage = (Stage) registerButton.getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.setFullScreen(true);
        stage.setTitle("Login");
    }
}
