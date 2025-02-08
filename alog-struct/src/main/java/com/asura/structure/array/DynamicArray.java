package com.asura.structure.array;

/**
 * @author jamieLu
 * @create 2025-01-26
 */
public class DynamicArray<T> {
    private T[] data;
    private int size;
    private int capacity;

    public DynamicArray() {
        this.capacity = 8;
        this.data = (T[] ) new Object[capacity];;
        size = 0;
    }

    public DynamicArray(int capacity) {
        this.capacity = capacity;
        this.data = (T[] ) new Object[capacity];;
        size = 0;
    }

    public void addLast(T value) {
        if (size == capacity) {
            resize(capacity * 2);
        }
        data[size] = value;
        size++;
    }
    public void insert(int index, T value) {
        if (index < 0 || index > size) {
            throw new IllegalArgumentException("index is illegal");
        }
        if (size == capacity) {
            resize(capacity * 2);
        }
        for (int i = size - 1; i >= index; i--) {
            data[i + 1] = data[i];
        }
        data[index] = value;
        size++;
    }
    public T removeFirest(int index) {
        return remove(0);
    }
    public T remove(int index) {
        if (index < 0 || index >= size) {
            throw new IllegalArgumentException("index is illegal");
        }
        T ret = data[index];
        for (int i = index + 1; i < size; i++) {
            data[i - 1] = data[i];
        }
        size--;
        data[size] = null;
        // 缩一半防止过饱和
        if (size == capacity / 4 && capacity / 2 != 0) {
            resize(capacity / 2);
        }
        return ret;
    }

    public void resize(int size) {
        T[] temp = (T[]) new Object[size];
        for (int i = 0; i < size; i++) {
            temp[i] = data[i];
        }
        capacity = size;
        data = temp;
    }

    public T get(int index) {
        if (index < 0 || index >= size) {
            throw new IllegalArgumentException("index is illegal");
        }
        return data[index];
    }

    public void set(int index, T value) {
        data[index] = value;
    }

}
