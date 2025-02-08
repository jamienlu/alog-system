package com.asura.structure.array;

import java.util.NoSuchElementException;

/**
 * @author jamieLu
 * @create 2025-01-27
 */
public class CycleArray<T> {
    private T[] arr;
    private int start;
    private int end;
    private int count;
    private int size;

    public CycleArray(int size) {
        this.size = size;
        this.arr = (T[]) new Object[size];
        // start 指向第一个有效元素的索引，闭区间
        this.start = 0;
        // 切记 end 是一个开区间，
        // 即 end 指向最后一个有效元素的下一个位置索引
        this.end = 0;
        this.count = 0;
    }

    private void resize(int newSize) {
        // 创建新的数组
        T[] newArr = (T[]) new Object[newSize];
        // 将旧数组的元素复制到新数组中
        for (int i = 0; i < count; i++) {
            newArr[i] = arr[(start + i) % size];
        }
        arr = newArr;
        // 重置 start 和 end 指针
        start = 0;
        end = count;
        size = newSize;
    }

    public void addFirst(T value) {
        if (count == size) {
            resize(size * 2);
        }
        start = (start - 1 + size) % size;
        arr[start] = value;
        count++;
    }
    public void addLast(T value) {
        if (count == size) {
            resize(size * 2);
        }
        arr[end] = value;
        end = (end + 1) % size;
        count++;
    }

    public T removeFirst() {
        if (count == 0) {
            throw new NoSuchElementException();
        }
        T ret = arr[start];
        start = (start + 1) % size;
        count--;
        if (count == size / 4 && size / 2 != 0) {
            resize(size / 2);
        }
        return ret;
    }
    public T removeLast() {
        if (count == 0) {
            throw new NoSuchElementException();
        }
        end = (end -1 + size ) % size;
        T ret = arr[end];
        count--;
        if (count == size / 4 && size / 2 != 0) {
            resize(size / 2);
        }
        return ret;
    }

    public T get(int index) {
        if (index < 0 || index >= count) {
            throw new IllegalArgumentException("index is illegal");
        }
        return arr[(start + index) % size];
    }
}
