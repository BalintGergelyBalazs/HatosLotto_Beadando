module com.example.hatoslotto {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    requires okhttp3;
    requires com.google.gson;


    opens com.example.hatoslotto to javafx.fxml;
    exports com.example.hatoslotto;
}