package com.zoran.list.linkedlist;

public class CircleSingleLinkedList<E> extends AbstractLinkedList<E> {
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
    public Node<E> getNodeByIndex(int index) {
        rangeCheck(index);
        Node<E> node = first;
        for (int i = 0; i < index; i++) {
            node = node.next;
        }
        return node;
    }
}
