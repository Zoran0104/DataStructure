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
        //todo
    }

    @Override
    protected Node<E> createNode(Object element, Node parent) {
        return new AVLNode<E>((E) element, parent);
    }


    private void updateHeight(Node<E> node) {
        ((AVLNode<E>) node).updateHeight();
    }

    private void rebalance(Node<E> grand) {
        //todo

    }

    private void rotateLeft(Node<E> node) {
        //todo
    }

    private void rotateRight(Node<E> node) {
        //todo
    }

    private void afterRotate(Node<E> node, Node<E> parent, Node<E> child) {
        //todo
    }
}
