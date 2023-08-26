module it.ralisin.littlefarmers {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.logging;
    requires java.sql;
    requires com.opencsv;


    opens it.ralisin.littlefarmers to javafx.fxml;
    exports it.ralisin.littlefarmers;
    exports it.ralisin.littlefarmers.controller.graphic_controller;
    opens it.ralisin.littlefarmers.controller.graphic_controller to javafx.fxml;
}