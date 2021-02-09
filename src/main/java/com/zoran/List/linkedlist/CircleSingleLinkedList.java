package com.zoran.List.linkedlist;

public class CircleSingleLinkedList<E> extends AbstractLinkedList<E> {
    @Override
    public void add(int index, E element) {
        rangeCheckForAdd(index);
        if (index == 0) {
            Node newNode = new Node<>(element, first);
            //注意一开始链表为空的情况
            Node<E> node = size == 0 ? newNode : getNodeByIndex(size - 1);
            first = newNode;
            node.next = first;
        } else {
            Node<E> nodeByIndex = getNodeByIndex(index - 1);
            nodeByIndex.next = new Node<>(element, nodeByIndex.next);
        }
        size++;
    }

    @Override
    public E remove(int index) {
        rangeCheck(index);
        Node<E> node = first;
        if (index == 0) {
            Node<E> nodeByIndex = getNodeByIndex(size - 1);
            first = first.next;
            nodeByIndex.next = first;
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
