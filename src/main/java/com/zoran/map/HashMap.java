package com.zoran.map;

import java.util.Arrays;
import java.util.Map.Entry;

public class HashMap<K, V> implements Map<K, V> {
    private static final int DEFAULT_CAPACITY = 1 << 4;

    private static class Entry<K, V> {
        K key;
        V value;
        boolean color = RED;
        Entry<K, V> left;
        Entry<K, V> right;
        public Entry<K, V> parent;

        public Entry(K key, V value, Entry<K, V> parent) {
            this.key = key;
            this.value = value;
            this.parent = parent;
        }

        public boolean isLeaf() {
            return left == null && right == null;
        }

        public boolean hasTwoChildren() {
            return left != null && right != null;
        }

        public boolean isLeftChild() {
            return parent != null && this == parent.left;
        }

        public boolean isRightChild() {
            return parent != null && this == parent.right;
        }

        public Entry<K, V> sibling() {
            if (isLeftChild()) {
                return parent.right;
            }
            if (isRightChild()) {
                return parent.left;
            }
            return null;
        }
    }

    public static final boolean RED = false;
    public static final boolean BLACK = true;
    private Entry<K, V>[] table;
    private int size;

    public HashMap() {
        table = new Entry[DEFAULT_CAPACITY];
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public void clear() {
        if (size == 0) {
            return;
        }
        size = 0;
        Arrays.fill(table, null);
    }

    @Override
    public V put(K key, V value) {
        int index = index(key);
        Entry<K, V> root = table[index];
        if (root == null) {
            root = new Entry<>(key, value, null);
            table[index] = root;
            size++;
            afterPut(root);
            return null;
        }
        return null;
    }

    @Override
    public V get(K key) {
        return null;
    }

    @Override
    public V remove(K key) {
        return null;
    }

    @Override
    public boolean containsKey(K key) {
        return false;
    }

    @Override
    public boolean containsValue(V value) {
        return false;
    }

    @Override
    public void traversal(Visitor<K, V> visitor) {

    }

    private int index(K key) {
        if (key == null) {
            return 0;
        }
        int hash = key.hashCode();
        return ((hash >>> 16) ^ hash) & (table.length - 1);
    }

    private void afterPut(Entry<K,V> entry) {
        Entry<K,V> parent = entry.parent;
        //添加节点为根节点的时候
        if (parent == null) {
            black(entry);
            return;
        }
        //如果父节点是黑色，直接返回
        if (isBlack(parent)) {
            return;
        }
        Entry<K,V> uncle = parent.sibling();
        //此处是迭代的结果，因为后续的每种情况都要对grand染色为red
        Entry<K,V> grand = red(parent.parent);
        if (isRed(uncle)) {
            black(parent);
            black(uncle);
            afterPut(grand);
            return;
        }
        if (parent.isLeftChild()) {
            if (entry.isLeftChild()) {
                black(parent);
            } else {
                black(entry);
                rotateLeft(parent);
            }
            rotateRight(grand);
        } else {
            if (entry.isLeftChild()) {
                black(entry);
                rotateRight(parent);
            } else {
                black(parent);
            }
            rotateLeft(grand);
        }
    }

    private void afterRemove(Entry<K,V> entry, Entry<K,V> replacement) {
        //如果删除的节点是红色
        if (isRed(entry)) {
            return;
        }
        //用以取代entry的子节点是红色
        if (isRed(replacement)) {
            black(replacement);
            return;
        }
        Entry<K,V> parent = entry.parent;
        //删除的是根节点
        if (parent == null) {
            return;
        }
        //删除的是黑色叶子节点
        boolean left = parent.left==null||entry.isLeftChild();
        Entry<K,V> sibling = left ? parent.right : parent.left;
        if (left) {
            if (isRed(sibling)) {
                black(sibling);
                red(parent);
                rotateLeft(parent);
                sibling = parent.right;
            }
            //兄弟节点必然是黑色节点
            if (isBlack(sibling.left) && isBlack(sibling.right)) {
                //兄弟节点没有1个红色子节点，父节点要向下跟兄弟节点合并
                boolean parentBlack = isBlack(parent);
                black(parent);
                red(sibling);
                if (parentBlack) {
                    afterRemove(parent, null);
                }
            } else {
                if (isBlack(sibling.right)) {
                    rotateRight(sibling);
                    sibling = parent.right;
                }
                color(sibling, colorOf(parent));
                black(sibling.right);
                black(parent);
                rotateLeft(parent);
            }
        } else {
            if (isRed(sibling)) {
                black(sibling);
                red(parent);
                rotateRight(parent);
                sibling = parent.left;
            }
            //兄弟节点必然是黑色节点
            if (isBlack(sibling.left) && isBlack(sibling.right)) {
                //兄弟节点没有1个红色子节点，父节点要向下跟兄弟节点合并
                boolean parentBlack = isBlack(parent);
                black(parent);
                red(sibling);
                if (parentBlack) {
                    afterRemove(parent, null);
                }
            } else {
                if (isBlack(sibling.left)) {
                    rotateLeft(sibling);
                    sibling = parent.left;
                }
                color(sibling, colorOf(parent));
                black(sibling.left);
                black(parent);
                rotateRight(parent);
            }
        }
    }

    private Entry<K,V> color(Entry<K,V> entry, boolean color) {
        if (entry == null) {
            return null;
        }
        entry.color = color;
        return entry;
    }

    private Entry<K,V> red(Entry<K,V> entry) {
        return color(entry, RED);
    }

    private Entry<K,V> black(Entry<K,V> entry) {
        return color(entry, BLACK);
    }

    private boolean colorOf(Entry<K,V> entry) {
        return entry == null ? BLACK : entry.color;
    }

    private boolean isBlack(Entry<K,V> entry) {
        return colorOf(entry) == BLACK;
    }

    private boolean isRed(Entry<K,V> entry) {
        return colorOf(entry) == RED;
    }

    private void rotateLeft(Entry<K,V> entry) {
        Entry<K,V> parent = entry.right;
        Entry<K,V> child = parent.left;
        entry.right = child;
        parent.left = entry;
        afterRotate(entry, parent, child);
    }

    private void rotateRight(Entry<K,V> entry) {
        Entry<K,V> parent = entry.left;
        Entry<K,V> child = parent.right;
        entry.left = child;
        parent.right = entry;
        afterRotate(entry, parent, child);
    }

    private void afterRotate(Entry<K,V> entry, Entry<K,V> parent, Entry<K,V> child) {
        parent.parent = entry.parent;
        if (entry.isLeftChild()) {
            entry.parent.left = parent;
        } else if (entry.isRightChild()) {
            entry.parent.right = parent;
        } else {
            //root = parent;
        }
        if (child != null) {
            child.parent = entry;
        }
        entry.parent = parent;
    }

    private Entry<K,V> getSuccessor(Entry<K,V> entry) {
        if (entry == null) {
            return null;
        }
        if (entry.right != null) {
            entry = entry.right;
            while (entry.left != null) {
                entry = entry.left;
            }
            return entry;
        }
        while (entry.parent != null && entry.parent.left != entry) {
            entry = entry.parent;
        }
        return entry.parent;
    }
}
