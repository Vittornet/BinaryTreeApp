package com.trees.binarytreeproject.domain.tree;

import java.util.ArrayList;
import java.util.List;

public class RedBlackTree {
    public final Node NIL;
    public Node root;

    public enum NodeColor {
        RED, BLACK
    }

    public class Node {
        public int value;
        public Node left, right, parent;
        public NodeColor color;

        public Node(int value, NodeColor color) {
            this.value = value;
            this.color = color;
            left = NIL;
            right = NIL;
            parent = NIL;
        }
    }

    public RedBlackTree() {
        NIL = new Node(0, NodeColor.BLACK);
        NIL.left = NIL;
        NIL.right = NIL;
        NIL.parent = NIL;
        root = NIL;
    }


    public void insert(int value) {
        Node z = new Node(value, NodeColor.RED);
        z.left = NIL;
        z.right = NIL;
        Node y = NIL;
        Node x = root;
        while (x != NIL) {
            y = x;
            if (z.value < x.value) {
                x = x.left;
            } else {
                x = x.right;
            }
        }
        z.parent = y;
        if (y == NIL) {
            root = z;
        } else if (z.value < y.value) {
            y.left = z;
        } else {
            y.right = z;
        }
        fixInsertion(z);
    }

    private void fixInsertion(Node z) {
        while (z.parent != NIL && z.parent.color == NodeColor.RED) {
            if (z.parent == z.parent.parent.left) {
                Node y = z.parent.parent.right;
                if (y != NIL && y.color == NodeColor.RED) {
                    z.parent.color = NodeColor.BLACK;
                    y.color = NodeColor.BLACK;
                    z.parent.parent.color = NodeColor.RED;
                    z = z.parent.parent;
                } else {
                    if (z == z.parent.right) {
                        z = z.parent;
                        rotateLeft(z);
                    }
                    z.parent.color = NodeColor.BLACK;
                    z.parent.parent.color = NodeColor.RED;
                    rotateRight(z.parent.parent);
                }
            } else {
                Node y = z.parent.parent.left;
                if (y != NIL && y.color == NodeColor.RED) {
                    z.parent.color = NodeColor.BLACK;
                    y.color = NodeColor.BLACK;
                    z.parent.parent.color = NodeColor.RED;
                    z = z.parent.parent;
                } else {
                    if (z == z.parent.left) {
                        z = z.parent;
                        rotateRight(z);
                    }
                    z.parent.color = NodeColor.BLACK;
                    z.parent.parent.color = NodeColor.RED;
                    rotateLeft(z.parent.parent);
                }
            }
        }
        root.color = NodeColor.BLACK;
    }

    private void rotateLeft(Node x) {
        Node y = x.right;
        x.right = y.left;
        if (y.left != NIL) {
            y.left.parent = x;
        }
        y.parent = x.parent;
        if (x.parent == NIL) {
            root = y;
        } else if (x == x.parent.left) {
            x.parent.left = y;
        } else {
            x.parent.right = y;
        }
        y.left = x;
        x.parent = y;
    }

    private void rotateRight(Node x) {
        Node y = x.left;
        x.left = y.right;
        if (y.right != NIL) {
            y.right.parent = x;
        }
        y.parent = x.parent;
        if (x.parent == NIL) {
            root = y;
        } else if (x == x.parent.right) {
            x.parent.right = y;
        } else {
            x.parent.left = y;
        }
        y.right = x;
        x.parent = y;
    }


    public void delete(int value) {
        Node z = search(root, value);
        if (z == NIL) return;
        Node y = z;
        NodeColor yOriginalColor = y.color;
        Node x;
        if (z.left == NIL) {
            x = z.right;
            transplant(z, z.right);
        } else if (z.right == NIL) {
            x = z.left;
            transplant(z, z.left);
        } else {
            y = minimum(z.right);
            yOriginalColor = y.color;
            x = y.right;
            if (y.parent == z) {
                x.parent = y;
            } else {
                transplant(y, y.right);
                y.right = z.right;
                y.right.parent = y;
            }
            transplant(z, y);
            y.left = z.left;
            y.left.parent = y;
            y.color = z.color;
        }
        if (yOriginalColor == NodeColor.BLACK) {
            fixDeletion(x);
        }
    }

    private void fixDeletion(Node x) {
        while (x != root && x.color == NodeColor.BLACK) {
            if (x == x.parent.left) {
                Node w = x.parent.right;
                if (w.color == NodeColor.RED) {
                    w.color = NodeColor.BLACK;
                    x.parent.color = NodeColor.RED;
                    rotateLeft(x.parent);
                    w = x.parent.right;
                }
                if (w.left.color == NodeColor.BLACK && w.right.color == NodeColor.BLACK) {
                    w.color = NodeColor.RED;
                    x = x.parent;
                } else {
                    if (w.right.color == NodeColor.BLACK) {
                        w.left.color = NodeColor.BLACK;
                        w.color = NodeColor.RED;
                        rotateRight(w);
                        w = x.parent.right;
                    }
                    w.color = x.parent.color;
                    x.parent.color = NodeColor.BLACK;
                    w.right.color = NodeColor.BLACK;
                    rotateLeft(x.parent);
                    x = root;
                }
            } else {
                Node w = x.parent.left;
                if (w.color == NodeColor.RED) {
                    w.color = NodeColor.BLACK;
                    x.parent.color = NodeColor.RED;
                    rotateRight(x.parent);
                    w = x.parent.left;
                }
                if (w.right.color == NodeColor.BLACK && w.left.color == NodeColor.BLACK) {
                    w.color = NodeColor.RED;
                    x = x.parent;
                } else {
                    if (w.left.color == NodeColor.BLACK) {
                        w.right.color = NodeColor.BLACK;
                        w.color = NodeColor.RED;
                        rotateLeft(w);
                        w = x.parent.left;
                    }
                    w.color = x.parent.color;
                    x.parent.color = NodeColor.BLACK;
                    w.left.color = NodeColor.BLACK;
                    rotateRight(x.parent);
                    x = root;
                }
            }
        }
        x.color = NodeColor.BLACK;
    }

    private void transplant(Node u, Node v) {
        if (u.parent == NIL) {
            root = v;
        } else if (u == u.parent.left) {
            u.parent.left = v;
        } else {
            u.parent.right = v;
        }
        v.parent = u.parent;
    }

    private Node minimum(Node node) {
        while (node.left != NIL) {
            node = node.left;
        }
        return node;
    }

    private Node search(Node node, int value) {
        while (node != NIL) {
            if (value == node.value) {
                return node;
            } else if (value < node.value) {
                node = node.left;
            } else {
                node = node.right;
            }
        }
        return NIL;
    }


    public List<Integer> inOrderTraversal() {
        List<Integer> result = new ArrayList<>();
        inOrderHelper(root, result);
        return result;
    }

    private void inOrderHelper(Node node, List<Integer> result) {
        if (node != NIL) {
            inOrderHelper(node.left, result);
            result.add(node.value);
            inOrderHelper(node.right, result);
        }
    }

    public void clear() {
        root = NIL;
    }
}
