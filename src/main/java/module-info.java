module com.trees.binarytreeproject {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.apache.pdfbox;
    requires java.desktop;
    requires javafx.swing;


    opens com.trees.binarytreeproject to javafx.fxml;
    exports com.trees.binarytreeproject;
    exports com.trees.binarytreeproject.infrastructure.controller;
    opens com.trees.binarytreeproject.infrastructure.controller to javafx.fxml;
    exports com.trees.binarytreeproject.infrastructure.controller.arch;
    opens com.trees.binarytreeproject.infrastructure.controller.arch to javafx.fxml;
}