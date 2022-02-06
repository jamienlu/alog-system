package com.asura.structure.find;

public class Find2Format {
    public int basicFind2(int [] arr, int target) {
        int left = 0;
        int right = arr.length -1;
        while (left <= right) {
            int mid = left + ((right -left) >> 1);
            if (arr[mid] == target) {
                return mid;
            }
            if (arr[mid] < target) {
                left = mid + 1;
            } else {
                right = mid -1;
            }
        }
        return -1;
    }

    public int lowerBoundFind2(int [] arr, int target) {
        int left = 0;
        int right = arr.length;
        while (left < right) {
            int mid = left + ((right -left) >> 1);
            if (arr[mid] >= target) {
                right = mid;
            } else {
                left = mid + 1;
            }
        }
        return right;
    }

    public int upperBoundFind2(int [] arr, int target) {
        int left = -1;
        int right = arr.length - 1;
        while (left < right) {
            int mid = left + ((right + 1 - left) >> 1);
            if (arr[mid] <= target) {
                left = mid;
            } else {
                right = mid - 1;
            }
        }
        return right;
    }
}
