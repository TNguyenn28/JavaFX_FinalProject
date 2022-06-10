module com.example.tnguyenpnv23a_finalproject {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;

    opens com.example.tnguyenpnv23a_finalproject to javafx.fxml;
    exports com.example.tnguyenpnv23a_finalproject;
}