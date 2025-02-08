package com.asura.structure.array;

import java.util.ArrayList;
import java.util.List;

/**
 * @author jamieLu
 * @create 2025-02-02
 */
public class ReverseArrayCase {
    /**
     * 48 旋转图像
     */
    public void rotate(int[][] matrix) {
        // 对角旋转 + 左右旋转等于顺时针90读
        for (int i = 0; i < matrix.length; i++) {
            for (int j = i; j < matrix[i].length; j++) {
                int temp = matrix[i][j];
                matrix[i][j] = matrix[j][i];
                matrix[j][i] = temp;
            }
        }

        for (int i = 0; i < matrix.length; i++) {
            int left = 0;
            int right = matrix[i].length - 1;
            while (left < right) {
                int temp = matrix[i][left];
                matrix[i][left] = matrix[i][right];
                matrix[i][right] = temp;
                left++;
                right--;
            }
        }
    }
    /**
     * 151. 翻转字符串里的单词
     */
    public String reverseWords(String s) {
        char[] chars = s.trim().replaceAll("\\s+"," ").toCharArray();
        int start = 0;
        int end = chars.length - 1;
        while (start < end) {
            char temp = chars[start];
            chars[start] = chars[end];
            chars[end] = temp;
            start++;
            end--;
        }
        int left = 0;
        int right = 0;
        while (left < chars.length && right < chars.length) {
            for (int i = left; i < chars.length; i++) {
                if (chars[i] == ' ') {
                    right = i;
                    break;
                } else {
                    right = chars.length;
                }
            }
            reverseWords(chars, left, right - 1);
            left = right + 1;
        }
        return new String(chars);
    }
    public void reverseWords(char[] chars, int start, int end) {
        while (start < end) {
            char temp = chars[start];
            chars[start] = chars[end];
            chars[end] = temp;
            start++;
            end--;
        }
    }

    public List<Integer> spiralOrder(int[][] matrix) {
        // 思路 给矩阵定4个角 上下左右
        // 上 -> 右 -> 下 -> 左 -> 上  循环
        // 每走一边对应边界减1
        int upper = 0;
        int right = matrix[0].length - 1;
        int bottom = matrix.length - 1;
        int left = 0;
        List<Integer> res = new ArrayList<>();
        while (res.size() < matrix.length * matrix[0].length) {
            if (upper <= bottom) {
                for (int i = left; i <= right; i++) {
                    res.add(matrix[upper][i]);
                }
                //
                upper++;
            }
            if (left <= right) {
                for (int i = upper; i <= bottom; i++) {
                    res.add(matrix[i][right]);
                }
                right--;
            }
            if (upper <= bottom) {
                for (int i = right; i >= left; i--) {
                    res.add(matrix[bottom][i]);
                }
                bottom--;
            }
            if (left <= right) {
                for (int i = bottom; i >= upper; i--) {
                    res.add(matrix[i][left]);
                }
                left++;
            }
        }
        return res;
    }
    public static void main(String[] args) {
       String tett =  new ReverseArrayCase().reverseWords("the sky is blue");
        System.out.println(tett);
       String s = new String("abd   ddd dff fg  g");
        System.out.println(s.replaceAll("\\s+"," "));;
    }


}
