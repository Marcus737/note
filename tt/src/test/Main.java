package test;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int t = sc.nextInt();
        while (t -- > 0){
            String s = sc.next();
            if (s.endsWith("po")) {
                System.out.println("FILIPINO");
            }else if (s.endsWith("desu") || s.endsWith("masu")) {
                System.out.println("JAPANESE");
            }else {
                System.out.println("KOREAN");
            }
        }
    }
}
