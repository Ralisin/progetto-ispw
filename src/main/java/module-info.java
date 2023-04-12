module it.ralisin.littlefarmers {
    requires javafx.controls;
    requires javafx.fxml;


    opens it.ralisin.littlefarmers to javafx.fxml;
    exports it.ralisin.littlefarmers;
}