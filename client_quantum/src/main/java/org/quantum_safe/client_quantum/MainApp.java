package org.quantum_safe.client_quantum;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainApp extends Application {

    @Override
    public void start(Stage stage) throws Exception {

        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/org/quantum_safe/client_quantum/ui/login1.fxml")
        );

        Scene scene = new Scene(loader.load());

        stage.setTitle("Quantum Safe Messenger");
        stage.setScene(scene);

        stage.setFullScreen(true);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}