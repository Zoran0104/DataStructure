package com.zoran.tree;

import java.util.Comparator;

public class AVLTree<E> extends BalanceBinarySearchTree<E> {

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
    protected void afterRemove(Node<E> node) {
        while ((node = node.parent) != null) {
            if (isBalance(node)) {
                updateHeight(node);
            } else {
                rebalance(node);
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

    private void rebalance2(Node<E> grand) {
        Node<E> parent = ((AVLNode<E>) grand).tallerChild();
        Node<E> node = ((AVLNode<E>) parent).tallerChild();
        if (parent.isLeftChild()) {
            if (node.isLeftChild()) {
                rotate(grand, node.left, node, node.right, parent, parent.right, grand, grand.right);
            } else {
                rotate(grand, parent.left, parent, node.left, node, node.right, grand, grand.right);
            }

        } else {
            if (node.isLeftChild()) {
                rotate(grand, grand.left, grand, node.left, node, node.right, parent, parent.right);
            } else {
                rotate(grand, grand.left, grand, parent.left, parent, node.left, node, node.right);
            }
        }
    }

    @Override
    protected void rotate(Node<E> r, Node<E> a, Node<E> b, Node<E> c, Node<E> d, Node<E> e, Node<E> f, Node<E> g) {
        super.rotate(r, a, b, c, d, e, f, g);
        updateHeight(b);
        updateHeight(f);
        updateHeight(g);
    }

    @Override
    protected void afterRotate(Node<E> node, Node<E> parent, Node<E> child) {
        super.afterRotate(node, parent, child);
        updateHeight(node);
        updateHeight(parent);
    }
}
