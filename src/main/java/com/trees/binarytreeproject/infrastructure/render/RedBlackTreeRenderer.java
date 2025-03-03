package com.trees.binarytreeproject.infrastructure.render;

import com.trees.binarytreeproject.domain.tree.RedBlackTree;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class RedBlackTreeRenderer implements TreeRenderer {

    private final RedBlackTree tree;
    private double maxX, maxY;

    public RedBlackTreeRenderer(RedBlackTree tree) {
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
        drawNode(gc, tree.root, startX, startY, hGap);
    }

    private void drawNode(GraphicsContext gc, RedBlackTree.Node node, double x, double y, double hGap) {
        if (node == tree.NIL) return;
        double radius = 20;
        maxX = Math.max(maxX, x + radius);
        maxY = Math.max(maxY, y + radius);


        if (node.color == RedBlackTree.NodeColor.RED) {
            gc.setFill(Color.RED);
        } else {
            gc.setFill(Color.BLACK);
        }
        gc.fillOval(x - radius, y - radius, radius * 2, radius * 2);
        gc.setStroke(Color.BLACK);
        gc.strokeOval(x - radius, y - radius, radius * 2, radius * 2);
        if (node.color == RedBlackTree.NodeColor.BLACK) {
            gc.setFill(Color.WHITE);
        } else {
            gc.setFill(Color.BLACK);
        }
        gc.fillText(String.valueOf(node.value), x - 5, y + 5);

        double childY = y + 70;
        if (node.left != tree.NIL) {
            double childX = x - hGap;
            gc.strokeLine(x, y + radius, childX, childY - radius);
            drawNode(gc, node.left, childX, childY, hGap / 2);
        }
        if (node.right != tree.NIL) {
            double childX = x + hGap;
            gc.strokeLine(x, y + radius, childX, childY - radius);
            drawNode(gc, node.right, childX, childY, hGap / 2);
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
    public java.util.List<Integer> inOrderTraversal() {
        return tree.inOrderTraversal();
    }

    @Override
    public void clearTree() {
        tree.clear();
    }

    @Override
    public double getDrawingWidth() {
        return maxX + 20;
    }

    @Override
    public double getDrawingHeight() {
        return maxY + 20;
    }
}
