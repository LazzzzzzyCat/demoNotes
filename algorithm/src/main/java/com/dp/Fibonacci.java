package com.dp;

/**
 * 斐波那契数列
 *
 * @author huwj
 * @date 2023/6/28 8:53
 */
public class Fibonacci {

    public static void main(String[] args) {
        int n = 11;
        System.out.println(fib(n));
        System.out.println(dpFib(n));
    }

    private static int fib(int n) {
        //终止条件
        if (n <= 2) {
            return n;
        }
        //递推关系
        return fib(n - 1) + fib(n - 2);
    }

    private static int dpFib(int n) {
        //定义dp数组以及含义
        int[] dp = new int[n + 1];
        //初始值
        dp[0] = 1;
        dp[1] = 1;
        //dp元素之间的关系
        for (int i = 2; i <= n; i++) {
            dp[i] = dp[i - 1] + dp[i - 2];
        }
        return dp[n];
    }
}
