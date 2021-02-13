package com.zoran.tree.binarysearchtree;


import com.zoran.tree.BinaryTree;
import java.util.Comparator;

public class BinarySearchTree<E> extends BinaryTree {

    public BinarySearchTree() {
    }

    public BinarySearchTree(Comparator<E> comparator) {
        super(comparator);
    }



    public void clear() {

    }

    public void add(E element) {
        //todo
    }

    protected void afterAdd(Node<E> node) {

    }

    private int compare(E e1, E e2) {
        if (comparator != null) {
            return comparator.compare(e1, e2);
        }
        return ((Comparable<E>) e1).compareTo(e2);
    }

    public void remove(E element) {
        remove(node(element));
    }

    private void remove(Node<E> node) {
        //todo
    }

    /**
     * 查找值为element的节点
     *
     * @param element Node中的值
     * @return
     */
    private Node<E> node(E element) {
        Node<E> node = root;
        while (node != null) {
            int compare = compare(element, node.element);
            if (compare == 0) {
                return node;
            } else if (compare > 0) {
                node = node.right;
            } else {
                node = node.left;
            }
        }
        return null;
    }



    public boolean contains(E element) {
        return node(element) != null;
    }

    private void checkElement(E element) {
        if (element == null) {
            throw new IllegalArgumentException("element cant be null");
        }
    }




    @Override
    public String toString() {
        return toString(root, new StringBuilder(), "");
    }

    private String toString(Node<E> node, StringBuilder sb, String prefix) {
        if (node == null) {
            return "null";
        }
        sb.append(prefix).append(node.element).append("\n");
        toString(node.left, sb, prefix + "[L]");
        toString(node.right, sb, prefix + "[R]");
        return sb.toString();
    }




}
