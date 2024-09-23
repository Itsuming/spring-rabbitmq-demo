package com.franksu.springbootrabbitmq.juc;

/**
 * @BelongsProject: spring-rabbitmq
 * @BelongsPackage: com.franksu.springbootrabbitmq.juc
 * @Author: suming9
 * @CreateTime: 2024-08-29  16:53
 * @Description: TODO
 * @Version: 1.0
 */
public class MinBalanceSubArr {
    private int n;
    private int[] memo;
    private String s;
    public int getMinBalance(String s) {
        int ans = 0;
        n = s.length();
        memo = new int[n];
        this.s = s;
        return dfs(0);
    }

    public int dfs(int i) {
        if (i >= n) {
            return 0;
        }
        // 判断是否已经计算过
        if (memo[i] != 0) {
            return memo[i];
        }
        // 定义数组记录个数
        int[] cnt = new int[26];
        // 定义字符串包含不同字符个数
        int diff = 0;
        int res = Integer.MAX_VALUE;
        int maxCnt = 0;
        for (int j = i; j < n; ++j) {
            // 若个数为0，则表示是个新字符，字符串不同字符个数+1
            int num = cnt[s.charAt(j) - 'a'];
            if (num == 0) {
                diff++;
            }
            // 将此字符数+1
            cnt[s.charAt(j) - 'a'] += 1;
            // 获取最大数（也是平衡子串不同字符的个数）
            maxCnt = Math.max(num + 1, maxCnt);
            // 计算
            if (maxCnt * diff == j - i + 1) {
                // 是平衡子串
                // 递归获取后续部分平衡子串个数
                int subRes = dfs(j + 1);
                res = Math.min(res, subRes + 1);
            }
        }
        // 更新memo
        memo[i] = res;
        return res;
    }

    public static void main(String[] args) {
        String s = "fabccddg";
        String s2 = "abababaccddb";
        MinBalanceSubArr minBalanceSubArr = new MinBalanceSubArr();
        System.out.println(minBalanceSubArr.getMinBalance(s));
        System.out.println(minBalanceSubArr.getMinBalance(s2));
    }
}