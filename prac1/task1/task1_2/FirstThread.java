package task1.task1_2;

// Multithreading function implementation

public class FirstThread extends Thread {
    public long workTime;
    final int SIZE;
    int[] randomIntArray;
    int upperBound;
    int biggest;
    public boolean doStop = false;

    public synchronized void doStop() {
        this.doStop = true;
    }

    FirstThread(int[] createdArray, int SIZE, int upperBound) {
        this.randomIntArray = createdArray;
        this.SIZE = SIZE;
        this.upperBound = upperBound;
    }

    @Override
    public void run() {
        try {
            long startTime = System.currentTimeMillis();

            int biggest = 0;
            for (int i = 0; i < this.SIZE; i++) {
                Thread.sleep(1);
                if (this.randomIntArray[i] > biggest)
                    biggest = randomIntArray[i];
            }
            System.out.println("The biggest number is " + biggest);
            long endTime = System.currentTimeMillis();
            this.workTime = endTime - startTime;
            this.biggest = biggest;

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void printArray() {
        for (int i = 0; i < SIZE; i++) {
            System.out.print(this.randomIntArray[i] + " ");
        }
    }
}
