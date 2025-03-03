package com.trees.binarytreeproject.infrastructure.render;

import javafx.scene.canvas.GraphicsContext;
import java.util.List;

public interface TreeRenderer {

    void drawTree(GraphicsContext gc, double canvasWidth, double canvasHeight);


    void insert(int value);


    void delete(int value);


    List<Integer> inOrderTraversal();


    void clearTree();

    double getDrawingWidth();
    double getDrawingHeight();
}