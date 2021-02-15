package com.zoran.tree;


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
        checkElement(element);
        if (root == null) {
            root = createNode(element, null);
            afterAdd(root);
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
                return;
            }
        }
        Node<E> newNode = createNode(element, parent);
        if (compare > 0) {
            parent.left = newNode;
        } else if (compare < 0) {
            parent.right = newNode;
        }
        size++;
        afterAdd(newNode);
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
        if (node == null) {
            return;
        }
        if (node.hasTwoChildren()) {
            Node<E> successor = getSuccessor(node);
            node.element = successor.element;
            node = successor;
        }
        Node<E> replacement = node.left != null ? node.left : node.right;
        if (replacement != null) {
            replacement.parent = node.parent;
            if (node.parent == null) {
                root = replacement;
            } else if (node.isLeftChild()) {
                node.parent.left = replacement;
            } else if (node.isRightChild()) {
                node.parent.right = replacement;
            }
            afterRemove(node, replacement);
        } else if (node.parent == null) {
            //叶子节点且root节点
            root = null;
            afterRemove(node, null);
        }else{
            //叶子节点且普通节点
            if (node.isLeftChild()) {
                node.parent.left = null;
            }else{
                node.parent.right = null;
            }
            afterRemove(node, null);
        }
    }

    protected void afterRemove(Node<E> node,Node<E> replacement) {

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
