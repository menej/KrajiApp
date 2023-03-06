module fri.project.kraji {
    requires javafx.controls;
    requires javafx.fxml;


    opens fri.project.kraji to javafx.fxml;
    exports fri.project.kraji;
}