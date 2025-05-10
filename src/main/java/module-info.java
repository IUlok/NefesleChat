module com.example.NefesleChat {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;
    requires static lombok;
    requires com.google.gson;
    requires java.net.http;
    requires java.desktop;
    requires java.logging;
    requires com.fasterxml.jackson.annotation;

    opens com.example.NefesleChat to javafx.fxml;
    exports com.example.NefesleChat;
    opens com.example.NefesleChat.entity to com.google.gson;
}