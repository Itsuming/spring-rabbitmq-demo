package com.franksu.springbootrabbitmq.juc;

/**
 * @BelongsProject: spring-rabbitmq
 * @BelongsPackage: com.franksu.springbootrabbitmq.juc
 * @Author: suming9
 * @CreateTime: 2024-09-10  09:10
 * @Description: TODO
 * @Version: 1.0
 */
public class HeapSort2 {

    public int[] heapSort(int[] nums) {
        int n = nums.length;
        // 初始化
        for (int i = (n >> 1) - 1; i >= 0; --i) {
            heapify(nums, i, n);
        }
        // 递归堆化
        for (int i = 0; i < n - 1; ++i) {
            swap(nums, i, n - 1 - i);
            heapify(nums, i, n - 1 - i);
        }
        return nums;
    }
    // 定义堆化函数
    public void heapify(int[] nums, int i, int length) {
        int left = 2 * i + 1;
        int right = 2 * i + 2;
        int largest = i;

        if (left < length && nums[left] > nums[largest]) {
            largest = left;
        }
        if (right < length && nums[right] > nums[largest]) {
            largest = right;
        }
        // 进行递归
        if (largest != i) {
            swap(nums, i, largest);
            heapify(nums, largest, length);
        }
    }

    public void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }

    public static void main(String[] args) {
        int[] array = new int[]{2,4,7,1,3};
        HeapSort heapSort = new HeapSort();
        heapSort.heapSort(array);
        for (int num : array) {
            System.out.println(num);
        }

    }
}