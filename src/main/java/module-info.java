module bombfx {
    requires javafx.fxml;
    requires javafx.controls;

    opens bombfx.views to javafx.fxml;

    exports bombfx;
}
