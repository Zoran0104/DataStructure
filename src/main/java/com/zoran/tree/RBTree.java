package com.zoran.tree;

import java.util.Comparator;

public class RBTree<E> extends BalanceBinarySearchTree<E> {
    private static class RBNode<E> extends Node<E> {
        //新添加的节点默认为红色 能够尽快让红黑树的性质满足
        boolean color = RED;

        public RBNode(E element, Node<E> parent) {
            super(element, parent);
        }

        @Override
        public String toString() {
            String s = "";
            if (color == RED) {
                s = "R_";
            }
            return s += element;
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
        Node<E> parent = node.parent;
        //添加节点为根节点的时候
        if (parent == null) {
            black(node);
            return;
        }
        //如果父节点是黑色，直接返回
        if (isBlack(parent)) {
            return;
        }
        Node<E> uncle = parent.sibling();
        //此处是迭代的结果，因为后续的每种情况都要对grand染色为red
        Node<E> grand = red(parent.parent);
        if (isRed(uncle)) {
            black(parent);
            black(uncle);
            afterAdd(grand);
            return;
        }
        if (parent.isLeftChild()) {
            if (node.isLeftChild()) {
                black(parent);
            } else {
                black(node);
                rotateLeft(parent);
            }
            rotateRight(grand);
        } else {
            if (node.isLeftChild()) {
                black(node);
                rotateRight(parent);
            } else {
                black(parent);
            }
            rotateLeft(grand);
        }
    }

    @Override
    protected void afterRemove(Node<E> node) {
        //如果删除的节点是红色
        //用以取代node的子节点是红色
        if (isRed(node)) {
            black(node);
            return;
        }
        Node<E> parent = node.parent;
        //删除的是根节点
        if (parent == null) {
            return;
        }
        //删除的是黑色叶子节点
        boolean left = parent.left == null || node.isLeftChild();
        Node<E> sibling = left ? parent.right : parent.left;
        if (left) {
            if (isRed(sibling)) {
                black(sibling);
                red(parent);
                rotateLeft(parent);
                sibling = parent.right;
            }
            //兄弟节点必然是黑色节点
            if (isBlack(sibling.left) && isBlack(sibling.right)) {
                //兄弟节点没有1个红色子节点，父节点要向下跟兄弟节点合并
                boolean parentBlack = isBlack(parent);
                black(parent);
                red(sibling);
                if (parentBlack) {
                    afterRemove(parent);
                }
            } else {
                if (isBlack(sibling.right)) {
                    rotateRight(sibling);
                    sibling = parent.right;
                }
                color(sibling, colorOf(parent));
                black(sibling.right);
                black(parent);
                rotateLeft(parent);
            }
        } else {
            if (isRed(sibling)) {
                black(sibling);
                red(parent);
                rotateRight(parent);
                sibling = parent.left;
            }
            //兄弟节点必然是黑色节点
            if (isBlack(sibling.left) && isBlack(sibling.right)) {
                //兄弟节点没有1个红色子节点，父节点要向下跟兄弟节点合并
                boolean parentBlack = isBlack(parent);
                black(parent);
                red(sibling);
                if (parentBlack) {
                    afterRemove(parent);
                }
            } else {
                if (isBlack(sibling.left)) {
                    rotateLeft(sibling);
                    sibling = parent.left;
                }
                color(sibling, colorOf(parent));
                black(sibling.left);
                black(parent);
                rotateRight(parent);
            }
        }


    }

    @Override
    protected Node<E> createNode(Object element, Node parent) {
        return new RBNode<E>((E) element, parent);
    }
}
