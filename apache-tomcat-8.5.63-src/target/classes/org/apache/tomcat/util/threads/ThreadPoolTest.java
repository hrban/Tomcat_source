package org.apache.tomcat.util.threads;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

public class ThreadPoolTest {

    public static void main(String[] args) {

        //定制版的任务队列
        TaskQueue taskqueue = new TaskQueue();

        //定制版的线程工厂
        TaskThreadFactory tf = new TaskThreadFactory("tomcat-",false,10);

        //定制版的线程池
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(5, 10, 5000,
                TimeUnit.MILLISECONDS,taskqueue, tf);

        taskqueue.setParent(threadPoolExecutor);

        for (int i = 0; i < 10; i++) {
            CompletableFuture.runAsync(() -> {
                try {
                    System.out.println("poolSize:" + threadPoolExecutor.getPoolSize() +
                            ",queueSize:" + threadPoolExecutor.getQueue().size() + "," + Thread.currentThread().getName());
                    Thread.sleep(1000);
                } catch (Exception e) {
                }

            }, threadPoolExecutor);
        }

    }
}

