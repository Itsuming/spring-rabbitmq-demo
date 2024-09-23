package com.franksu.springbootrabbitmq.juc;

/**
 * @BelongsProject: spring-rabbitmq
 * @BelongsPackage: com.franksu.springbootrabbitmq.juc
 * @Author: suming9
 * @CreateTime: 2024-09-09  08:48
 * @Description: TODO
 * @Version: 1.0
 */
public class HeapSort {

    public int[] heapSort(int[] array) {
        if (array == null || array.length == 0) {
            return array;
        }

        int n = array.length;
        // 建一个大顶堆
        for (int i = n / 2 - 1; i >= 0; i--) {
            heapify(array, i, n);
        }

        // 将未排序部分的最大数交换至已排序部分，再进行一次堆化以保持堆的结构
        for (int i = 0; i < n - 1; i++) {
            swap(array, 0, n - 1 - i);
            heapify(array, 0, n - 1 - i);
        }

        return array;
    }

    private void heapify(int[] array, int i, int length) {
        int left = 2 * i + 1;
        int right = 2 * i + 2;
        int largest = i;

        if (left < length && array[left] > array[largest]) {
            largest = left;
        }

        if (right < length && array[right] > array[largest]) {
            largest = right;
        }

        if (largest != i) {
            swap(array, i, largest);
            heapify(array, largest, length);
        }
    }

    private void swap(int[] nums, int i, int j) {
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