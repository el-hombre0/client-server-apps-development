package task4;

import java.io.File;
import java.util.concurrent.BlockingQueue;

/**
 * Класс обработки файла
 */
public class FileExecutorTask implements  Runnable{
    /**
     * Очередь, в которой лежат файлы
     */
    private BlockingQueue<File> fileBlockingQueue;

    public FileExecutorTask(BlockingQueue<File> fileBlockingQueue) {
        this.fileBlockingQueue = fileBlockingQueue;
    }

    /**
     * Поток берёт файл из очереди и ждет 7 мс
     */
    @Override
    public void run() {
        try {
            File file;
            file = fileBlockingQueue.take();
            Thread.sleep(file.length() * 7);
            System.out.println("\tОбработан " + file.getName());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
