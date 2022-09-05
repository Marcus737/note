package test;

import java.io.*;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;



public class Main {

    static int N = 30010;
    static int n, m, k;
    static int[] a = new int[N], b = new int[N], c = new int[N];
    //当前在第i个城市，第j天的最大收益
    static long[] f = new long[N];

    public static void main(String[] args) throws IOException {
        String[] ss = br.readLine().split(" ");
        n = toInt(ss[0]); m = toInt(ss[1]); k = toInt(ss[2]);
        ss = br.readLine().split(" ");
        for (int i = 0; i < m; i++) {
            c[i + 1] = toInt(ss[i]);
        }
        ss = br.readLine().split(" ");
        for (int i = 0; i < m; i++) {
            a[i + 1] = toInt(ss[i]);
        }
        ss = br.readLine().split(" ");
        for (int i = 0; i < m; i++) {
            b[i + 1] = toInt(ss[i]);
        }


    }

    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
    static int toInt(String s) {
        return Integer.parseInt(s);
    }
}
