package com.asura.structure.list;

import java.util.NoSuchElementException;

/**
 * @author jamieLu
 * @create 2025-01-26
 */
public class DoubleListNode<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    private static class Node<T> {
        T val;
        Node<T> next;
        Node<T> prev;

        Node(T val) {
            this.val = val;
        }
    }
    public void addLast(T value) {
        Node<T> cur = new Node<>(value);
        // 拿到尾节点的前一个节点
        Node<T> temp = tail.prev;

        // 节点指向设置的尾节点
        temp.next = cur;
        cur.prev = temp;

        // 尾节点修正
        cur.next = tail;
        tail.prev =cur;
        size++;
    }

    public void addFirst(T value) {
        Node<T> cur = new Node<>(value);
        Node<T> temp = head.next;

        temp.prev = cur;
        cur.next = temp;

        head.next =cur;
        cur.prev = head;
        size++;
    }

    public void add(int index, T value) {
        checkPositionIndex(index);
        if (index == size) {
            addLast(value);
        } else {
            Node<T> cur = new Node<>(value);
            Node<T> temp = getNode(index);
            Node<T> prev = temp.prev;

            // 插入位置的前一个节点指向当前节点
            prev.next = cur;
            cur.prev = prev;

            // 当前节点的下一个节点指向位置节点
            cur.next = temp;
            temp.prev = cur;
            size++;
        }
    }
    public T remove(int index) {
        checkElementIndex(index);
        Node<T> cur = getNode(index);
        Node<T> prev = cur.prev;
        Node<T> next = cur.next;

        // 链接前后节点
        prev.next = next;
        next.prev = prev;
        // 移除当前节点
        cur.prev = cur.next = null;
        size --;
        return cur.val;
    }

    public T removeFirst() {
        if (size < 1) {
            throw new NoSuchElementException();
        }
        Node<T> cur = head.next;
        Node<T> temp = cur.next;
        head.next = temp;
        temp.prev = head;

        cur.prev = null;
        cur.next = null;
        size--;
        return cur.val;
    }

    public T removeLast() {
        if (size < 1) {
            throw new NoSuchElementException();
        }
        Node<T> cur = tail.prev;
        Node<T> temp = cur.prev;

        // 链接前后节点
        tail.prev = temp;
        temp.next = tail;
        // 移除当前节点
        cur.prev = null;
        cur.next = null;
        size --;
        return cur.val;
    }

    public T get(int index) {
        checkElementIndex(index);
        // 找到 index 对应的 Node
        Node<T> p = getNode(index);
        return p.val;
    }
    private Node<T> getNode(int index) {
        checkElementIndex(index);
        Node<T> p = head.next;
        for (int i = 0; i < index; i++) {
            p = p.next;
        }
        return p;
    }


    private boolean isElementIndex(int index) {
        return index >= 0 && index < size;
    }
    // 检查 index 索引位置是否可以存在元素
    private void checkElementIndex(int index) {
        if (!isElementIndex(index))
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
    }
    private boolean isPositionIndex(int index) {
        return index >= 0 && index <= size;
    }
    // 检查 index 索引位置是否可以添加元素
    private void checkPositionIndex(int index) {
        if (!isPositionIndex(index))
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
    }


}
