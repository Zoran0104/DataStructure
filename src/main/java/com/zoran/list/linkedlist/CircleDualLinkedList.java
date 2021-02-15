package com.zoran.list.linkedlist;

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
        rangeCheckForAdd(index);
        if (index == size) {
            //往最后一个位置添加元素或没有元素的时候添加元素
            last = new Node<>(last, element, first);
            //要考虑添加的是否为第一个元素
            if (last.prev == null) {
                first = last;
                first.next = first;
            } else {
                last.prev.next = last;
                first.prev = last;
            }
        } else {
            Node<E> nodeByIndex = getNodeByIndex(index);
            Node<E> node = new Node<>(nodeByIndex.prev, element, nodeByIndex);
            nodeByIndex.prev = node;
            if (index == 0) {
                first = node;
                last.next = first;
            } else {
                nodeByIndex.prev.next = node;
            }
        }
        size++;
    }

    @Override
    public E remove(int index) {
        rangeCheck(index);
        Node<E> node = getNodeByIndex(index);
        if (size != 1) {
            node.prev.next = node.next;
            node.next.prev = node.prev;
            if (node == first) {
                first = node.next;
            }
            if (node == last) {
                last = node.prev;
            }
        } else {
            first = null;
            last = null;
        }
        size--;
        return node.element;
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
