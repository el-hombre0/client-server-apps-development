package task1.task1_1;

// Consistent function implementation
import java.util.Random;

public class ArrayStuff extends Thread {
    final int SIZE = 10000;
    int[] randomIntArray = new int[SIZE];
    int upperBound = 100;
    long workTime;

    ArrayStuff() {
        Random random = new Random();
        for (int i = 0; i < SIZE; i++) {
            this.randomIntArray[i] = random.nextInt(this.upperBound);
        }
    }

    // @Override
    // public void run() {
    //     long startTime = System.currentTimeMillis();
    //     try {
    //         Random random = new Random();
    //         for (int i = 0; i < SIZE; i++) {
    //             this.randomIntArray[i] = random.nextInt(this.upperBound);
    //         }
    //         int biggest = 0;
    //         for (int i = 0; i < SIZE; i++) {
    //             Thread.sleep(1);
    //             if (this.randomIntArray[i] > biggest)
    //                 biggest = randomIntArray[i];
    //         }
    //         System.out.println("The biggest number is " + biggest);
    //     } catch (InterruptedException e) {
    //         e.printStackTrace();
    //     }
    //     long endTime = System.currentTimeMillis();
    //     this.workTime = endTime - startTime;
    // }

    // public void fillingInArray() {
    //     Random random = new Random();
    //     for (int i = 0; i < SIZE; i++) {
    //         randomIntArray[i] = random.nextInt(upperBound);
    //     }
    // }

    public void printArray() {
        for (int i = 0; i < SIZE; i++) {
            System.out.print(this.randomIntArray[i] + " ");
        }
    }

    public int maxElemSearch() {
        int biggest = 0;
        for (int i = 0; i < SIZE; i++) {
            if (this.randomIntArray[i] > biggest)
                biggest = this.randomIntArray[i];
        }
        return biggest;
    }
}
