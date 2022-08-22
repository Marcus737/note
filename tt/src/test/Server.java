package test;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;

public class Server {
    private ServerSocket socket;
    private Integer port;
    private List<Socket> socketList;
    private ArrayBlockingQueue<String> queue;

    public Server(Integer port) throws IOException {
        this.port = port;
        socket = new ServerSocket(port);
        /**
         * 这里要用线程安全的类，因为可能有2个线程在操作该数据结构
         */
        socketList = Collections.synchronizedList(new ArrayList<>());
        /**
         * 阻塞队列，队列为空就阻塞当前线程
         */
        queue = new ArrayBlockingQueue<>(100);
    }

    private void openNewServerThread(Socket st){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    BufferedReader br = new BufferedReader(new InputStreamReader(st.getInputStream()));
                    while (!socket.isClosed()){
                        String s = br.readLine();
                        if (s.equalsIgnoreCase("quit")){
                            socket.close();
                            break;
                        }
                        queue.add(s);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void writeToFile(String filename, String s) throws IOException {
        filename = filename + ".txt";
        File file = new File(filename);
        if (!file.exists()) file.createNewFile();
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)));
        bw.write(s);
        bw.flush();
        bw.close();
    }

    public void run() throws InterruptedException, IOException {
        //接收新客户端
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true){
                    try {
                        Socket accept = socket.accept();
                        System.out.println("新连接");
                        socketList.add(accept);
                        openNewServerThread(accept);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();

        while (true){
            String s = queue.take();
            System.out.println("服务器接受到信息：" + s);
            //通知所有客户端，有消息更新
            for (Socket ts : socketList) {
                if (ts.isClosed()){
                    socketList.remove(ts);
                    continue;
                }
                try {
                    writeToFile("message", s);
                    PrintStream ps = new PrintStream(ts.getOutputStream());
                    ps.println(s);
                    ps.flush();

                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        new Server(8969).run();
    }
}

