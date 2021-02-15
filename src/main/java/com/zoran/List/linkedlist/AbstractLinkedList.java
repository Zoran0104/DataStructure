package com.zoran.List.linkedlist;

import com.zoran.List.AbstractList;

public abstract class AbstractLinkedList<E> extends AbstractList<E> {
    protected static class Node<E> {
        public E element;
        public Node<E> next;

        public Node(E element, Node<E> node) {
            this.element = element;
            this.next = node;
        }
    }
    protected Node<E> first;

    public Node<E> getNodeByIndex(int index) {
        rangeCheck(index);
        Node<E> node = first;
        for (int i = 0; i < index; i++) {
            node = node.next;
        }
        return node;
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
    public int indexOf(E element) {
        Node<E> node = first;
        if (null == element) {
            for (int i = 0; i < size; i++) {
                if (null == node.element) {
                    return i;
                }
                node = node.next;
            }
            return ELEMENT_NOT_FOUND;
        }
        for (int i = 0; i < size; i++) {
            if (element.equals(node.element)) {
                return i;
            }
            node = node.next;
        }
        return ELEMENT_NOT_FOUND;
    }
}
