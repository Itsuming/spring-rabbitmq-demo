package com.franksu.springbootrabbitmq.algorithm;

/**
 * @BelongsProject: spring-rabbitmq
 * @BelongsPackage: com.franksu.springbootrabbitmq.algorithm
 * @Author: suming9
 * @CreateTime: 2024-08-15  09:46
 * @Description: TODO
 * @Version: 1.0
 */
class SegmentTreeNoDynamic {
    private int[] tree;
    private int n;

    public SegmentTreeNoDynamic(int[] nums) {
        if (nums.length > 0) {
            n = nums.length;
            tree = new int[2 * n];
            buildTree(nums);
        }
    }

    private void buildTree(int[] nums) {
        // 构建线段树
        for (int i = n, j = 0; i < 2 * n; i++, j++) {
            tree[i] = nums[j];
        }
        for (int i = n - 1; i > 0; i--) {
            tree[i] = tree[i * 2] + tree[i * 2 + 1];
        }
    }

    public void update(int pos, int val) {
        // 更新叶子节点值
        pos += n;
        tree[pos] = val;
        while (pos > 0) {
            int left = pos;
            int right = pos;
            if (pos % 2 == 0) {
                right = pos + 1;
            } else {
                left = pos - 1;
            }
            // 更新父节点值
            tree[pos / 2] = tree[left] + tree[right];
            pos /= 2;
        }
    }

    public int sumRange(int l, int r) {
        // 计算区间和
        l += n;
        r += n;
        int sum = 0;
        while (l <= r) {
            if (l % 2 == 1) {
                sum += tree[l];
                l++;
            }
            if (r % 2 == 0) {
                sum += tree[r];
                r--;
            }
            l /= 2;
            r /= 2;
        }
        return sum;
    }
}
