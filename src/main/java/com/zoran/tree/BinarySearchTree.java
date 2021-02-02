package com.zoran.tree;


import com.zoran.tree.printer.BinaryTreeInfo;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.Queue;

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

        public boolean isLeaf() {
            return left == null && right == null;
        }

        public boolean hasTwoChildren() {
            return left != null && right != null;
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
        Node<E> newNode = new Node<>(element, parent);
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
            } else if (node == node.parent.left) {
                node.parent.left = replacement;
            } else if (node == node.parent.right) {
                node.parent.right = replacement;
            }
        } else if (node.parent == null) {
            //叶子节点且root节点
            root = null;
        }else{
            //叶子节点且普通节点
            if (node == node.parent.right) {
                node.parent.right = null;
            }else{
                node.parent.left = null;
            }
        }
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

    public void preOrderTraversal(Operation<E> operation) {
        if (operation == null) {
            return;
        }
        preOrderTraversal(root, operation);
    }

    private void preOrderTraversal(Node<E> node, Operation<E> operation) {
        if (node == null || operation.isStop) {
            return;
        }
        operation.isStop = operation.operation(node.element);
        preOrderTraversal(node.left, operation);
        preOrderTraversal(node.right, operation);
    }

    public void onOrderTraversal(Operation<E> operation) {
        if (operation == null) {
            return;
        }
        onOrderTraversal(root, operation);
    }

    private void onOrderTraversal(Node<E> node, Operation<E> operation) {
        if (node == null || operation.isStop) {
            return;
        }
        onOrderTraversal(node.left, operation);
        if (operation.isStop) {
            return;
        }
        operation.operation(node.element);
        onOrderTraversal(node.right, operation);
    }

    public void postOrderTraversal(Operation<E> operation) {
        postOrderTraversal(root, operation);
    }

    private void postOrderTraversal(Node<E> node, Operation<E> operation) {
        if (node == null || operation.isStop) {
            return;
        }
        postOrderTraversal(node.left, operation);
        postOrderTraversal(node.right, operation);
        if (operation.isStop) {
            return;
        }
        operation.operation(node.element);
    }

    public void levelOrderTraversal(Operation<E> operation) {
        if (operation == null) {
            return;
        }
        levelOrderTraversal(root, operation);
    }

    private void levelOrderTraversal(Node<E> root, Operation<E> operation) {
        if (root == null) {
            return;
        }
        Queue<Node<E>> queue = new LinkedList<>();
        queue.offer(root);
        while (!queue.isEmpty()) {
            Node<E> node = queue.poll();
            operation.operation(node.element);
            if (node.left != null) {
                queue.offer(node.left);
            }
            if (node.right != null) {
                queue.offer(node.right);
            }
        }
    }

    public Node<E> getPredecessor(Node<E> node) {
        if (node == null) {
            return null;
        }
        //前驱节点在左子树中
        if (node.left != null) {
            node = node.left;
            while (node.right != null) {
                node = node.right;
            }
            return node;
        }
        //前驱节点在父节点
        while (node.parent != null && node.parent.right != node) {
            node = node.parent;
        }
        return node.parent;
    }

    public Node<E> getSuccessor(Node<E> node) {
        if (node == null) {
            return null;
        }
        if (node.right != null) {
            node = node.right;
            while (node.left != null) {
                node = node.left;
            }
            return node;
        }
        while (node.parent != null && node.parent.left != node) {
            node = node.parent;
        }
        return node.parent;
    }

    public boolean contains(E element) {
        return node(element) != null;
    }

    private void checkElement(E element) {
        if (element == null) {
            throw new IllegalArgumentException("element cant be null");
        }
    }

    public int heightRecursion() {
        return heightRecursion(root);
    }

    private int heightRecursion(Node<E> node) {
        if (node == null) {
            return 0;
        }
        return Math.max(height(node.left), height(node.right)) + 1;
    }

    public int height() {
        return height(root);
    }

    private int height(Node<E> node) {
        if (node == null) {
            return 0;
        }
        Queue<Node<E>> queue = new LinkedList<>();
        queue.offer(node);
        int height = 0;
        //int rowSize = 1;
        //while (!queue.isEmpty()) {
        //    Node<E> nodePoll = queue.poll();
        //    rowSize--;
        //    if (nodePoll.left != null) {
        //        queue.offer(nodePoll.left);
        //    }
        //    if (nodePoll.right != null) {
        //        queue.offer(nodePoll.right);
        //    }
        //    if (rowSize == 0) {
        //        height++;
        //        rowSize = queue.size();
        //    }
        //}
        while (!queue.isEmpty()) {
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                Node<E> nodePoll = queue.poll();
                if (nodePoll.left != null) {
                    queue.offer(nodePoll.left);
                }
                if (nodePoll.right != null) {
                    queue.offer(nodePoll.right);
                }
            }
            height++;
        }
        return height;
    }

    public boolean isCompleteTree() {
        return isCompleteTree(root);
    }

    private boolean isCompleteTree(Node<E> node) {
        if (node == null) {
            return false;
        }
        Queue<Node<E>> queue = new LinkedList<>();
        queue.offer(node);
        boolean leaf = false;
        while (!queue.isEmpty()) {
            Node<E> pNode = queue.poll();
            if (leaf && !pNode.isLeaf()) {
                return false;
            }
            if (pNode.left != null) {
                queue.offer(pNode.left);
            } else if (pNode.right != null) {
                return false;
            }

            if (pNode.right != null) {
                queue.offer(pNode.right);
            } else {
                leaf = true;
            }
        }
        return true;
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

    public abstract static class Operation<E> {
        public boolean isStop = false;

        public abstract boolean operation(E element);
    }
}
