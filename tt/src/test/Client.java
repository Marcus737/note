package test;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {

    private Socket socket;
    private String ip;
    private Integer port;
    private String clientName;
    PrintStream ps;
    BufferedReader br;

    public Client(String clientName, String ip, Integer port) throws IOException {
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

    private String readFromFile(String filename) throws IOException {
        filename = filename + ".txt";
        File file = new File(filename);
        if (!file.exists()) file.createNewFile();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
        String s = bufferedReader.readLine();
        bufferedReader.close();
        return s;
    }

    /**
     * 收到服务端消息
     */
    public void onReceive(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (!socket.isClosed()) {
                        String s = br.readLine();
//                        System.out.println("onReceive：" + s);
                        String s1 = readFromFile("message");
                        System.out.println(s1);
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
        new Client("user1", "127.0.0.1", 8969).run();
    }
}

