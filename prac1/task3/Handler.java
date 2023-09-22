package task3;

import java.io.File;
import java.util.concurrent.BlockingQueue;

/**
 * Класс обработки файла
 */
public class Handler implements Runnable {
    private BlockingQueue<File> fileBlockingQueue;

    public Handler(BlockingQueue<File> fileBlockingQueue) {
        this.fileBlockingQueue = fileBlockingQueue;
    }

    @Override
    public void run() {
        try {
            File file;
            /**
             * Блокирующая операция take() - берет следующий элемент, если же очередь пустая
             * - блокирует выполнение до появления элемента (до момента, когда перестанет
             * быть пустой)
             */
            file = fileBlockingQueue.take();
            /**
             * Время обработки файла = Длина * 7 мс
             */
            Thread.sleep(file.length() * 7);
            System.out.println("\tОбработан " + file.getName());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
