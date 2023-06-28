package com.dp;

/**
 * 青蛙跳台阶，每次可跳一阶或者两阶，跳上n阶台阶有多少种跳法
 *
 * @author huwj
 * @date 2023/6/28 9:13
 */
public class FrogJumpStairs {

    public static void main(String[] args) {
        System.out.println(frogJump(11));
    }

    private static int frogJump(int n) {
        //定义dp
        int[] dp = new int[n + 1];
        //初始关系
        dp[0] = 0;
        dp[1] = 1;
        dp[2] = 2;

        //元素之间关系 dp[i] = dp[i-1] + dp[i-2];
        for (int i = 3; i <= n; i++) {
            dp[i] = dp[i - 1] + dp[i - 2];
        }
        return dp[n];
    }
}
