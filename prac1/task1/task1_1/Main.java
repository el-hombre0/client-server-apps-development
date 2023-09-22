package task1.task1_1;

public class Main {
    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();
        ArrayStuff obj = new ArrayStuff();
        obj.printArray();
        System.out.println("\nMax element is " + obj.maxElemSearch());
        long endTime = System.currentTimeMillis();
        System.out.println("Work time is " + (endTime - startTime) + " ms.");
    }
}
