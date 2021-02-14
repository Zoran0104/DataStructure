package com.zoran.tree;

import java.util.Comparator;

public class RBTree<E> extends BinarySearchTree<E> {
    private static class RBNode<E> extends Node<E> {
        //新添加的节点默认为红色 能够尽快让红黑树的性质满足
        boolean color = RED;

        public RBNode(E element, Node<E> parent) {
            super(element, parent);
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
        super.afterAdd(node);
    }

    @Override
    protected void afterRemove(Node<E> node) {
        super.afterRemove(node);
    }
}
