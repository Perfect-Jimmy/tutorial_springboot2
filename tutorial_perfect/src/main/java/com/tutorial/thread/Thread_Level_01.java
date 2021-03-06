package com.tutorial.thread;

import java.util.concurrent.*;

public class Thread_Level_01 {
   // public static ExecutorService fixedExecutorService = new ThreadPoolExecutor(3, 5, 0, TimeUnit.MILLISECONDS, new SynchronousQueue<Runnable>());  //Executors.newFixedThreadPool(5);
   public static ExecutorService fixedExecutorService = new ThreadPoolExecutor(3, 5, 0, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>());
    public static void main(String[] args) {
       for(int i=0;i<1000;i++){
           fixedExecutorService.execute(new Runnable() {
               @Override
               public void run() {
                   System.out.println("线程池:"+Thread.currentThread().getName()+"---执行");
                   try {
                       Thread.sleep(1000);
                   } catch (InterruptedException e) {
                       e.printStackTrace();
                   }
               }
           });
       }

    }
}
