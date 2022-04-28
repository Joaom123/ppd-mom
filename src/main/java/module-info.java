module ifce.ppd.mom.ppdmom {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.naming;
    requires activemq.core;
    requires javax.jms.api;


    opens ifce.ppd.mom.ppdmom to javafx.fxml;
    exports ifce.ppd.mom.ppdmom;
    exports ifce.ppd.mom.ppdmom.controllers;
    opens ifce.ppd.mom.ppdmom.controllers to javafx.fxml;
}