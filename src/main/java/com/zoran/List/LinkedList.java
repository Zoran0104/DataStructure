package com.zoran.List;

public class LinkedList<E> extends AbstractList<E> {
    private static class Node<E> {
        E element;
        Node<E> next;

        public Node(E element, Node<E> node) {
            this.element = element;
            this.next = node;
        }
    }

    private Node<E> first;

    public LinkedList() {
    }

    public LinkedList(Node<E> first) {
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

    //逆序链表-递归
    public Node<E> reverse(Node head) {
        Node<E> node = head;
        if (node == null || node.next == null) {
            return node;
        }
        Node<E> reverse = reverse(node.next);
        node.next.next = reverse;
        node.next = null;
        return reverse;
    }

    //逆序链表-头插法
    public LinkedList<E> reverse() {
        Node<E> node = first;

        if (node == null || node.next == null) {
            return new LinkedList<>(node);
        }
        Node<E> newNode = null;
        while (node != null) {
            Node<E> next = node.next;
            node.next = newNode;
            newNode = node;
            node = next;
        }
        return new LinkedList<E>(newNode);
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
