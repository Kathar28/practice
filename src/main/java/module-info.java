module com.app.formapplication {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;

    opens com.app.formapplication to javafx.fxml;
    exports com.app.formapplication;
    exports com.app.formapplication.documents;
    opens com.app.formapplication.documents to javafx.fxml;
}