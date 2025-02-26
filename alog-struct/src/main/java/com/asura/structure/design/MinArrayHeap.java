package com.asura.structure.design;

import java.util.Arrays;

/**
 * 小顶堆
 *
 * @author jamieLu
 * @create 2025-01-30
 */
public class MinArrayHeap {
    private int[] heap;
    private int size;
    private int capacity;
    public MinArrayHeap(int capacity) {
        this.capacity = capacity;
        this.size = 0;
        heap = new int[capacity];
    }
    public int parent(int index) {
        return (index - 1) / 2;
    }
    public int leftChild(int index) {
        return index * 2 + 1;
    }
    public int rightChild(int index) {
        return index * 2 + 2;
    }
    public void add(int value) {
        if (size == capacity) {
            throw new IllegalArgumentException("heap is full");
        }
        heap[size] = value;
        size++;
        siftUp(size - 1);
    }
    public void remove() {
        if (size == 0) {
            throw new IllegalArgumentException("heap is empty");
        }
        heap[0] = heap[size - 1];
        size--;
        siftDown(0);
    }
    private void siftDown(int index) {
        while (leftChild(index) < size) {
            int left = leftChild(index);
            int right = rightChild(index);
            int min = left;
            if (right < size && heap[right] < heap[left]) {
                min = right;
            }
            if (heap[index] < heap[min]) {
                break;
            }
            swap(index, min);
            index = min;
        }
    }
    private void siftUp(int index) {
        while (index > 0 && heap[parent(index)] > heap[index]) {
            swap(parent(index), index);
            index = parent(index);
        }
    }
    private void swap(int i, int j) {
        int temp = heap[i];
        heap[i] = heap[j];
        heap[j] = temp;
    }
    public void print() {
        System.out.println(Arrays.toString(heap));
    }
}
