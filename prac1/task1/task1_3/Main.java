package task1.task1_3;

import java.util.Random;
import java.util.concurrent.ForkJoinPool;

public class Main {
    final int SIZE = 10000;
    int[] randomIntArray = new int[SIZE];
    int upperBound = 100;

    public void fillingInArray() {
        Random random = new Random();
        for (int i = 0; i < SIZE; i++) {
            randomIntArray[i] = random.nextInt(upperBound);
        }
    }

    public void printArray() {
        for (int i = 0; i < SIZE; i++) {
            System.out.print(this.randomIntArray[i] + " ");
        }
    }

    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();
        Main obj = new Main();
        obj.fillingInArray();
        obj.printArray();
        ForkJoinPool pool = new ForkJoinPool();
        Action action = new Action(obj.randomIntArray, obj.SIZE);
        pool.invoke(action);
        long endTime = System.currentTimeMillis();
        System.out.println("Total time is " + (endTime - startTime) + " ms.");

    }

}
