package com.franksu.springbootrabbitmq.juc;

import io.swagger.models.auth.In;

import java.util.Arrays;

/**
 * @BelongsProject: spring-rabbitmq
 * @BelongsPackage: com.franksu.springbootrabbitmq.juc
 * @Author: suming9
 * @CreateTime: 2024-08-30  09:13
 * @Description: TODO
 * @Version: 1.0
 */
public class MinBalanceSubStr2 {

    public int getMinBalance(String s) {
        int n = s.length();
        int[] dp = new int[n + 1];
        return 0;
    }

    public static void main(String[] args) {
        String s = "fabccddg";
        MinBalanceSubStr2 minBalanceSubStr2 = new MinBalanceSubStr2();
        System.out.println(minBalanceSubStr2.getMinBalance(s));
    }
}