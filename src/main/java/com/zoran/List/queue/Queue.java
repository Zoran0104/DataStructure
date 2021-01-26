package com.zoran.List.queue;

import com.zoran.List.linkedlist.SingleLinkedList;

public class Queue<E>   {
    private SingleLinkedList<E> queue = new SingleLinkedList<>();
    public int size() {
        return queue.size();
    }

    public boolean isEmpty() {
        return queue.isEmpty();
    }

    public void enQueue(E item) {
        queue.add(item);
    }

    public E deQueue() {
        return queue.remove(0);
    }

    public E front() {
        return queue.get(0);
    }
}
