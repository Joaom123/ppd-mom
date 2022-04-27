module ifce.ppd.mom.ppdmom {
    requires javafx.controls;
    requires javafx.fxml;


    opens ifce.ppd.mom.ppdmom to javafx.fxml;
    exports ifce.ppd.mom.ppdmom;
}