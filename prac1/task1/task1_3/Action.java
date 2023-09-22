package task1.task1_3;

import java.util.Arrays;
import java.util.concurrent.RecursiveAction;

public class Action extends RecursiveAction {

    private int SIZE;
    int[] randomIntArray = new int[SIZE];

    Action(int[] createdArray, int SIZE) {
        this.randomIntArray = createdArray;
        this.SIZE = SIZE;
    }

    @Override
    protected void compute() {
        if (SIZE <= 5000) {
            try {
                int biggest = 0;
                for (int i = 0; i < this.SIZE; i++) {
                    Thread.sleep(1);
                    if (this.randomIntArray[i] > biggest)
                        biggest = randomIntArray[i];
                }
                System.out.println("\nThe biggest number is " + biggest);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } else {
            int[] firstCrop = Arrays.copyOfRange(randomIntArray, 0, SIZE / 2);
            int[] secondCrop = Arrays.copyOfRange(randomIntArray, SIZE / 2, SIZE);
            Action a1 = new Action(firstCrop, SIZE / 2);
            Action a2 = new Action(secondCrop, SIZE / 2);
            invokeAll(a1, a2);
        }
    }

}
