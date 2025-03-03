package com.trees.binarytreeproject.domain.tree;

import java.util.ArrayList;
import java.util.List;

public class BinaryTree {

    public Node root;

    public static class Node {
        public int value;
        public Node left;
        public Node right;

        public Node(int value) {
            this.value = value;
        }
    }

    public void insert(int value) {
        root = insertRecursive(root, value);
    }

    private Node insertRecursive(Node node, int value) {
        if (node == null) {
            return new Node(value);
        }
        if (value < node.value) {
            node.left = insertRecursive(node.left, value);
        } else if (value > node.value) {
            node.right = insertRecursive(node.right, value);
        }
        return node;
    }

    public void delete(int value) {
        root = deleteRecursive(root, value);
    }

    private Node deleteRecursive(Node node, int value) {
        if (node == null) {
            return null;
        }
        if (value < node.value) {
            node.left = deleteRecursive(node.left, value);
        } else if (value > node.value) {
            node.right = deleteRecursive(node.right, value);
        } else {
            if (node.left == null && node.right == null) {
                return null;
            }
            if (node.left == null) {
                return node.right;
            }
            if (node.right == null) {
                return node.left;
            }
            int smallestValue = findSmallestValue(node.right);
            node.value = smallestValue;
            node.right = deleteRecursive(node.right, smallestValue);
        }
        return node;
    }

    private int findSmallestValue(Node node) {
        return node.left == null ? node.value : findSmallestValue(node.left);
    }

    public List<Integer> inOrderTraversal() {
        List<Integer> list = new ArrayList<>();
        inOrderRecursive(root, list);
        return list;
    }

    private void inOrderRecursive(Node node, List<Integer> list) {
        if (node != null) {
            inOrderRecursive(node.left, list);
            list.add(node.value);
            inOrderRecursive(node.right, list);
        }
    }

    public void clear() {
        root = null;
    }
}