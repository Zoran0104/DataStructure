package com.zoran.set;

import com.zoran.tree.BinaryTree;
import com.zoran.tree.RBTree;

public class TreeSet<E> implements Set<E> {
    private RBTree<E> tree = new RBTree<>();
    @Override
    public int size() {
        return tree.size();
    }

    @Override
    public boolean isEmpty() {
        return tree.isEmpty();
    }

    @Override
    public void clear() {
        tree.clear();
    }

    @Override
    public boolean contains(E element) {
        return tree.contains(element);
    }

    @Override
    public void add(E element) {
        tree.add(element);
    }

    @Override
    public void remove(E element) {
        tree.remove(element);
    }

    @Override
    public void traversal(Visitor<E> visitor) {
        tree.onOrderTraversal(new BinaryTree.Operation<E>() {
            @Override
            public boolean operation(E element) {
                return visitor.visit(element);
            }
        });
    }
}
