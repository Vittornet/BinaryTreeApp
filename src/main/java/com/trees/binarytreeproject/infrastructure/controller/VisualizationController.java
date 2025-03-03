package com.trees.binarytreeproject.infrastructure.controller;

import com.trees.binarytreeproject.domain.tree.BinaryTree;
import com.trees.binarytreeproject.domain.tree.RedBlackTree;
import com.trees.binarytreeproject.infrastructure.NavigationService;
import com.trees.binarytreeproject.infrastructure.render.BinaryTreeRenderer;
import com.trees.binarytreeproject.infrastructure.render.RedBlackTreeRenderer;
import com.trees.binarytreeproject.infrastructure.render.TreeRenderer;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class VisualizationController implements Initializable {
    private final double DEFAULT_CANVAS_WIDTH = 570;
    private final double DEFAULT_CANVAS_HEIGHT = 580;
    @FXML
    private BorderPane visualizationRoot;
    @FXML
    private Canvas treeCanvas;
    @FXML
    private TextField numberInput;
    @FXML
    private ComboBox<String> treeTypeComboBox;
    @FXML
    private Pane canvasPane;
    private TreeRenderer currentRenderer;

    private BinaryTree binaryTree;
    private RedBlackTree redBlackTree;


    private enum TreeType {BINARY, RED_BLACK}

    private TreeType currentTreeType = TreeType.BINARY;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        NavigationService.register("visualization", visualizationRoot);

        treeCanvas.setWidth(DEFAULT_CANVAS_WIDTH);
        treeCanvas.setHeight(DEFAULT_CANVAS_HEIGHT);
        canvasPane.setPrefWidth(DEFAULT_CANVAS_WIDTH);
        canvasPane.setPrefHeight(DEFAULT_CANVAS_HEIGHT);


        treeTypeComboBox.setItems(FXCollections.observableArrayList("Binary Tree", "Red-Black Tree"));
        treeTypeComboBox.getSelectionModel().selectFirst();


        binaryTree = new BinaryTree();
        redBlackTree = new RedBlackTree();

        currentTreeType = TreeType.BINARY;
        binaryTree = new BinaryTree();
        currentRenderer = new BinaryTreeRenderer(binaryTree);
        drawTree();

        treeTypeComboBox.setOnAction(e -> {
            String selected = treeTypeComboBox.getValue();
            if (selected != null) {
                switch (selected) {
                    case "Binary Tree":
                        currentTreeType = TreeType.BINARY;
                        binaryTree = new BinaryTree();
                        currentRenderer = new BinaryTreeRenderer(binaryTree);
                        break;
                    case "Red-Black Tree":
                        currentTreeType = TreeType.RED_BLACK;
                        redBlackTree = new RedBlackTree();
                        currentRenderer = new RedBlackTreeRenderer(redBlackTree);
                        break;
                }
                drawTree();
            }
        });


        numberInput.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                handleAdd();
            }
        });
    }

    @FXML
    private void handleClearTree() {
        if (currentRenderer != null) {
            treeCanvas.setWidth(DEFAULT_CANVAS_WIDTH);
            treeCanvas.setHeight(DEFAULT_CANVAS_HEIGHT);
            canvasPane.setPrefWidth(DEFAULT_CANVAS_WIDTH);
            canvasPane.setPrefHeight(DEFAULT_CANVAS_HEIGHT);
            currentRenderer.clearTree();
            drawTree();
        }
    }

    @FXML
    private void handleAdd() {
        try {
            int value = Integer.parseInt(numberInput.getText());
            currentRenderer.insert(value);
            drawTree();
            numberInput.clear();
        } catch (NumberFormatException e) {
            showAlert("Invalid Input", "Please enter a valid integer.");
        } catch (UnsupportedOperationException e) {
            showAlert("Not Available", e.getMessage());
        }
    }

    @FXML
    private void handleDelete() {
        try {
            int value = Integer.parseInt(numberInput.getText());
            currentRenderer.delete(value);
            drawTree();
            numberInput.clear();
        } catch (NumberFormatException e) {
            showAlert("Invalid Input", "Please enter a valid integer.");
        } catch (UnsupportedOperationException e) {
            showAlert("Not Available", e.getMessage());
        }
    }

    @FXML
    private void handleOutputSorted() {
        try {
            var sortedList = currentRenderer.inOrderTraversal();
            try (BufferedWriter writer = new BufferedWriter(new FileWriter("sorted_tree.txt"))) {
                for (Integer num : sortedList) {
                    writer.write(num.toString());
                    writer.newLine();
                }
                showAlert("Success", "Sorted elements written to sorted_tree.txt");
            } catch (IOException e) {
                showAlert("Error", "File write error: " + e.getMessage());
            }
        } catch (UnsupportedOperationException e) {
            showAlert("Not Available", e.getMessage());
        }
    }

    @FXML
    private void backToMenu() {
        NavigationService.show("menu");
    }

    private void drawTree() {

        double currentWidth = treeCanvas.getWidth();
        double currentHeight = treeCanvas.getHeight();

        GraphicsContext gc = treeCanvas.getGraphicsContext2D();
        currentRenderer.drawTree(gc, currentWidth, currentHeight);

        double requiredWidth = currentRenderer.getDrawingWidth();
        double requiredHeight = currentRenderer.getDrawingHeight();

        if (requiredWidth > currentWidth || requiredHeight > currentHeight) {
            treeCanvas.setWidth(requiredWidth);
            treeCanvas.setHeight(requiredHeight);
            canvasPane.setPrefWidth(requiredWidth);
            canvasPane.setPrefHeight(requiredHeight);

            gc = treeCanvas.getGraphicsContext2D();
            currentRenderer.drawTree(gc, requiredWidth, requiredHeight);
        }
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
