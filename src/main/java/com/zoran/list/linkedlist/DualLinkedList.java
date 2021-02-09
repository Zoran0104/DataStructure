package com.zoran.list.linkedlist;

public class DualLinkedList<E> extends AbstractLinkedList<E> {
    private static class Node<E> extends AbstractLinkedList.Node<E> {
        Node<E> prev;
        E element;
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
        //todo
        return null;
    }
}
