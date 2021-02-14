package com.zoran.tree;

import java.util.Comparator;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

public class BalanceBinarySearchTree<E> extends BinarySearchTree<E> {
    public BalanceBinarySearchTree() {
    }

    public BalanceBinarySearchTree(Comparator<E> comparator) {
        super(comparator);
    }

    protected void rotate(Node<E> r, Node<E> a, Node<E> b, Node<E> c, Node<E> d, Node<E> e, Node<E> f, Node<E> g) {
        d.parent = r.parent;
        if (r.isLeftChild()) {
            r.parent.left = d;
        } else if (r.isRightChild()) {
            r.parent.right = d;
        } else {
            root = d;
        }

        b.left = a;
        b.right = c;
        if (a != null) {
            a.parent = b;
        }
        if (c != null) {
            c.parent = b;
        }

        f.left = e;
        f.right = g;
        if (e != null) {
            e.parent = f;
        }
        if (g != null) {
            g.parent = f;
        }

        d.left = b;
        d.right = f;
        b.parent = d;
        f.parent = d;
    }

    protected void rotateLeft(Node<E> node) {
        Node<E> parent = node.right;
        Node<E> child = parent.left;
        node.right = child;
        parent.left = node;
        afterRotate(node, parent, child);
    }

    protected void rotateRight(Node<E> node) {
        Node<E> parent = node.left;
        Node<E> child = parent.right;
        node.left = child;
        parent.right = node;
        afterRotate(node, parent, child);
    }

    protected void afterRotate(Node<E> node, Node<E> parent, Node<E> child) {
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
    }
}
