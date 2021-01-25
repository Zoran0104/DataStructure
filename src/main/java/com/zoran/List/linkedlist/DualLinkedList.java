package com.zoran.List.linkedlist;

import com.zoran.List.AbstractList;

public class DualLinkedList<E> extends AbstractList<E> {
    private static class Node<E> {
        E element;
        Node<E> prev;
        Node<E> next;

        public Node(Node<E> prev, E element, Node<E> node) {
            this.prev = prev;
            this.element = element;
            this.next = node;
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            if (prev != null) {
                sb.append(prev.element);
            } else {
                sb.append("null");
            }
            sb.append("_").append(element).append("_");
            if (next != null) {
                sb.append(next.element);
            } else {
                sb.append("null");
            }
            return sb.toString();
        }
    }

    private Node<E> first;
    private Node<E> last;

    public DualLinkedList() {
    }

    public DualLinkedList(Node<E> first) {
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
    public DualLinkedList<E> reverse() {
        Node<E> node = first;

        if (node == null || node.next == null) {
            return new DualLinkedList<>(node);
        }
        Node<E> newNode = null;
        while (node != null) {
            Node<E> next = node.next;
            node.next = newNode;
            newNode = node;
            node = next;
        }
        return new DualLinkedList<E>(newNode);
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        Node<E> head = first;
        while (head != null) {
            stringBuilder.append(head).append("\t");
            head = head.next;
        }
        return "LinkedList{" +
                stringBuilder.toString() +
                '}';
    }
}
