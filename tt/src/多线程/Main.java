package 多线程;

import java.util.concurrent.ExecutorService;

class Worker implements Runnable{
    @Override
    public void run() {
        synchronized (Worker.class){
            for (int i = 1; i < 10; i++) {
                System.out.println(Thread.currentThread().getName() + ":" + i);
            }
        }
    }
}

public class Main {


    public static void main(String[] args) {

        Worker worker1 = new Worker(), worker2 = new Worker();
        new Thread(worker1, "线程1").start();
        new Thread(worker2, "线程2").start();
    }
}
