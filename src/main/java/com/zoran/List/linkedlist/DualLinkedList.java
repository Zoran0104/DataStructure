package com.zoran.List.linkedlist;

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
        //理论上这是没必要进行断开各个链表的节点链接，
        //但避免有迭代器对象指向中间元素导致元素无法被gc回收
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
            last = new Node<>(last, element, null);
            //要考虑添加的是否为第一个元素
            if (last.prev == null) {
                first = last;
            } else {
                last.prev.next = last;
            }
        } else {
            Node<E> nodeByIndex = getNodeByIndex(index);
            Node<E> node = new Node<>(nodeByIndex.prev, element, nodeByIndex);
            nodeByIndex.prev = node;
            if (index == 0) {
                first = node;
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
        if (node.prev == null) {
            first = node.next;
        } else {
            node.prev.next = node.next;
        }

        if (node.next == null) {
            last = node.prev;
        } else {
            node.next.prev = node.prev;
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
