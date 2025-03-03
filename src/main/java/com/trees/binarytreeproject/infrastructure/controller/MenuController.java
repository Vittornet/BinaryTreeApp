package com.trees.binarytreeproject.infrastructure.controller;

import com.trees.binarytreeproject.infrastructure.NavigationService;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import java.net.URL;
import java.util.ResourceBundle;

public class MenuController implements Initializable {

    public Button btnVisualization;
    public Button btnInfo;
    public Button btnTests;
    @FXML
    private AnchorPane menuRoot;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        NavigationService.register("menu", menuRoot);
        NavigationService.show("menu");
    }

    @FXML
    private void onVisualizationClicked() {
        NavigationService.show("visualization");
    }

    @FXML
    private void onInfoClicked() {
        NavigationService.show("info");
    }

    @FXML
    private void onTestsClicked() {
        NavigationService.show("tests");
    }
}