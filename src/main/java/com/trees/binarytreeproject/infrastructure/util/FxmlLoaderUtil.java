package com.trees.binarytreeproject.infrastructure.util;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;

public class FxmlLoaderUtil {

    private static final String BASE_PATH = "/com/trees/binarytreeproject/";

    public static Parent loadFXML(String fxmlFileName) {
        URL resource = FxmlLoaderUtil.class.getResource(BASE_PATH + fxmlFileName);
        try {
            return FXMLLoader.load(Objects.requireNonNull(resource, "FXML file not found: " + fxmlFileName));
        } catch (IOException e) {
            throw new RuntimeException("Не вдалося завантажити FXML файл: " + fxmlFileName, e);
        }
    }
}
