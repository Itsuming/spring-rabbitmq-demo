package com.franksu.springbootrabbitmq.algorithm;

/**
 * @BelongsProject: spring-rabbitmq
 * @BelongsPackage: com.franksu.springbootrabbitmq.algorithm
 * @Author: suming9
 * @CreateTime: 2024-08-21  09:27
 * @Description: TODO
 * @Version: 1.0
 */
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class WaysToReachStair {


    public int waysToReachStair(int k) {
        // 记忆化搜索
        // 定义一个map存储记忆化搜索结果
        // 若当前所在位置已经大于k + 1，则不可能再有后续情况，直接跳过
        // 当前位置前一位置是情况1（向下走），执行情况2
        // 前一位位置不是情况1，执行情况1
        return dfs(1, 0, 0, k, new HashMap<>());
    }
    public int waysToReachStair2(int k) {
        // 记忆化搜索
        // 定义一个map存储记忆化搜索结果
        // 若当前所在位置已经大于k + 1，则不可能再有后续情况，直接跳过
        // 当前位置前一位置是情况1（向下走），执行情况2
        // 前一位位置不是情况1，执行情况1
        return dfs2(1, 0, 0, k, new HashMap<>());
    }

    /**
     * i：当前位置 j: 情况2的次数 preDown：前一步是否向下（0/1 false/true）
     * k: 目标台阶 memo：记忆map
     */
    public int dfs(int i, int j, int preDown, int k, Map<Long, Integer> memo) {
        // 若是大于k + 1,则无法到达终点，直接返回
        if (i > k + 1) {
            return 0;
        }
        // 将状态(i, j, preDown)压缩成一个long
        long mask = (long) i << 32 | j << 1 | preDown;
        // 判断是否已经计算过
        if (memo.containsKey(mask)) {
            return memo.get(mask);
        }
        // 判断当前位置是否是k，是则设置res为1，否则为0
        int res = i == k ? 1 : 0;
        // 进行搜索
        // 执行操作2
        res += dfs(i + 1 << j, j + 1, 0, k, memo);
        // 判断是否可以执行操作1
        if (preDown == 0) {
            res += dfs(i - 1, j, 1, k, memo);
        }
        // 更新记忆
        memo.put(mask, res);
        return res;
    }

    public int dfs2(int i, int j, int preDown, int k, Map<Long, Integer> memo) {
        // 判断当前位置是否已经大于k + 1
        if (i > k + 1) {
            return 0;
        }
        // 判断是否已经计算
        // 转换为压缩key
        long s = (long) i << 32 | j << 1 | preDown;
        if (memo.containsKey(s)) {
            return memo.get(s);
        }
        // 初始化当前的值
        int res = i == k ? 1 : 0;
        // 进行搜索（前一位置不是下降位）
        res += dfs(i + 1 << j, j + 1, 0, k, memo);
        if (preDown == 0) {
            res += dfs(i - 1, j, 1, k, memo);
        }
        // 更新记忆
        memo.put(s, res);
        return res;
    }

    public static void main(String[] args) {
        // 0--2  1--4
        WaysToReachStair sol = new WaysToReachStair();
        int k = 7;  // Example input
        System.out.println(sol.waysToReachStair(k));  // Example output
        System.out.println(sol.waysToReachStair2(k));  // Example output
    }
}
