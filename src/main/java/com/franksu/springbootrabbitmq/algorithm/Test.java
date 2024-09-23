package com.franksu.springbootrabbitmq.algorithm;

import io.swagger.models.auth.In;

import java.util.Arrays;
import java.util.List;

/**
 * @BelongsProject: spring-rabbitmq
 * @BelongsPackage: com.franksu.springbootrabbitmq.algorithm
 * @Author: suming9
 * @CreateTime: 2024-08-16  08:38
 * @Description: TODO
 * @Version: 1.0
 */
public class Test {
    // m*n个网格，随机选取一点，只能正下或正右移动（可以不相邻），每次移动后，累计移动后位置与移动前位置差值
    // 最终返回可能的最大差值

    public static int getMaxDiff(List<List<Integer>> grid) {
        int ans = Integer.MIN_VALUE;

        // 动态规划
        // 从左或从右获取最大值后加上当前位置与前一位置差值
        // 建立preRow[i][j]维护i，j位置上方的最大值
        // 建立preCol[i][j]维护i,j位置左侧的最大值，避免复杂度过高
        int m = grid.size();
        int n = grid.get(0).size();
        int[][] dp = new int[m][n];
        int[][] preRow = new int[m][n];
        int[][] preCol = new int[m][n];
        for (int i = 0; i < m; ++i) {
            Arrays.fill(dp[i], Integer.MIN_VALUE);
        }
        for (int i = 0; i < m; ++i) {
            for (int j = 0; j < n; ++j) {
                if (i > 0) {
                    dp[i][j] = Math.max(dp[i][j], grid.get(i).get(j) + preRow[i - 1][j]);
                }
                if (j > 0) {
                    dp[i][j] = Math.max(dp[i][j], grid.get(i).get(j) + preCol[i][j - 1]);
                }
                ans = Math.max(ans, dp[i][j]);
                preRow[i][j] = preCol[i][j] = Math.max(dp[i][j], 0) - grid.get(i).get(j);
                if (i > 0) {
                    preRow[i][j] = Math.max(preRow[i][j], preRow[i - 1][j]);
                }
                if (j > 0) {
                    preCol[i][j] = Math.max(preCol[i][j], preCol[i][j - 1]);
                }
            }
        }

        return ans;
    }

    public static void main(String[] args) {

    }
}