package com.zoran.list.linkedlist;

public class SingleLinkedList<E> extends AbstractLinkedList<E> {
    @Override
    public void add(int index, E element) {
        rangeCheckForAdd(index);
        if (index == 0) {
            first = new Node<>(element, first);
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
            first = first.next;
        } else {
            Node<E> prev = getNodeByIndex(index - 1);
            node = prev.next;
            prev.next = prev.next.next;
        }
        size--;
        return node.element;
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
