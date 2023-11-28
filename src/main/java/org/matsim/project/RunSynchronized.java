package org.matsim.project;

import java.util.concurrent.*;
import java.util.stream.IntStream;

public class RunSynchronized {

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        var calc = new SquareCalculator();
        Future<Integer> calculate = calc.calculate(4);
        System.out.println(calculate.get());
        calc.executor.shutdown();
    }

    public static class SquareCalculator {

        private ExecutorService executor = Executors.newSingleThreadExecutor();

        public Future<Integer> calculate(Integer input) {
            return executor.submit(() -> {
                System.out.println("calculate");
                Thread.sleep(1000);
                System.out.println("return");
                return input * input;
            });
        }
    }
}
