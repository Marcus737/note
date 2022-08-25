package test;

import java.util.Arrays;

public class Solution {
    int N = (int) (1e6 + 10);
    int[] a = new int[N];

    {
        Arrays.fill(a, Integer.MAX_VALUE);
        a[0] = 0;
        for (int i = 0; i < N - 1; i++) {
            a[i + 1] = Math.min(a[i + 1], a[i] + 1);
            if (i * 2 < N) a[i * 2] = Math.min(a[i * 2], a[i] + 1);
        }
    }

    public int numberOfSteps(int num) {
        return a[num];
    }

    public static void main(String[] args) {
        new Solution().numberOfSteps(10);
    }
}