package com.franksu.springbootrabbitmq.algorithm;

/**
 * @BelongsProject: spring-rabbitmq
 * @BelongsPackage: com.franksu.springbootrabbitmq.algorithm
 * @Author: suming9
 * @CreateTime: 2024-08-15  08:42
 * @Description: TODO
 * @Version: 1.0
 */
public class SegmentTree {

    private Node root;
    private int start, end;

    public SegmentTree(int start, int end) {
        this.start = start;
        this.end = end;
        this.root = new Node(0);
    }
    // 定义下推函数
    public void pushDown(Node node, int leftNum, int rightNum) {
        // 判断节点是否为空，为空则新建
        if (node.left == null) {
            node.left = new Node(0);
        }
        if (node.right == null) {
            node.right = new Node(0);
        }
        // 更新值
        if (node.add == 0) return;
        node.left.val += leftNum * node.add;
        node.right.val += rightNum * node.add;
        node.left.add += node.add;
        node.right.add += node.add;
    }

    // 定义向上更新函数
    public void pushUp(Node node) {
        node.val = node.left.val + node.right.val;
    }

    // 定义更新函数
    public void update(Node node, int start, int end, int l, int r, int val) {
        // 若node所代表区间全部在更新范围内，则直接更新返回
        if (start >= l && end <= r) {
            node.val += (end - start + 1) * val;
            node.add = val;
            return;
        }
        // 若未全部包含，则更新左右交集部分
        int mid = l + (r - l) / 2;
        // 下推(因为下面的区间需要使用，所以要更新数据)
        pushDown(node, mid - start + 1, end - mid);
        if (l <= mid) {
            update(node.left, start, mid, l, r, val);
        }
        if (r > mid) {
            update(node.right, mid + 1, end, l, r, val);
        }
        // 向上更新
        pushUp(node);
    }
    // 定义查询函数
    public int query(Node node, int start, int end, int l, int r) {
        // 区间 [l ,r] 完全包含区间 [start, end]
        // 例如：[2, 4] = [2, 2] + [3, 4]，当 [start, end] = [2, 2] 或者 [start, end] = [3, 4]，直接返回
        if (l <= start && end <= r) return node.val;
        // 把当前区间 [start, end] 均分得到左右孩子的区间范围
        // node 左孩子区间 [start, mid]
        // node 左孩子区间 [mid + 1, end]
        int mid = (start + end) >> 1, ans = 0;
        // 下推标记
        pushDown(node, mid - start + 1, end - mid);
        // [start, mid] 和 [l, r] 可能有交集，遍历左孩子区间
        if (l <= mid) ans += query(node.left, start, mid, l, r);
        // [mid + 1, end] 和 [l, r] 可能有交集，遍历右孩子区间
        if (r > mid) ans += query(node.right, mid + 1, end, l, r);
        // ans 把左右子树的结果都累加起来了，与树的后续遍历同理
        return ans;

    }

}

// 定义节点类
class Node {
    // 左右子节点
    Node left, right;
    // 当前节点值
    int val;
    // 下推标志
    int add;
    Node(int val) {
        this.val = val;
    }
}