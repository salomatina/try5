module ru.mephi {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.apache.poi.ooxml;
    requires commons.math3;
    requires java.desktop;

    opens ru.mephi to javafx.fxml;
    exports ru.mephi;
}