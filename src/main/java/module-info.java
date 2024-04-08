module com.example.mj_motors {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;
    requires javafx.graphics;
    requires java.sql;

    opens com.example.mj_motors to javafx.fxml;
    exports com.example.mj_motors;
}