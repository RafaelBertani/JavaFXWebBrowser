module com.example {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires javafx.graphics;

    opens com.example to javafx.fxml;
    exports com.example;
}
