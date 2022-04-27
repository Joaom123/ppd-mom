module ifce.ppd.mom.ppdmom {
    requires javafx.controls;
    requires javafx.fxml;
    requires activemq.core;
    requires javax.jms.api;
    requires java.naming;


    opens ifce.ppd.mom.ppdmom to javafx.fxml;
    exports ifce.ppd.mom.ppdmom;
    exports ifce.ppd.mom.ppdmom.subpub;
    opens ifce.ppd.mom.ppdmom.subpub to javafx.fxml;
}