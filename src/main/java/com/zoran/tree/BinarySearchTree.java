package com.zoran.tree;


import com.zoran.tree.printer.BinaryTreeInfo;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.function.Predicate;

public class BinarySearchTree<E> implements BinaryTreeInfo {
    private static class Node<E> {

        E element;
        Node<E> left;
        Node<E> right;
        Node<E> parent;

        public Node(E element, Node<E> parent) {
            this.element = element;
            this.parent = parent;
        }

    }

    private int size;

    private Node<E> root;
    private Comparator<E> comparator;

    public BinarySearchTree() {
        this(null);
    }

    public BinarySearchTree(Comparator<E> comparator) {
        this.comparator = comparator;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void clear() {

    }

    public void add(E element) {
        checkElement(element);
        if (root == null) {
            root = new Node<>(element, null);
            return;
        }
        Node<E> node = root;
        int compare = 0;
        Node<E> parent = null;
        while (node != null) {
            compare = compare(node.element, element);
            parent = node;
            if (compare > 0) {
                node = node.left;
            } else if (compare < 0) {
                node = node.right;
            } else {
                node.element = element;
            }
        }
        if (compare > 0) {
            parent.left = new Node<>(element, parent);
        } else if (compare < 0) {
            parent.right = new Node<>(element, parent);
        }
        size++;
    }

    private int compare(E e1, E e2) {
        if (comparator != null) {
            return comparator.compare(e1, e2);
        }
        return ((Comparable<E>) e1).compareTo(e2);
    }

    public void remove(E element) {

    }

    public void preOrderTraversal(Predicate<E> predicate) {
        preOrderTraversal(root, predicate);
    }

    private void preOrderTraversal(Node<E> node, Predicate<E> predicate) {
        if (node == null || predicate == null) {
            return;
        }
        predicate.test(node.element);
        preOrderTraversal(node.left, predicate);
        preOrderTraversal(node.right, predicate);
    }

    public void onOrderTraversal(Predicate<E> predicate) {
        onOrderTraversal(root,predicate);
    }

    private void onOrderTraversal(Node<E> node, Predicate<E> predicate) {
        if (node == null || predicate == null) {
            return;
        }
        onOrderTraversal(node.left,predicate);
        predicate.test(node.element);
        onOrderTraversal(node.right,predicate);
    }

    public void postOrderTraversal(Predicate<E> predicate) {
        postOrderTraversal(root,predicate);
    }

    private void postOrderTraversal(Node<E> node, Predicate<E> predicate) {
        if (node == null) {
            return;
        }
        postOrderTraversal(node.left,predicate);
        postOrderTraversal(node.right,predicate);
        predicate.test(node.element);
    }

    public void levelOrderTraversal(Predicate<E> predicate) {
        levelOrderTraversal(root,predicate);
    }

    private void levelOrderTraversal(Node<E> root, Predicate<E> predicate) {
        if (root == null) {
            return;
        }
        Queue<Node<E>> queue = new LinkedList<>();
        queue.offer(root);
        while (!queue.isEmpty()) {
            Node<E> node = queue.poll();
            predicate.test(node.element);
            if (node.left != null) {
                queue.offer(node.left);
            }
            if (node.right != null) {
                queue.offer(node.right);
            }
        }
    }

    public boolean contains(E element) {
        return false;
    }

    private void checkElement(E element) {
        if (element == null) {
            throw new IllegalArgumentException("element cant be null");
        }
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("BinarySearchTree{");
        sb.append("root=").append(root);
        sb.append('}');
        return sb.toString();
    }

    @Override
    public Object root() {
        return root;
    }

    @Override
    public Object left(Object node) {
        return ((Node<E>) node).left;
    }

    @Override
    public Object right(Object node) {
        return ((Node<E>) node).right;
    }

    @Override
    public Object string(Object node) {
        return ((Node<E>) node).element;
    }
    
}
