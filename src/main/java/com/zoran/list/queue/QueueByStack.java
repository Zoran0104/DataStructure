package com.zoran.list.queue;

import com.zoran.list.Stack;

public class QueueByStack<E> {
    private Stack<E> inStack = new Stack<>();
    private Stack<E> outStack = new Stack<>();

    public int size() {
        return inStack.size() + outStack.size();
    }

    public boolean isEmpty() {
        return inStack.size() == 0 && outStack.size() == 0;
    }

    public void enQueue(E item) {
        inStack.push(item);
    }

    public E deQueue() {
        if (outStack.isEmpty()) {
            while (!inStack.isEmpty()) {
                outStack.push(inStack.pop());
            }
        }
        return outStack.pop();
    }

    public E front() {
        if (outStack.isEmpty()) {
            while (!inStack.isEmpty()) {
                outStack.push(inStack.pop());
            }
        }
        return outStack.peek();
    }
}
