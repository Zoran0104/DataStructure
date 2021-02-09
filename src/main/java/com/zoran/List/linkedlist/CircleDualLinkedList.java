package com.zoran.List.linkedlist;

public class CircleDualLinkedList<E> extends AbstractLinkedList<E> {
    private static class Node<E> extends AbstractLinkedList.Node<E> {
        E element;
        Node<E> prev;
        Node<E> next;

        public Node(Node<E> prev, E element, Node<E> next) {
            super(element, next);
            this.prev = prev;
            this.element = element;
            this.next = next;
        }
    }

    private Node<E> first;
    private Node<E> last;


    @Override
    public void clear() {
        Node<E> node = first;
        Node<E> tmp;
        while (node != null) {
            tmp = node.next;
            node.prev = null;
            node.next = null;
            node.element = null;
            node = tmp;
        }
        first = null;
        last = null;
        size = 0;
    }

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
        Node<E> node;
        if (index < (size >> 1)) {
            node = first;
            for (int i = 0; i < index; i++) {
                node = node.next;
            }
        } else {
            node = last;
            for (int i = size - 1; i > index; i--) {
                node = node.prev;
            }
        }
        return node;
    }
}
