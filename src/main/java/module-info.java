module ifce.ppd.mom.ppdmom {
    requires javafx.controls;
    requires javafx.fxml;
    requires activemq.core;
    requires javax.jms.api;
    requires java.naming;


    opens ifce.ppd.mom.ppdmom to javafx.fxml;
    exports ifce.ppd.mom.ppdmom;
    exports ifce.ppd.mom.ppdmom.server;
    opens ifce.ppd.mom.ppdmom.server to javafx.fxml;
    exports ifce.ppd.mom.ppdmom.controllers;
    opens ifce.ppd.mom.ppdmom.controllers to javafx.fxml;
    exports ifce.ppd.mom.ppdmom.server.legado;
    opens ifce.ppd.mom.ppdmom.server.legado to javafx.fxml;
}