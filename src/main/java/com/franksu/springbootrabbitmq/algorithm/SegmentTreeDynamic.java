package com.franksu.springbootrabbitmq.algorithm;

/**
 * @BelongsProject: spring-rabbitmq
 * @BelongsPackage: com.franksu.springbootrabbitmq.algorithm
 * @Author: suming9
 * @CreateTime: 2024-08-14  09:15
 * @Description: 线段树
 * @Version: 1.0
 */
public class SegmentTreeDynamic {
    class Node {
        Node left, right;
        int val, add;
    }
    private int N = (int) 1e9;
    private Node root = new Node();

    /**
     * 在区间 [start, end] 中更新区间 [l, r] 的值，将区间 [l, r] ➕ val
     * 对于上面的例子，应该这样调用该函数：update(root, 0, 4, 2, 4, 1)
     * @param node 当前节点
     * @param start 当前节点代表的区间的起始点
     * @param end 当前节点代表区间结束点
     * @param l 需要更新操作区间起始点
     * @param r 需要更细操作区间结束点
     * @param val 更新值
     */
    public void update(Node node, int start, int end, int l, int r, int val) {
        // 如果[l, r]区间完全包含了区间[start, end]，则只需要更新node节点所代表的区间即可
        if (l <= start && end <= r) {
            node.val += (end - start + 1) * val;
            node.add += val;
            return ;
        }
        // 否则需要进行下推和向上更新来完成区间内部分子区间的更新操作，左侧与右侧区间与需更新区间的交集
        int mid = (start + end) >> 1;
        // 下推
        pushDown(node, mid - start + 1, end - mid);
        // 完成当前所需区间的更新（不需要完全更新）
        if (l <= mid) update(node.left, start, mid, l, r, val);
        if (r > mid) update(node.right, mid + 1, end, l, r, val);
        pushUp(node);
    }
    public int query(Node node, int start, int end, int l, int r) {
        if (l <= start && end <= r) return node.val;
        int mid = (start + end) >> 1, ans = 0;
        pushDown(node, mid - start + 1, end - mid);
        if (l <= mid) ans += query(node.left, start, mid, l, r);
        if (r > mid) ans += query(node.right, mid + 1, end, l, r);
        return ans;
    }
    private void pushUp(Node node) {
        node.val = node.left.val + node.right.val;
    }
    private void pushDown(Node node, int leftNum, int rightNum) {
        if (node.left == null) node.left = new Node();
        if (node.right == null) node.right = new Node();
        if (node.add == 0) return ;
        node.left.val += node.add * leftNum;
        node.right.val += node.add * rightNum;
        // 对区间进行「加减」的更新操作，下推懒惰标记时需要累加起来，不能直接覆盖
        node.left.add += node.add;
        node.right.add += node.add;
        node.add = 0;
    }
}