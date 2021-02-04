package com.zoran.tree;

import com.zoran.tree.binarysearchtree.BinarySearchTree;

import java.util.Comparator;

public class AVLTree<E> extends BinarySearchTree<E> {

    protected static class AVLNode<E> extends Node<E> {
        int height = 1;

        public AVLNode(E element, Node<E> parent) {
            super(element, parent);
        }

        public int balanceFactor() {
            int leftHeight = left == null ? 0 : ((AVLNode<E>) left).height;
            int rightHeight = right == null ? 0 : ((AVLNode<E>) right).height;
            return Math.abs(leftHeight - rightHeight);
        }

        public void updateHeight() {
            int leftHeight = left == null ? 0 : ((AVLNode<E>) left).height;
            int rightHeight = right == null ? 0 : ((AVLNode<E>) right).height;
            height = 1 + Math.max(leftHeight, rightHeight);
        }

        public Node<E> tallerChild() {
            int leftHeight = left == null ? 0 : ((AVLNode<E>) left).height;
            int rightHeight = right == null ? 0 : ((AVLNode<E>) right).height;
            if (leftHeight > rightHeight) {
                return left;
            }
            if (leftHeight < rightHeight) {
                return right;
            }
            return isLeftChild() ? left : right;
        }
    }

    public AVLTree() {
    }

    public AVLTree(Comparator<E> comparator) {
        super(comparator);
    }

    private boolean isBalance(Node<E> node) {
        return ((AVLNode<E>) node).balanceFactor() <= 1;
    }

    @Override
    protected void afterAdd(Node<E> node) {
        while ((node = node.parent) != null) {
            if (isBalance(node)) {
                updateHeight(node);
            } else {
                rebalance(node);
                break;
            }
        }
    }

    @Override
    protected Node<E> createNode(Object element, Node parent) {
        return new AVLNode<E>((E) element, parent);
    }


    private void updateHeight(Node<E> node) {
        ((AVLNode<E>) node).updateHeight();
    }

    private void rebalance(Node<E> grand) {
        Node<E> parent = ((AVLNode<E>) grand).tallerChild();
        Node<E> node = ((AVLNode<E>) parent).tallerChild();
        if (parent.isLeftChild()) {
            if (node.isRightChild()) {
                rotateLeft(parent);
            }
            rotateRight(grand);
        } else {
            if (node.isLeftChild()) {
                rotateRight(parent);
            }
            rotateLeft(grand);
        }

    }

    private void rotateLeft(Node<E> node) {
        Node<E> parent = node.right;
        Node<E> child = parent.left;
        node.right = child;
        parent.left = node;
        afterRotate(node, parent, child);
    }

    private void rotateRight(Node<E> node) {
        Node<E> parent = node.left;
        Node<E> child = parent.right;
        node.left = child;
        parent.right = node;
        afterRotate(node, parent, child);
    }

    private void afterRotate(Node<E> node, Node<E> parent, Node<E> child) {
        parent.parent = node.parent;
        if (node.isLeftChild()) {
            node.parent.left = parent;
        } else if (node.isRightChild()) {
            node.parent.right = parent;
        } else {
            root = parent;
        }
        if (child != null) {
            child.parent = node;
        }
        node.parent = parent;
        updateHeight(node);
        updateHeight(parent);
    }
}
