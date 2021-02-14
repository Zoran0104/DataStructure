package com.zoran.tree;

import java.util.Comparator;

public class RBTree<E> extends BalanceBinarySearchTree<E> {
    private static class RBNode<E> extends Node<E> {
        //新添加的节点默认为红色 能够尽快让红黑树的性质满足
        boolean color = RED;

        public RBNode(E element, Node<E> parent) {
            super(element, parent);
        }

        @Override
        public String toString() {
            String s = "";
            if (color == RED) {
                s = "R_";
            }
            return s += element;
        }
    }

    public static final boolean RED = false;
    public static final boolean BLACK = true;

    public RBTree() {
    }

    public RBTree(Comparator<E> comparator) {
        super(comparator);
    }

    private Node<E> color(Node<E> node, boolean color) {
        if (node == null) {
            return null;
        }
        ((RBNode<E>) node).color = color;
        return node;
    }

    private Node<E> red(Node<E> node) {
        return color(node, RED);
    }

    private Node<E> black(Node<E> node) {
        return color(node, BLACK);
    }

    private boolean colorOf(Node<E> node) {
        return node == null ? BLACK : ((RBNode<E>) node).color;
    }

    private boolean isBlack(Node<E> node) {
        return colorOf(node) == BLACK;
    }

    private boolean isRed(Node<E> node) {
        return colorOf(node) == RED;
    }

    @Override
    protected void afterAdd(Node<E> node) {
        Node<E> parent = node.parent;
        //添加节点为根节点的时候
        if (parent == null) {
            black(node);
            return;
        }
        //如果父节点是黑色，直接返回
        if (isBlack(parent)) {
            return;
        }
        Node<E> uncle = parent.sibling();
        //此处是迭代的结果，因为后续的每种情况都要对grand染色为red
        Node<E> grand = red(parent.parent);
        if (isRed(uncle)) {
            black(parent);
            black(uncle);
            afterAdd(grand);
            return;
        }
        if (parent.isLeftChild()) {
            if (node.isLeftChild()) {
                black(parent);
            } else {
                black(node);
                rotateLeft(parent);
            }
            rotateRight(grand);
        } else {
            if (node.isLeftChild()) {
                black(node);
                rotateRight(parent);
            } else {
                black(parent);
            }
            rotateLeft(grand);
        }
    }

    @Override
    protected void afterRemove(Node<E> node) {
        super.afterRemove(node);
    }

    @Override
    protected Node<E> createNode(Object element, Node parent) {
        return new RBNode<E>((E) element, parent);
    }
}
