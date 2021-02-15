package com.zoran.set;

import com.zoran.list.List;
import com.zoran.list.linkedlist.SingleLinkedList;




public class ListSet<E> implements Set<E> {
    private SingleLinkedList<E> set = new SingleLinkedList<>();
    @Override
    public int size() {
        return set.size();
    }

    @Override
    public boolean isEmpty() {
        return set.isEmpty();
    }

    @Override
    public void clear() {
        set.clear();
    }

    @Override
    public boolean contains(E element) {
        return set.contains(element);
    }

    @Override
    public void add(E element) {
        int index = set.indexOf(element);
        if (index != List.ELEMENT_NOT_FOUND) {
            set.set(index, element);
            return;
        }
        set.add(element);
    }

    @Override
    public void remove(E element) {
        int index = set.indexOf(element);
        if (index != List.ELEMENT_NOT_FOUND) {
            set.remove(index);
        }
    }

    @Override
    public void traversal(Visitor<E> visitor) {
        if (visitor == null) {
            return;
        }
        for (int i = 0; i < set.size(); i++) {
            visitor.visit(set.get(i));
        }
    }
}
