package com.asura.structure.sort;

import java.util.PriorityQueue;
import java.util.Queue;

public class SortFormat {

	public static void chooseSort(int[] target) {
		int n = target.length;
		for (int i = 0; i < n; i++) {
			int min = i;
			for (int j = i+1; j < n; j++) {
				if (target[j] < target[min]) {
					min = j;
				}
			}
			if (i != min) {
				exch(target, i, min);
			}
		}
	}

	private static void exch(int[] target, int i, int min) {
		target[i] = target[i] ^ target[min];
		target[min] = target[i] ^ target[min];
		target[min] = target[i] ^ target[min];
	}

	public static void insertSort(int[] target) {
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

	public static void shellSort(int[] target) {
		int n = target.length;
		int h = 1;
		while (h < n/3) {
			h = 3*h +1;
		}
		while (h >=1) {
			for (int i = h; i < n; i++) {
				for (int j = i; j >= h && target[j] < target[j-h]; j-=h) {
					exch(target, j, j - h);
				}
			}
			h /= 3;

		}
	}

    public static void quickSort(int[] target, int start, int end) {
        if (start >= end) {
            return;
        }
        int pivot = partition(target, start, end);
        quickSort(target, start, pivot);
        quickSort(target, pivot + 1, end);
    }

    public static int partition(int[] target, int start, int end) {
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
