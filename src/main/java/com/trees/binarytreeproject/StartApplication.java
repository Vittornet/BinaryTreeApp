package com.trees.binarytreeproject;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class StartApplication extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("main_start.fxml")));
        Scene scene = new Scene(root,  800, 600);
        root.getStylesheets().add(Objects.requireNonNull(getClass().getResource("css/app.css")).toExternalForm());
        primaryStage.setTitle("Дерева");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
