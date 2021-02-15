package com.zoran.map;

import com.zoran.tree.BinaryTree;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.Optional;
import java.util.Queue;

@SuppressWarnings("all")
public class TreeMap<K, V> implements Map<K, V> {
    public static final boolean RED = false;
    public static final boolean BLACK = true;
    private Comparator<K> comparator;

    public TreeMap() {
    }

    public TreeMap(Comparator<K> comparator) {
        this.comparator = comparator;
    }

    private static class Entry<K, V> {
        K key;
        V value;
        boolean color = RED;
        Entry<K, V> left;
        Entry<K, V> right;
        public Entry<K,V> parent;

        public Entry(K key,V value, Entry<K,V> parent) {
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

        public Entry<K,V> sibling() {
            if (isLeftChild()) {
                return parent.right;
            }
            if (isRightChild()) {
                return parent.left;
            }
            return null;
        }
    }
    protected int size;
    protected Entry<K,V> root;
    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }
    @Override
    public void clear() {

    }

    @Override
    public V put(K key, V value) {
        checkKey(key);
        if (root == null) {
            root = new Entry<>(key, value, null);
            afterPut(root);
            size++;
            return null;
        }
        Entry<K,V> entry = root;
        int compare = 0;
        Entry<K,V> parent = null;
        while (entry != null) {
            compare = compare(entry.key, key);
            parent = entry;
            if (compare > 0) {
                entry = entry.left;
            } else if (compare < 0) {
                entry = entry.right;
            } else {
                entry.key = key;
                V oldValue = entry.value;
                entry.value = value;
                return oldValue;
            }
        }
        Entry<K, V> newEntry = new Entry<>(key, value, parent);
        if (compare > 0) {
            parent.left = newEntry;
        } else if (compare < 0) {
            parent.right = newEntry;
        }
        size++;
        afterPut(newEntry);
        return null;
    }
    protected void afterPut(Entry<K,V> entry) {
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
    @Override
    public V get(K key) {
        return Optional.ofNullable(entry(key)).orElseThrow().value;
    }

    @Override
    public V remove(K key) {
        return remove(entry(key));
    }

    @Override
    public boolean containsKey(K key) {
        return entry(key) != null;
    }

    @Override
    public boolean containsValue(V value) {
        if (root == null) {
            return false;
        }
        Queue<Entry<K, V>> queue = new LinkedList<>();
        queue.offer(root);
        while (!queue.isEmpty()) {
            Entry<K, V> entry = queue.poll();
            if (valEquals(entry.value,value)) {
                return true;
            }
            if (entry.left != null) {
                queue.offer(entry.left);
            }
            if (entry.right != null) {
                queue.offer(entry.right);
            }
        }
        return false;
    }

    private boolean valEquals(V v1, V v2) {
        return v1 == null ? v2 == null : v1.equals(v2);
    }

    @Override
    public void traversal(Visitor<K, V> visitor) {
        if (visitor == null) {
            return;
        }
        traversal(root, visitor);
    }

    private void traversal(Entry<K,V> entry, Visitor<K,V> visitor) {
        if (entry == null || visitor.stop) {
            return;
        }
        traversal(entry.left, visitor);
        if (visitor.stop) {
            return;
        }
        visitor.visit(entry.key, entry.value);
        traversal(entry.right, visitor);
    }

    private void checkKey(K key) {
        if (key == null) {
            throw new IllegalArgumentException("key cant be null");
        }
    }

    private int compare(K e1, K e2) {
        if (comparator != null) {
            return comparator.compare(e1, e2);
        }
        return ((Comparable<K>) e1).compareTo(e2);
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

    protected void rotateLeft(Entry<K,V> entry) {
        Entry<K,V> parent = entry.right;
        Entry<K,V> child = parent.left;
        entry.right = child;
        parent.left = entry;
        afterRotate(entry, parent, child);
    }

    protected void rotateRight(Entry<K,V> entry) {
        Entry<K,V> parent = entry.left;
        Entry<K,V> child = parent.right;
        entry.left = child;
        parent.right = entry;
        afterRotate(entry, parent, child);
    }

    protected void afterRotate(Entry<K,V> entry, Entry<K,V> parent, Entry<K,V> child) {
        parent.parent = entry.parent;
        if (entry.isLeftChild()) {
            entry.parent.left = parent;
        } else if (entry.isRightChild()) {
            entry.parent.right = parent;
        } else {
            root = parent;
        }
        if (child != null) {
            child.parent = entry;
        }
        entry.parent = parent;
    }

    private Entry<K,V> entry(K key) {
        Entry<K,V> entry = root;
        while (entry != null) {
            int compare = compare(key, entry.key);
            if (compare == 0) {
                return entry;
            } else if (compare > 0) {
                entry = entry.right;
            } else {
                entry = entry.left;
            }
        }
        return null;
    }
    
    private V remove(Entry<K,V> entry) {
        if (entry == null) {
            return null;
        }
        V oldValue = entry.value;
        if (entry.hasTwoChildren()) {
            Entry<K,V> successor = getSuccessor(entry);
            entry.key = successor.key;
            entry = successor;
        }
        Entry<K,V> replacement = entry.left != null ? entry.left : entry.right;
        if (replacement != null) {
            replacement.parent = entry.parent;
            if (entry.parent == null) {
                root = replacement;
            } else if (entry.isLeftChild()) {
                entry.parent.left = replacement;
            } else if (entry.isRightChild()) {
                entry.parent.right = replacement;
            }
            afterRemove(entry, replacement);
        } else if (entry.parent == null) {
            //叶子节点且root节点
            root = null;
            afterRemove(entry, null);
        }else{
            //叶子节点且普通节点
            if (entry.isLeftChild()) {
                entry.parent.left = null;
            }else{
                entry.parent.right = null;
            }
            afterRemove(entry, null);
        }
        return oldValue;
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
