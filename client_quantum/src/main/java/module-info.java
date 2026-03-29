module org.quantum_safe.client_quantum {

    requires javafx.controls;
    requires javafx.fxml;
    requires com.fasterxml.jackson.datatype.jsr310;
    requires com.fasterxml.jackson.databind;
    requires liboqs.java;
    requires java.net.http;
    requires jdk.jfr;
//    requires org.openquantumsafe;

    opens org.quantum_safe.client_quantum to javafx.fxml;
    opens org.quantum_safe.client_quantum.Controller to javafx.fxml;
    opens org.quantum_safe.client_quantum.Crypto to javafx.fxml;
    opens org.quantum_safe.client_quantum.DTO to com.fasterxml.jackson.databind;

    exports org.quantum_safe.client_quantum;
}