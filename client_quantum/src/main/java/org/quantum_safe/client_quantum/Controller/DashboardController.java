package org.quantum_safe.client_quantum.Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.quantum_safe.client_quantum.Crypto.MessageEncryptor;
import org.quantum_safe.client_quantum.DTO.EncryptedMessage;
import org.quantum_safe.client_quantum.Service.GetUserService;
import org.quantum_safe.client_quantum.Service.MessageService;
import org.quantum_safe.client_quantum.Session;

import java.util.List;

public class DashboardController
{
    @FXML private Button logoutButton;
    @FXML private Button viewInboxButton;
    @FXML private Button sendButton;
    @FXML private ComboBox recipientComboBox;
    @FXML private TextArea messageArea;
    private ObservableList<String> originalMails =
            FXCollections.observableArrayList();
    
    @FXML
    public void initialize()
    {
        loadUsers();
        enableFiltering();
        logoutButton.setOnAction(e->handleLogOut());
        viewInboxButton.setOnAction(e->handleViewInbox());
        sendButton.setOnAction(e->handleSendButton());
    }

    private void handleSendButton()  {


        String recipient = (String) recipientComboBox.getValue();
        String message = messageArea.getText();

        if (recipient == null || recipient.isEmpty()) {
            showAlert("Please select a recipient.");
            return;
        }

        if (message == null || message.isEmpty()) {
            showAlert("Message cannot be empty.");
            return;
        }

        // Disable button to prevent double click
        sendButton.setDisable(true);

        new Thread(() -> {
            try {

                // 1️⃣ Fetch receiver public key
                String publicKeyBase64 =
                        GetUserService.getPublicKey(recipient);

                // 2️⃣ Encrypt message
                EncryptedMessage encrypted =
                        MessageEncryptor.encrypt(publicKeyBase64, message,recipient);
System.out.println(encrypted.getReceiverMailId());
                System.out.println(encrypted.getSenderMailId());
                // 3️⃣ Send encrypted message to server
                 Integer status=MessageService.sendMessage(encrypted);

                if(status==200)


                { javafx.application.Platform.runLater(() -> {
                    showSuccess("Message sent successfully!");
                    messageArea.clear();
                    sendButton.setDisable(false);
                });}
                else
                {
                    javafx.application.Platform.runLater(() -> {
                        showAlert("Message Not Sent!");

                     });
                }

            } catch (Exception e) {
                e.printStackTrace();

                javafx.application.Platform.runLater(() -> {
                    showAlert("Failed to send message.");
                    sendButton.setDisable(false);
                });
            }
        }).start();
    }

    private void handleViewInbox()
    {
        try {
            Parent root = FXMLLoader.load(
                    getClass().getResource("/org/quantum_safe/client_quantum/ui/inbox.fxml"));

            Stage stage = (Stage) viewInboxButton.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Inbox");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void handleLogOut()
    {
        try {
            Session.clear(); // create this method if not present

            Parent root = FXMLLoader.load(
                    getClass().getResource("/org/quantum_safe/client_quantum/ui/login1.fxml"));

            Stage stage = (Stage) logoutButton.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Login");

        } catch (Exception e) {
            e.printStackTrace();
        }


    }
    private void loadUsers() {

        try {
            List<String> mails = GetUserService.getAllMailIds();
            originalMails.setAll(mails);
            recipientComboBox.setItems(originalMails);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showAlert(String msg) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setContentText(msg);
        alert.showAndWait();
    }
    private void showSuccess(String msg) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Success");
        alert.setContentText(msg);
        alert.showAndWait();
    }
    private void enableFiltering() {

        FilteredList<String> filteredList =
                new FilteredList<>(originalMails, p -> true);

        recipientComboBox.setItems(filteredList);

        recipientComboBox.setEditable(true);

        recipientComboBox.getEditor().textProperty().addListener(
                (obs, oldVal, newVal) -> {

                    filteredList.setPredicate(mail -> {

                        if (newVal == null || newVal.isEmpty()) {
                            return true;
                        }

                        return mail.toLowerCase()
                                .contains(newVal.toLowerCase());
                    });

                    recipientComboBox.show();
                });
    }


}
