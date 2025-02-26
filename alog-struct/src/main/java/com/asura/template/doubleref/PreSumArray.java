package com.asura.template.doubleref;

/**
 * @author jamieLu
 * @create 2025-02-05
 */
public class PreSumArray {
    private final int[][] preSum;

    public PreSumArray(int[][] matrix) {
        preSum = new int[matrix.length + 1][matrix[0].length + 1];
        for (int i = 1; i < matrix.length; i++) {
            for (int j = 1; j < matrix[i].length; j++) {
                preSum[i][j] = preSum[i - 1][j] + preSum[i][j - 1] - preSum[i - 1][j - 1] + matrix[i-1][j-1];

            }
        }
    }
    /**
     * 计算矩阵中指定区域的和
     */
    public int sumRegion(int row1, int col1, int row2, int col2) {
        // 位置转索引
        return preSum[row2+1][col2+1] + preSum[row1][col1] - preSum[row1][col2+1] - preSum[row2 + 1][col1];
    }

}
