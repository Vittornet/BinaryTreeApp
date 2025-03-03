package com.trees.binarytreeproject.infrastructure;

import javafx.scene.Node;
import java.util.HashMap;
import java.util.Map;

public class NavigationService {
    private static final Map<String, Node> screens = new HashMap<>();


    public static void register(String screenName, Node screen) {
        screens.put(screenName, screen);
    }


    public static void show(String screenName) {
        screens.forEach((key, node) -> node.setVisible(false));
        Node screen = screens.get(screenName);
        if (screen != null) {
            screen.setVisible(true);
        }
    }
}
