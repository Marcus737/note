package test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Solution {
    int N = 510;
    List<Integer>[] t = new List[N];
    public List<List<Integer>> groupThePeople(int[] groupSizes) {
        List<List<Integer>> res = new ArrayList<>();
        for (int i = 0; i < groupSizes.length; i++) {
            int v = groupSizes[i];
            if (t[v] == null) {
                t[v] = new ArrayList<>();
                res.add(t[v]);
            }
            t[v].add(i);
        }
        for (List<Integer> list : res) {
            while (list.size() > list.get(0)){
                List<Integer> tl = new ArrayList<>();
                for (int i = 0; i < list.size(); i++) {
                    tl.add(list.remove(0));
                }
                res.add(tl);
            }
        }
        return res;
    }

    public static void main(String[] args) {
        int[] a = new int[]{3,3,3,3,3,1,3};
        new Solution().groupThePeople(a);
    }
}