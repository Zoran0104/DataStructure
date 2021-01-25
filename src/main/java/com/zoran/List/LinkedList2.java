package com.zoran.List;

public class LinkedList2<E> extends AbstractList<E>{
    private Node<E> first;

    public LinkedList2() {
        first = new Node<>(null, null);
    }

    @Override
    public void clear() {
        first.next = null;
        size = 0;
    }

    @Override
    public E get(int index) {
        return getNodeByIndex(index).element;
    }

    @Override
    public E set(int index, E element) {
        Node<E> nodeByIndex = getNodeByIndex(index);
        E oldValue = nodeByIndex.element;
        nodeByIndex.element = element;
        return oldValue;
    }

    @Override
    public void add(int index, E element) {
        rangeCheckForAdd(index);
        if (index == 0) {
            first = new Node<>(element, first);
        }else{
            Node<E> nodeByIndex = getNodeByIndex(index-1);
            nodeByIndex.next = new Node<>(element, nodeByIndex.next);
        }
        size++;
    }

    @Override
    public E remove(int index) {
        rangeCheck(index);
        Node<E> node = first;
        if (index == 0) {
            first = first.next;
        }else{
            Node<E> prev = getNodeByIndex(index-1);
            node = prev.next;
            prev.next = prev.next.next;
        }
        size--;
        return node.element;
    }

    @Override
    public int indexOf(E element) {
        Node<E> node = first;
        if (null == element) {
            for (int i = 0; i < size ; i++) {
                if (null == node.element) {
                    return i;
                }
                node = node.next;
            }
        }
        for (int i = 0; i < size; i++) {
            if (element.equals(node.element)) {
                return i;
            }
        }
        return ELEMENT_NOT_FOUND;
    }

    public Node<E> getNodeByIndex(int index) {
        rangeCheck(index);
        Node<E> node = first;
        for (int i = 0; i < index; i++) {
            node = node.next;
        }
        return node;
    }

    private static class Node<E> {
        E element;
        Node<E> next;

        public Node(E element, Node<E> node) {
            this.element = element;
            this.next = node;
        }
    }

    @Override
    public String toString() {
        return "LinkedList{" +
                "first=" + first +
                '}';
    }
}
