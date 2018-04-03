package test.threadpool;

/**
 * Created by xiaoni on 2018/4/3.
 */
public class WorkerThread implements Runnable{
    String id = "";

    WorkerThread(String id) {
        this.id = id;
    }

    @Override
    public void run() {
        for(int i = 0;i < 1000;i++) {
            System.out.println(id + ":" + i);
        }
    }
}
