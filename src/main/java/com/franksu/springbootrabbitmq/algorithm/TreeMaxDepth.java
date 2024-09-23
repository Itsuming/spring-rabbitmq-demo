package com.franksu.springbootrabbitmq.algorithm;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Queue;

/**
 * @BelongsProject: spring-rabbitmq
 * @BelongsPackage: com.franksu.springbootrabbitmq.algorithm
 * @Author: suming9
 * @CreateTime: 2024-08-25  13:37
 * @Description: TODO
 * @Version: 1.0
 */
public class TreeMaxDepth {

    public static int maxDepth(TreeNode root) {
        Deque<TreeNode> queue = new ArrayDeque<TreeNode>();
        int ans = 0;
        queue.push(root);
        while (!queue.isEmpty()) {
            int size = queue.size();
            while (size > 0) {
                TreeNode node = queue.pop();
                if (node.left != null) {
                    queue.push(node.left);
                }
                if (node.right != null) {
                    queue.push(node.right);
                }
                size--;
            }
            ans++;
        }
        return ans;
    }

    public static void main(String[] args) {

    }
}

class TreeNode {

    int val;
    TreeNode left;
    TreeNode right;
    public TreeNode(int val) {
        this.val = val;
    }

}