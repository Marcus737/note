package test;

class Solution {
    int N = 1010;
    int[] a = new int[N];
    public int busyStudent(int[] startTime, int[] endTime, int queryTime) {
        int n = startTime.length;
        for (int i = 0; i < n; i++) {
            for (int j = startTime[i]; j <= endTime[j]; j++) {
                a[j]++;
            }
        }
        return a[queryTime];
    }

    public static void main(String[] args) {
        Thread.yield();
    }
}