package com.trees.binarytreeproject.infrastructure.render;

import com.trees.binarytreeproject.domain.tree.BinaryTree;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.List;

public class BinaryTreeRenderer implements TreeRenderer {

    private final BinaryTree tree;
    private double maxX, maxY;

    public BinaryTreeRenderer(BinaryTree tree) {
        this.tree = tree;
        maxX = 0;
        maxY = 0;
    }

    @Override
    public void drawTree(GraphicsContext gc, double canvasWidth, double canvasHeight) {
        gc.clearRect(0, 0, canvasWidth, canvasHeight);
        double startX = canvasWidth / 2;
        double startY = 40;
        double hGap = canvasWidth / 4;
        drawBinaryNode(gc, tree.root, startX, startY, hGap);
    }

    private void drawBinaryNode(GraphicsContext gc, BinaryTree.Node node, double x, double y, double hGap) {
        if (node == null) return;
        double radius = 20;

        maxX = Math.max(maxX, x + radius);
        maxY = Math.max(maxY, y + radius);


        gc.setFill(Color.LIGHTBLUE);
        gc.fillOval(x - radius, y - radius, radius * 2, radius * 2);
        gc.setStroke(Color.BLACK);
        gc.strokeOval(x - radius, y - radius, radius * 2, radius * 2);

        gc.setFill(Color.BLACK);
        gc.fillText(String.valueOf(node.value), x - 5, y + 5);
        double childY = y + 70;
        if (node.left != null) {
            double childX = x - hGap;
            gc.strokeLine(x, y + radius, childX, childY - radius);
            drawBinaryNode(gc, node.left, childX, childY, hGap / 2);
        }
        if (node.right != null) {
            double childX = x + hGap;
            gc.strokeLine(x, y + radius, childX, childY - radius);
            drawBinaryNode(gc, node.right, childX, childY, hGap / 2);
        }
    }

    @Override
    public void insert(int value) {
        tree.insert(value);
    }

    @Override
    public void delete(int value) {
        tree.delete(value);
    }

    @Override
    public List<Integer> inOrderTraversal() {
        return tree.inOrderTraversal();
    }

    @Override
    public double getDrawingWidth() {

        return maxX + 20;
    }

    @Override
    public double getDrawingHeight() {
        return maxY + 20;
    }

    @Override
    public void clearTree() {
        tree.clear();
    }
}
