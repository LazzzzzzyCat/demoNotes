package com.slidewindow;

/**
 * 滑动窗口
 *
 * @author huwj
 * @date 2023/6/29 9:09
 */
public class FixWindowMaxSum {

    public static void main(String[] args) {
        int[] nums = new int[]{2, 3, 5, 22, 53, 66, 100, 2, 1, 13, 10};
        System.out.println(getMaxSum(nums, 3));
    }


    /**
     * 示例1
     * 给定一个整数数组，计算长度为 'k' 的连续子数组的最大总和。
     * <p>
     * 输入：arr [] = {100,200,300,400}
     * k = 2
     * <p>
     * 输出：700
     * <p>
     * 解释：300 + 400 = 700
     *
     * @param nums
     * @param k
     * @return
     */
    private static int getMaxSum(int[] nums, int k) {
        if (nums.length <= k) {
            int sum = 0;
            for (int num : nums) {
                sum += num;
            }
            return sum;
        }
        //第一个窗口
        int maxSum = 0;
        for (int i = 0; i < k; i++) {
            maxSum += nums[i];
        }

        //下一个窗口 = 本次窗口 + 进窗口的值 - 出窗口的值
        int sum = maxSum;
        for (int i = k; i < nums.length; i++) {
            sum = sum + nums[i] - nums[i - k];
            maxSum = Math.max(maxSum, sum);
        }
        return maxSum;
    }
}
