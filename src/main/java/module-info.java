module it.ralisin.littlefarmers {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.logging;
    requires java.sql;


    opens it.ralisin.littlefarmers to javafx.fxml;
    exports it.ralisin.littlefarmers;
    exports it.ralisin.littlefarmers.controller.GraphicController;
    opens it.ralisin.littlefarmers.controller.GraphicController to javafx.fxml;
}