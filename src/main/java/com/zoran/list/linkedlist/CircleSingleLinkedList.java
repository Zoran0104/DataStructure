package com.zoran.list.linkedlist;

public class CircleSingleLinkedList<E> extends AbstractLinkedList<E> {
    @Override
    public void add(int index, E element) {
        //todo
    }

    @Override
    public E remove(int index) {
        rangeCheck(index);
        Node<E> node = first;
        if (index == 0) {
            if (size == 1) {
                first = null;
            } else {
                Node<E> nodeByIndex = getNodeByIndex(size - 1);
                first = first.next;
                nodeByIndex.next = first;
            }
        } else {
            Node<E> prev = getNodeByIndex(index - 1);
            node = prev.next;
            prev.next = prev.next.next;
        }
        size--;
        return node.element;
    }

    @Override
    public Node<E> getNodeByIndex(int index) {
        rangeCheck(index);
        Node<E> node = first;
        for (int i = 0; i < index; i++) {
            node = node.next;
        }
        return node;
    }
}
