package com.franksu.springbootrabbitmq.algorithm;

import java.util.ArrayList;
import java.util.List;

/**
 * @BelongsProject: spring-rabbitmq
 * @BelongsPackage: com.franksu.springbootrabbitmq.algorithm
 * @Author: suming9
 * @CreateTime: 2024-07-30  08:31
 * @Description: TODO
 * @Version: 1.0
 */
public class Permunation {

    public static List<List<Integer>> permunate(int[] nums) {
        // 回溯
        int n = nums.length;
        boolean[] visited = new boolean[n];
        List<List<Integer>> ans = new ArrayList<List<Integer>>();
        backtrack(nums, ans, new ArrayList<Integer>(), 0, visited);
        return ans;
    }

    public static void backtrack(int[] nums, List<List<Integer>> ans, List<Integer> list, int index, boolean[] visited) {
        // 若排序完成则直接返回
        if (index == nums.length) {
            ans.add(new ArrayList<>(list));
            return;
        }
        // 开始遍历
        for (int i = 0; i < nums.length; i++) {
            if (visited[i]) {
                continue;
            }
            visited[i] = true;
            list.add(nums[i]);
            backtrack(nums, ans, list, index + 1, visited);
            list.remove(list.size() - 1);
            visited[i] = false;
        }
    }

    public static void main(String[] args) {
        int[] nums = new int[]{1, 2, 3};
        List<List<Integer>> permunateList = permunate(nums);
        for (List<Integer> list : permunateList) {
            System.out.println(list.toString());
        }
    }
}