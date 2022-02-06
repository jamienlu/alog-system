package com.asura.structure.sort;

import java.util.PriorityQueue;
import java.util.Queue;

public class SortFormat {
    public void insertSort(int[] target) {
        for (int i = 1; i < target.length; i++) {
            int value = target[i];
            int j = i -1;
            for (; j >=0 ; j--) {
                if (target[j] > value) {
                    target[j + 1] = target[j];
                } else {
                    break;
                }
            }
            target[j + 1] = value;
        }
    }

    public void quickSort(int[] target, int start, int end) {
        if (start >= end) {
            return;
        }
        int pivot = partition(target, start, end);
        quickSort(target, start, pivot);
        quickSort(target, pivot + 1, end);
    }

    public int partition(int[] target, int start, int end) {
        int pivot = start + (int)(Math.random() * (end - start + 1));
        int pivotVal = target[pivot];
        while (start <= end) {
            while (target[start] < pivotVal) {
                start++;
            }
            while (target[end] > pivotVal) {
                end--;
            }
            if (start == end) {
                break;
            }
            if (start < end) {
                int temp = target[start];
                target[start] = target[end];
                target[end] = temp;
                start++;
                end--;
            }
        }
        return end;
    }

    public void recurSort(int[] target, int start, int end) {
        if (start >= end) {
            return;
        }
        int mid = (start + end) >> 1;
        recurSort(target, start, mid);
        recurSort(target, mid + 1, end);
        merge(target, start, end, mid);
    }

    public void merge(int[] target, int start, int end, int mid) {
        int[] status = new int[end - start + 1];
        int i = start;
        int j = mid + 1;
        for (int k = 0; k < status.length; k++) {
            if (j > end || (i <= mid && target[i] <= target[j])) {
                status[k] = target[i++];
            } else {
                status[k] = target[j++];
            }
        }
        System.arraycopy(status, 0, target, start, status.length);
    }

    public void batchSort(int[] target) {
        Queue<Integer> queue = new PriorityQueue<>();
        for(int i = 0; i < target.length; i++) {
            queue.add(-target[i]);
        }
        for(int i = 0; i < target.length; i++) {
            target[i] = -queue.peek();
            queue.poll();
        }
    }
}
