module com.example.fxscenebuilder {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.fxscenebuilder to javafx.fxml;
    exports com.example.fxscenebuilder;
}