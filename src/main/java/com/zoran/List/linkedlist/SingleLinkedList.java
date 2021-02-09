package com.zoran.List.linkedlist;

public class SingleLinkedList<E> extends AbstractLinkedList<E> {
    @Override
    public void add(int index, E element) {
        //todo
    }

    @Override
    public E remove(int index) {
        //todo
        return null;
    }


    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        Node<E> head = first;
        while (head != null) {
            if (head == first) {
                stringBuilder.append(head.element);
            } else {
                stringBuilder.append(",").append(head.element);
            }
            head = head.next;
        }
        return "LinkedList{" +
                stringBuilder.toString() +
                '}';
    }
}
