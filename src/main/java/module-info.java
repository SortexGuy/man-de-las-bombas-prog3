module bombfx {
    requires javafx.fxml;
    requires javafx.controls;
    requires javafx.base;
    requires javafx.graphics;

    opens bombfx.views to javafx.fxml;

    exports bombfx;
}
