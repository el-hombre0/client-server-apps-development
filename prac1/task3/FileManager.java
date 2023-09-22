package task3;

import java.io.File;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FileManager {
    /**
     * Директория для генерации файлов
     */
    private final String dir = "task3/files/";

    FileManager() {
        /**
         * Блокирующая очередь на 5 файлов
         * Блокирующая операция take() - берет следующий элемент, если же очередь пустая
         * - блокирует выполнение до появления элемента (до момента, когда перестанет
         * быть пустой)
         * Блокирующая операция put(E e) - помещает элемент в очередь, если очередь
         * заполнена - блокирует выполнение до освобождения места в очереди и успешного
         * помещения в нее нового элемента.
         */
        BlockingQueue<File> blockQueue = new ArrayBlockingQueue<>(5);
        /**
         * Фиксированный пул на 8 потоков для создания файлов
         */
        ExecutorService generatorPool = Executors.newFixedThreadPool(8);
        /**
         * Фиксированный пул на 8 потоков для обработки файлов
         */
        ExecutorService handlerPool = Executors.newFixedThreadPool(8);

        int fileCount = 0;

        String fileName = "";
        /**
         * Пока создано меньше 10 файлов
         */
        while (fileCount < 10) {
            /**
             * Имя файла - его номер по счёту
             */
            fileName = Integer.toString(fileCount);
            /**
             * Создание файла
             */
            Generator fileGenerator = new Generator(fileName, dir, blockQueue);
            /**
             * submit возвращает объект интерфейса Future
             */
            generatorPool.submit(fileGenerator);

            /**
             * Обработка файла
             */
            Handler fileHandler = new Handler(blockQueue);
            handlerPool.submit(fileHandler);

            fileCount++;
        }
        generatorPool.shutdown();
        handlerPool.shutdown();
    }
}
