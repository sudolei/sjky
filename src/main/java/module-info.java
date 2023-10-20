module com.qiang.century {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires mvel2;
    requires itextpdf;
    requires jai.imageio.core;
    opens org.isky to javafx.fxml;
    exports org.isky;
}