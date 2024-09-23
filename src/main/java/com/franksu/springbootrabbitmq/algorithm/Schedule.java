package com.franksu.springbootrabbitmq.algorithm;

/**
 * @BelongsProject: spring-rabbitmq
 * @BelongsPackage: com.franksu.springbootrabbitmq.algorithm
 * @Author: suming9
 * @CreateTime: 2024-08-20  09:01
 * @Description: TODO
 * @Version: 1.0
 */
public class Schedule {

    private final int MOD = 1000000007;
    public int checkRecord(int n) {
        // 定义dp[i][j][k]表示第i个位置结尾处j个A、k个连续L的情况
        int[][][] dp = new int[n][2][3];
        // 初始化，在第一个位置没有A和L的情况下奖励1次
        dp[0][0][0] = 1;
        // 开始进行遍历
        for (int i = 1; i < n; ++i) {
            // 以P结尾
            for (int j = 0; j < 2; ++j) {
                for (int k = 0; k < 3; ++k) {
                    dp[i][j][k] = (dp[i][j][0] + dp[i - 1][j][k]) % MOD;
                }
            }
            // 以A结尾
            for (int k = 0; k < 3; ++k) {
                dp[i][1][0] = (dp[i][1][0] + dp[i - 1][0][k]) % MOD;
            }
            // 以L结尾
            for (int j = 0; j < 2; ++j) {
                for (int k = 1; k < 3; ++k) {
                    dp[i][j][k] = (dp[i][j][k] + dp[i - 1][j][k - 1]) % MOD;
                }
            }
        }

        // 计算总数
        int sum = 0;
        for (int j = 0; j < 2; ++j) {
            for (int k = 0; k < 3; ++k) {
                sum = (sum + dp[n][j][k]) % MOD;
            }
        }
        return sum;


    }
}