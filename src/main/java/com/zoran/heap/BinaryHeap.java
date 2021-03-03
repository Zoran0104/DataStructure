package com.zoran.heap;

import com.zoran.tree.printer.BinaryTreeInfo;

import java.util.Comparator;

@SuppressWarnings("ALL")
public class BinaryHeap<E> extends AbstractHeap<E> implements BinaryTreeInfo {
    private E[] elements;
    private static final int DEFAULT_CAPACITY = 10;

    public BinaryHeap() {
        this(null);
    }

    public BinaryHeap(Comparator<E> comparator) {
        super(comparator);
        elements = (E[]) new Object[DEFAULT_CAPACITY];
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public void clear() {
        for (int i = 0; i < size; i++) {
            elements[i] = null;
        }
        size = 0;
    }

    @Override
    public void add(E element) {
        ensureCapacity(size + 1);
        elementNotNullCheck(element);
        elements[size++] = element;
        siftUp(size - 1);
    }

    @Override
    public E get() {
        emptyCheck();
        return elements[0];
    }

    @Override
    public E remove() {
        E oldValue = get();
        int lastIndex = --size;
        elements[0] = elements[lastIndex];
        elements[lastIndex] = null;
        siftDown(0);
        return oldValue;
    }

    @Override
    public E replace(E element) {
        E oldValue = null;
        elementNotNullCheck(element);
        if (size == 0) {
            elements[0] = element;
            size++;
        } else {
            oldValue = elements[0];
            elements[0] = element;
            siftDown(0);
        }
        return oldValue;
    }

    private void siftUp(int index) {
        if (index < 1 || index >= size) {
            return;
        }
        E element = elements[index];
        while (index > 0) {
            int parentIndex = (index - 1) >> 1;
            if (compare(element, elements[parentIndex]) <= 0) {
                break;
            }
            elements[index] = elements[parentIndex];
            index = parentIndex;
        }
        elements[index] = element;
    }

    private void siftDown(int index) {
        E element = elements[index];
        int half = size >> 1;
        while (index < half) {
            int childIndex = (index << 1) + 1;
            int rightIndex = childIndex + 1;
            E child = elements[childIndex];
            if (rightIndex < size && compare(child, elements[rightIndex]) < 0) {
                childIndex = rightIndex;
                child = elements[childIndex];
            }
            if (compare(element, child) >= 0) {
                break;
            }
            elements[index] = child;
            index = childIndex;
        }
        elements[index] = element;
    }

    private void emptyCheck() {
        if (size == 0) {
            throw new IndexOutOfBoundsException();
        }
    }

    private void ensureCapacity(int capacity) {
        int oldCapacity = elements.length;
        if (capacity <= oldCapacity) {
            return;
        }
        int newCapacity = (oldCapacity >> 1) + oldCapacity;
        if (newCapacity < capacity) {
            newCapacity = capacity;
        }
        E[] newElements = (E[]) new Object[newCapacity];
        System.arraycopy(elements, 0, newElements, 0, size);
        elements = newElements;
    }

    private void elementNotNullCheck(E element) {
        if (element == null) {
            throw new IllegalArgumentException("element must not to be null");
        }
    }

    @Override
    public Object root() {
        return 0;
    }

    @Override
    public Object left(Object node) {
        int index = (((Integer) node) << 1) + 1;
        return index >= size ? null : index;
    }

    @Override
    public Object right(Object node) {
        int index = (((Integer) node) << 1) + 2;
        return index >= size ? null : index;
    }

    @Override
    public Object string(Object node) {
        return elements[(int) node];
    }
}