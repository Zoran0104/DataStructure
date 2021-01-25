package com.zoran.List;

import java.util.logging.Logger;

public class ArrayList<E> extends AbstractList<E> {

    private E[] elements;
    private static final int DEFAULT_CAPACITY = 10;

    public ArrayList(int capacity) {
        capacity = Math.max(capacity, DEFAULT_CAPACITY);
        elements = (E[]) new Object[capacity];

    }

    @Override
    public void clear() {
        for (int i = 0; i < size; i++) {
            elements[i] = null;
        }
        size = 0;
    }

    @Override
    public E get(int index) {
        rangeCheck(index);
        return elements[index];
    }

    @Override
    public E set(int index, E element) {
        rangeCheck(index);
        E old = elements[index];
        elements[index] = element;
        return old;
    }

    @Override
    public void add(int index, E element) {
        rangeCheckForAdd(index);
        ensureCapacity(size + 1);
        for (int i = size; i > index; i--) {
            elements[i] = elements[i - 1];
        }
        elements[index] = element;
        size++;
    }

    @Override
    public E remove(int index) {
        E old = elements[index];
        if (size - 1 - index >= 0) {
            System.arraycopy(elements, index + 1, elements, index, size - 1 - index);
        }
        //删除后最后一个位置也必须设为null，否则尽管最后一个位置前移对象仍需存在，但是当后续元素需删除
        //如果不清除，则该对象一直存在，直到该位置被覆盖
        elements[--size] = null;
        trim();
        return old;
    }

    @Override
    public int indexOf(E element) {
        if (element == null) {
            for (int i = 0; i < size; i++) {
                if (elements[i] == null) {
                    return i;
                }
            }
        } else {
            for (int i = 0; i < size; i++) {
                if (element.equals(elements[i])) {
                    return i;
                }
            }
        }
        return ELEMENT_NOT_FOUND;
    }

    /**
     * 保证要有capacity的容量
     *
     * @param newCapacity
     */
    private void ensureCapacity(int newCapacity) {
        int oldCapacity = elements.length;
        if (newCapacity < oldCapacity) {
            return ;
        }
        //移位操作符的优先级是比加号低的!!!!
        newCapacity = (oldCapacity >> 1) + oldCapacity;
        E[] newElements = (E[]) new Object[newCapacity];
        if (size >= 0) {
            System.arraycopy(elements, 0, newElements, 0, size);
        }
        elements = newElements;
    }


    /**
     * 数组缩容
     */
    private void trim() {
        Logger arrayList = Logger.getLogger("ArrayList");
        int oldCapacity = elements.length;
        int newCapacity = oldCapacity >> 1;
        //临界值size和新容量是否相等时也进行缩容
        if (newCapacity <= DEFAULT_CAPACITY || size > newCapacity) {
            return;
        }
        E[] newElements = (E[]) new Object[newCapacity];
        if (size >= 0) {
            System.arraycopy(elements, 0, newElements, 0, size);
        }
        elements = newElements;
        System.out.println("trim:"+newCapacity+"origin:"+oldCapacity);
    }
    @Override
    public String toString() {
        // size=3, [99, 88, 77]
        StringBuilder string = new StringBuilder();
        string.append("size=").append(size).append(", [");
        for (int i = 0; i < size; i++) {
            if (i != 0) {
                string.append(", ");
            }

            string.append(elements[i]);

//			if (i != size - 1) {
//				string.append(", ");
//			}
        }
        string.append("]");
        return string.toString();
    }
}
