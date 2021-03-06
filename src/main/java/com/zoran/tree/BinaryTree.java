package com.zoran.tree;


import com.zoran.tree.printer.BinaryTreeInfo;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class BinaryTree<E> implements BinaryTreeInfo {
    protected static class Node<E> {
        public E element;
        public Node<E> left;
        public Node<E> right;
        public Node<E> parent;

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

        public boolean isLeftChild() {
            return parent != null && this == parent.left;
        }

        public boolean isRightChild() {
            return parent != null && this == parent.right;
        }

        public Node<E> sibling() {
            if (isLeftChild()) {
                return parent.right;
            }
            if (isRightChild()) {
                return parent.left;
            }
            return null;
        }
    }

    protected int size;

    protected Node<E> root;
    protected Comparator<E> comparator;

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public BinaryTree() {
        this(null);
    }

    public BinaryTree(Comparator<E> comparator) {
        this.comparator = comparator;
    }

    public void preOrderTraversalRecursion(Operation<E> operation) {
        if (operation == null) {
            return;
        }
        preOrderTraversalRecursion(root, operation);
    }

    private void preOrderTraversalRecursion(Node<E> node, Operation<E> operation) {
        if (node == null || operation.isStop) {
            return;
        }
        operation.isStop = operation.operation(node.element);
        preOrderTraversalRecursion(node.left, operation);
        preOrderTraversalRecursion(node.right, operation);
    }

    public void preOrderTraversal(Operation<E> operation) {
        if (operation == null || root == null) {
            return;
        }
        Stack<Node<E>> stack = new Stack<>();
        stack.push(root);
        while (!stack.isEmpty()) {
            Node<E> pop = stack.pop();
            operation.operation(pop.element);
            if (pop.right != null) {
                stack.push(pop.right);
            }
            if (pop.left != null) {
                stack.push(pop.left);
            }
        }
    }

    public void onOrderTraversalRecursion(Operation<E> operation) {
        if (operation == null) {
            return;
        }
        onOrderTraversalRecursion(root, operation);
    }

    private void onOrderTraversalRecursion(Node<E> node, Operation<E> operation) {
        if (node == null || operation.isStop) {
            return;
        }
        onOrderTraversalRecursion(node.left, operation);
        if (operation.isStop) {
            return;
        }
        operation.operation(node.element);
        onOrderTraversalRecursion(node.right, operation);
    }

    public void onOrderTraversal(Operation<E> operation) {
        if (root == null || operation == null) {
            return;
        }
        Stack<Node<E>> stack = new Stack<>();
        Node<E> node = root;
        boolean needLeft = true;
        stack.push(node);
        while (!stack.isEmpty()) {
            node = stack.peek();
            if (node.left != null && needLeft) {
                stack.push(node.left);
            } else {
                needLeft = false;
                node = stack.pop();
                operation.operation(node.element);
                if (node.right != null) {
                    stack.push(node.right);
                    needLeft = true;
                }
            }
        }
    }

    public void onOrderTraversal2(Operation<E> operation) {
        if (root == null || operation == null) {
            return;
        }
        Stack<Node<E>> stack = new Stack<>();
        Node<E> node = root;
        while (true) {
            if (node != null) {
                stack.push(node);
                node = node.left;
            } else if (stack.isEmpty()) {
                return;
            } else {
                node = stack.pop();
                operation.operation(node.element);
                node = node.right;
            }
        }
    }

    public void postOrderTraversalRecursion(Operation<E> operation) {
        postOrderTraversalRecursion(root, operation);
    }

    private void postOrderTraversalRecursion(Node<E> node, Operation<E> operation) {
        if (node == null || operation.isStop) {
            return;
        }
        postOrderTraversalRecursion(node.left, operation);
        postOrderTraversalRecursion(node.right, operation);
        if (operation.isStop) {
            return;
        }
        operation.operation(node.element);
    }

    public void postOrderTraversal(Operation<E> operation) {
        if (operation == null || root == null) {
            return;
        }
        Node<E> node = root;
        Stack<Node<E>> stack = new Stack<>();
        stack.push(node);
        Node<E> pop = root;
        while (!stack.isEmpty()) {
            Node<E> top = stack.peek();
            if (top.isLeaf() || pop.parent == top) {
                pop = stack.pop();
                if (operation.operation(pop.element)) {
                    return;
                }
            } else {
                if (top.right != null) {
                    stack.push(top.right);
                }
                if (top.left != null) {
                    stack.push(top.left);
                }
            }
        }
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

    protected Node<E> createNode(E element, Node<E> parent) {
        return new Node<>(element, parent);
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
        return node;
    }

    public abstract static class Operation<E> {
        public boolean isStop = false;

        public abstract boolean operation(E element);
    }


}
