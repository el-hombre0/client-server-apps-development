package task4;
/**
 * Реализовать следующую систему.
 * Файл. Имеет следующие характеристики:
 *     0. Тип файла (например XML, JSON, XLS)
 *     1. Размер файла — целочисленное значение от 10 до 100.
 * Генератор файлов — генерирует файлы с задержкой от 100 до 1000 мс.
 * Очередь — получает файлы из генератора. Вместимость очереди — 5 файлов.
 * Обработчик файлов — получает файл из очереди.
 * Каждый обработчик имеет параметр — тип файла, который он может обработать.
 * Время обработки файла: «Размер файла*7мс». Система должна быть реализована при помощи инструментов RxJava.
 */

import io.reactivex.rxjava3.core.Observable;

import java.io.File;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class Task4 {
    /**
     * Директория хранения файлов
     */
    private final String dir = "src/main/java/task4/files/";

    public Task4() {
        // Очередь для хранения файлов вместимостью 5
        BlockingQueue<File> fileBlockingQueue = new ArrayBlockingQueue<>(5);

        // Количество созданных файлов
        int fileCount = 0;

        // Название файла
        String fileName = "";

        // Даём обработчику файлов доступ к очереди, в которой эти файлы находятся
        FileExecutorTask fileExecutorTask = new FileExecutorTask(fileBlockingQueue);

        // Создаём 10 файлов
        while(fileCount < 10){
            // Название файла - номер по счёту
            fileName = Integer.toString(fileCount);
            // Перевод в строку
            String finalFileName = fileName;

            // Наблюдаемый объект - класс создания новых файлов. В передатчик для подписчика кладётся файл
            Observable.create(
                    tempEmitter -> new FileGeneratorTask(finalFileName, dir, tempEmitter).run())
                    // Observer - блокирующая очередь и обработчик файлов
                    .subscribe(e -> {
                        fileBlockingQueue.put((File) e);
                        fileExecutorTask.run();
                    });

            fileCount++;
        }
    }

    public static void main(String[] args) {
        new Task4();
    }
}
