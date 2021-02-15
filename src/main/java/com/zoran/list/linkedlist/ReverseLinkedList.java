package com.zoran.list.linkedlist;

import com.zoran.list.linkedlist.AbstractLinkedList.Node;

public class ReverseLinkedList {
    //逆序链表-递归
    public static Node reverseRecursion(Node first) {
        Node node = first;
        if (node == null || node.next == null) {
            return node;
        }
        Node reverse = reverseRecursion(node.next);
        node.next.next = reverse;
        node.next = null;
        return reverse;
    }

    //逆序链表-头插法
    public static Node reverse(Node first) {
        Node node = first;

        if (node == null || node.next == null) {
            return node;
        }
        Node newNode = null;
        while (node != null) {
            Node next = node.next;
            node.next = newNode;
            newNode = node;
            node = next;
        }
        return newNode;
    }
}
