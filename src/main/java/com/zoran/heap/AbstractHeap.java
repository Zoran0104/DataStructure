package com.zoran.heap;

import java.util.Comparator;

public abstract class AbstractHeap<E> implements Heap<E> {
    protected int size;
    private Comparator<E> comparator;

    public AbstractHeap() {
        this(null);
    }

    public AbstractHeap(Comparator<E> comparator) {
        this.comparator = comparator;
    }

    protected int compare(E e1, E e2) {
        return comparator == null ? ((Comparable<E>) e1).compareTo(e2) : comparator.compare(e1, e2);
    }
}
