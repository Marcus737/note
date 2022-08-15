package test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class Client2 {

    private Socket socket;
    private String ip;
    private Integer port;
    private String clientName;
    PrintStream ps;
    BufferedReader br;

    public Client2(String clientName, String ip, Integer port) throws IOException {
        this.ip = ip;
        this.port = port;
        this.clientName = clientName;
        socket = new Socket(ip, port);
        ps = new PrintStream(socket.getOutputStream());
        br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    public void sendMsg(String msg) {
        ps.println(clientName + ":" + msg);
        ps.flush();
    }


    public void onReceive(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (!socket.isClosed()) {
                        String s = br.readLine();
                        System.out.println("onReceive：" + s);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void close() throws IOException {
        br.close();
        ps.close();
        socket.close();
    }

    public void run() throws IOException {
        onReceive();
        Scanner sc = new Scanner(System.in);
        while (true){
            String s = sc.nextLine();
            sendMsg(s);
            if ("quit".equalsIgnoreCase(s)){
                break;
            }
        }
        close();
    }

    public static void main(String[] args) throws IOException {
        new Client2("user2", "127.0.0.1", 8969).run();
    }
}

