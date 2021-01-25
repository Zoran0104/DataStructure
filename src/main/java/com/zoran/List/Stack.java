package com.zoran.List;

public class Stack<E> {
    private ArrayList<E> stack;
    public boolean isEmpty() {
        return stack.isEmpty();
    }

    public int size() {
        return stack.size();
    }

    public void push(E element) {
        stack.add(element);
    }

    public E pop() {
        return stack.remove(size() - 1);
    }

    public E peek() {
        return stack.get(size() - 1);
    }
}
