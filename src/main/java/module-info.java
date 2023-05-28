module it.ralisin.littlefarmers {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.logging;


    opens it.ralisin.littlefarmers to javafx.fxml;
    exports it.ralisin.littlefarmers;
    opens it.ralisin.littlefarmers.controller to javafx.fxml;
    exports it.ralisin.littlefarmers.controller;
}