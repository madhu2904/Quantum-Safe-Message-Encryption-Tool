package org.quantum_safe.client_quantum.Controller;

import javafx.collections.FXCollections;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import org.quantum_safe.client_quantum.DTO.MessagePreview;
import org.quantum_safe.client_quantum.DTO.ReadMessageResponse;
import org.quantum_safe.client_quantum.Service.InboxService;
import org.quantum_safe.client_quantum.Crypto.MessageDecryptor;

import java.util.List;

public class InboxController {

    @FXML private Button backButton;
    @FXML private ListView<MessagePreview> messageListView;
    @FXML private Label senderLabel;
    @FXML private Label timestampLabel;
    @FXML private Label messageIdLabel;
    @FXML private Label statusLabel;
    @FXML private TextArea contentArea;
    @FXML private Button alertButton;
    @FXML private ProgressIndicator loadingIndicator;

    private final InboxService inboxService = new InboxService();
    private final MessageDecryptor cryptoService = new MessageDecryptor();

    @FXML
    public void initialize() {

        setupCellFactory();
        loadInbox();

        messageListView.getSelectionModel()
                .selectedItemProperty()
                .addListener((obs, oldVal, selected) -> {
                    if (selected != null) {
                        loadAndDecrypt(selected);
                    }
                });

        backButton.setOnAction(e -> goBack());
    }


    private void loadInbox() {

        Task<List<MessagePreview>> task = new Task<>() {
            @Override
            protected List<MessagePreview> call() throws Exception {
                return inboxService.fetchInbox();
            }
        };

        task.setOnRunning(e -> loadingIndicator.setVisible(true));

        task.setOnSucceeded(e -> {
            loadingIndicator.setVisible(false);

            List<MessagePreview> previews = task.getValue();
            messageListView.setItems(
                    FXCollections.observableArrayList(previews)
            );
        });

        task.setOnFailed(e -> {
            loadingIndicator.setVisible(false);
            showError("Some Error in loading the messages, try logging in again.");
        });

        new Thread(task).start();
    }


    private void loadAndDecrypt(MessagePreview msg) {

        Task<String> task = new Task<>() {
            @Override
            protected String call() throws Exception {

                ReadMessageResponse response =
                        inboxService.readMessage(msg.getMessageId());

                return cryptoService.decrypt(response);
            }
        };

        task.setOnRunning(e -> {
            loadingIndicator.setVisible(true);
            contentArea.clear();
        });

        task.setOnSucceeded(e -> {
            loadingIndicator.setVisible(false);

            contentArea.setText(task.getValue());

            senderLabel.setText("From: " + msg.getSenderMail());
            messageIdLabel.setText("Message ID: " + msg.getMessageId());
            timestampLabel.setText((msg.getTimestamp()).toString());

            statusLabel.setText("Status: READ");

            msg.setIsRead(true);
            messageListView.refresh();
        });

        task.setOnFailed(e -> {
            loadingIndicator.setVisible(false);
//            showError(task.getException().getMessage());
            showError("Alert sent to sender for second read attempt.");
        });

        new Thread(task).start();
    }

    // ===========================
    // 🔹 PREVIEW STYLING
    // ===========================
    private void setupCellFactory() {

        messageListView.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(MessagePreview msg, boolean empty) {
                super.updateItem(msg, empty);

                if (empty || msg == null) {
                    setText(null);
                    setStyle("");
                } else {

                    String status = msg.getIsRead() ? "READ" : "UNREAD";

                    setText(
                            msg.getSenderMail() + "\n" +
                                    "Message ID: " + msg.getMessageId() + "\n" +
                                    msg.getTimestamp() + "\n" +
                                    "Status: " + status
                    );

                    if (msg.getIsRead()) {
                        // Light Red colour
                        setStyle("-fx-background-color: #ffe6e6;" +
                                "-fx-padding:10;");
                    } else {
                        //  Light Yellow colour
                        setStyle("-fx-background-color: #fff9c4;" +
                                "-fx-font-weight:bold;" +
                                "-fx-padding:10;");
                    }
                }
            }
        });
    }

    private void goBack() {
        try {
            Parent root = FXMLLoader.load(
                    getClass().getResource(
                            "/org/quantum_safe/client_quantum/ui/dashboard.fxml"));
            Stage stage = (Stage) backButton.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setFullScreen(true);
            stage.setTitle("Dashboard");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showError(String msg) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setContentText(msg);
        System.out.println(msg);
        alert.showAndWait();
    }
    private void showSuccess(String msg) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Success");
        alert.setContentText(msg);
        alert.showAndWait();
    }
}