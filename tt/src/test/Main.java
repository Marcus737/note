package test;

import java.io.*;
import java.util.LinkedList;
import java.util.Queue;

class MC {
    class P {
        int x, y, cnt, d;
        P parent;
        public P() {
        }

        private P(P p) {
            this.x = p.x;
            this.y = p.y;
            this.cnt = p.cnt;
            this.d = p.d;
        }
    }

    int N = 110;
    int[][] map = new int[N][N];
    int n, m, k, T;
    int[] dx = new int[]{-1, 0, 1, 0}, dy = new int[]{0, 1, 0, -1};
    P s = new P(), e = new P();

    public void run() throws IOException {
        T = toInt(br.readLine());
        while (T-- > 0) {
            String[] ss = br.readLine().split(" ");
            n = toInt(ss[1]);
            m = toInt(ss[0]);
            for (int i = 0; i < n; i++) {
                String s = br.readLine();
                for (int j = 0; j < m; j++) {
                    if (s.charAt(j) == '*') map[i][j] = 1;
                }
            }
            ss = br.readLine().split(" ");
            k = toInt(ss[0]);
            s.x = toInt(ss[2]) - 1;
            s.y = toInt(ss[1]) - 1;
            e.x = toInt(ss[4]) - 1;
            e.y = toInt(ss[3]) - 1;
            if (bfs()) bw.write("yes");
            else bw.write("no");
            bw.write("\n");
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < m; j++) {
                    map[i][j] = 0;
                }
            }
        }
        bw.flush();
    }

    private boolean bfs() {
        Queue<P> q = new LinkedList<>();
        q.add(s);
        s.d = -1;
        s.cnt = -1;
        boolean res = false;
        while (q.size() > 0) {
            P p = q.poll();
            P parent = p.parent;
            if (p.x == e.x && p.y == e.y) {
                if (p.cnt <= k) {
                    res = true;
                }
            }
            for (int i = 0; i < 4; i++) {
                int tx = p.x + dx[i], ty = p.y + dy[i];
                if (tx < 0 || tx >= n || ty < 0 || ty >= m) continue;
                if (map[tx][ty] == 1) continue;
                if (parent != null){
                    if (tx == parent.x && ty == parent.y) continue;
                }
                P tp = new P();
                tp.parent = p;
                tp.x = tx;
                tp.y = ty;
                tp.cnt = p.cnt;
                tp.d = i;
                if (p.d != i) {
                    tp.cnt += 1;
                }
                q.add(tp);
            }
        }
        return res;
    }

    int toInt(String s) {
        return Integer.parseInt(s);
    }

    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
}

public class Main {
    public static void main(String[] args) throws IOException {
        new MC().run();
    }
}
