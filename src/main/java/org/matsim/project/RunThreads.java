package org.matsim.project;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RunThreads {
    public static void main(String[] args) {
        Shared shared = new Shared();
//        Thread one = new Thread(new RunnableDemo("one", shared));
//        Thread two = new Thread(new RunnableDemo("two", shared));
        ExecutorService executorService = Executors.newFixedThreadPool(2);

        executorService.submit(new Thread(new RunnableDemo("one", shared)));
        executorService.submit(new Thread(new RunnableDemo("two", shared)));

        executorService.shutdown();
    }

    private static class RunnableDemo implements Runnable{
        private Shared counter;
        private String name;

        public RunnableDemo(String name, Shared counter) {
            this.counter = counter;
            this.name=name;
        }

        @Override
        public void run() {
            System.out.println(name + " " + counter.getCount());
            counter.inc();
            System.out.println(name + " " + counter.getCount());
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println(name + " " + counter.getCount());
            counter.inc();
            System.out.println(name + " " + counter.getCount());
        }
    }

    private static class Shared{
        private int count = 0;

        public void inc() {
            this.count+=1;
        }

        public int getCount() {
            return count;
        }
    }
}
