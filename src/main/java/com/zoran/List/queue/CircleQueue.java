package com.zoran.List.queue;

import com.zoran.List.ArrayList;

import java.util.Objects;

public class CircleQueue<E> {
    private int front;
    private int rear;
    private int size;
    private E[] elements = (E[]) new Object[DEFAULT_CAPACITY];
    private static final int DEFAULT_CAPACITY = 10;

    public CircleQueue() {
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void enQueue(E item) {
        ensureCapacity(size + 1);
        int index = index(size);
        elements[index] = item;
        size++;
    }

    public E deQueue() {
        E element = elements[front];
        elements[front] = null;
        front = index(1);
        size--;
        return element;
    }

    public void clear() {
        for (int i = 0; i < size; i++) {
            elements[index(i)] = null;
        }
        size = 0;
    }

    public E front() {
        return elements[front];
    }

    private void ensureCapacity(int capacity) {
        int oldCapacity = elements.length;
        if (capacity <= oldCapacity) {
            return;
        }
        int newLength = oldCapacity + (oldCapacity >> 1);
        E[] newElements = (E[]) new Object[newLength];
        for (int i = 0; i < size; i++) {
            newElements[i] = elements[index(i)];
        }
        elements = newElements;
        front = 0;
    }

    private int index(int index) {
        index += front;
        return index - (index >= elements.length ? elements.length : 0);
    }
}
