package com.zoran.List.linkedlist;

import com.zoran.List.AbstractList;

public class CircleSingleLinkedList<E> extends AbstractList<E> {
    private static class Node<E> {
        E element;
        Node<E> next;

        public Node(E element, Node<E> node) {
            this.element = element;
            this.next = node;
        }

        @Override
        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(element).append("_").append(next.element);
            return stringBuilder.toString();
        }
    }

    private Node<E> first;

    public CircleSingleLinkedList() {
    }

    public CircleSingleLinkedList(Node<E> first) {
        this.first = first;
    }

    @Override
    public void clear() {
        //理论上这是没必要进行断开各个链表的节点链接，
        //但避免有迭代器对象指向中间元素导致元素无法被gc回收
        Node<E> node = first;
        Node<E> tmp;
        while (node != null) {
            tmp = node.next;
            node.next = null;
            node.element = null;
            node = tmp;
        }
        first = null;
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
            Node newNode  = new Node<>(element, first);
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
    public int indexOf(E element) {
        Node<E> node = first;
        if (null == element) {
            for (int i = 0; i < size; i++) {
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

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        Node<E> head = first;
        do {
            stringBuilder.append(head).append("\t");
            head = head.next;
        } while (head != first);
        return stringBuilder.toString();
    }
}
