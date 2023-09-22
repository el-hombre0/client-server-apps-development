package task3;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * Класс создания файла
 */
public class Generator implements Runnable {
    private String fileName;
    private String dir;
    private BlockingQueue<File> blockQueue;

    public Generator(String fileName, String dir, BlockingQueue<File> blockQueue) {
        this.fileName = fileName;
        this.dir = dir;
        this.blockQueue = blockQueue;
    }

    @Override
    public void run() {
        try {
            int delay = (int) (Math.random() * 900 + 100); // Генерация задержки от 100 доя 1000 мс
            TimeUnit.MILLISECONDS.sleep(delay);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        File file;
        /**
         * Тип файла выбирается случайно исходя из кол-ва доступных типов
         */
        int fileType = (int) (Math.random() * FileTypeEnum.values().length);
        /**
         * Задание имени файла
         */
        synchronized (Thread.currentThread()) {
            file = new File(dir + fileName + "." + FileTypeEnum.values()[fileType]);
        }

        try {
            file.createNewFile();
            System.out.println("Создан " + file.getName());
            /**
             * Доступ к изменению файла, открыт на чтение и запись
             */
            RandomAccessFile raFile = new RandomAccessFile(file, "rw");
            /**
             * Установка случайного размера файла от 10 до 100 байт
             */
            raFile.setLength((int) (Math.random() * 90 + 10));
            raFile.close();
            /**
             * Блокирующая операция put(E e) - помещает элемент в очередь, если очередь
             * заполнена - блокирует выполнение до освобождения места в очереди и успешного
             * помещения в нее нового элемента.
             */
            blockQueue.put(file);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
