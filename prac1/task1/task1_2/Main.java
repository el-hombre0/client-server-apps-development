package task1.task1_2;

import java.util.Arrays;
import java.util.Random;

public class Main {
    public static void main(String[] args) {
        final int SIZE = 10000;
        int[] randomIntArray = new int[SIZE];
        int upperBound = 100;
        final int threadsNumber = 2;

        Random random = new Random();
        for (int i = 0; i < SIZE; i++) {
            randomIntArray[i] = random.nextInt(upperBound);
        }

        int[] firstCrop = Arrays.copyOfRange(randomIntArray, 0, SIZE / threadsNumber);
        int[] secondCrop = Arrays.copyOfRange(randomIntArray, SIZE / threadsNumber, SIZE);

        // int[] firstCrop = Arrays.copyOfRange(randomIntArray, 0, 5);
        // int[] secondCrop = Arrays.copyOfRange(randomIntArray, 5, 9);

        FirstThread t1 = new FirstThread(firstCrop, SIZE / threadsNumber, upperBound);
        SecondThread t2 = new SecondThread(secondCrop, SIZE / threadsNumber, upperBound);

        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        t1.printArray();
        t2.printArray();
        
        System.out.println("\nThe biggest element is " + ((t1.biggest > t2.biggest) ? t1.biggest : t2.biggest));
        long firstTime = t1.workTime;
        System.out.println("\nTime of the first is " + firstTime + " ms.");

        long secondTime = t2.workTime;
        System.out.println("\nTime of the second is " + secondTime + " ms.");

        System.out.println("\nTotal time s is " + (firstTime + secondTime) + " ms.");

    }
}
