module hu.petrik.stopper {
    requires javafx.controls;
    requires javafx.fxml;


    opens hu.petrik.stopper to javafx.fxml;
    exports hu.petrik.stopper;
}