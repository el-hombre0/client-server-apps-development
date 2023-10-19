package task4;

import io.reactivex.rxjava3.core.Emitter;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * Класс создания файлов
 */
public class FileGeneratorTask implements Runnable{
    private String fileName;
    /**
     * Директория, где файл создаётся
     */
    private String dir;
    /**
     * Передатчик файла
     */
    private Emitter emitter;

    public FileGeneratorTask(String fileName, String dir, Emitter emitter) {
        this.fileName = fileName;
        this.dir = dir;
        this.emitter = emitter;
    }

    /**
     * Поток генерирует случайную задержку
     */
    @Override
    public void run() {
        try {
            Thread.sleep((int)(Math.random() * 900 + 100));
        } catch (InterruptedException e){
            e.printStackTrace();
        }

        // Случайно выбираем тип файла
        File file;

        int fileType = (int)(Math.random() * FileTypeEnum.values().length);
        // Для потокобезопасности
        synchronized (Thread.currentThread()) {
            // Параметры нового файла
            file = new File(dir + fileName + "." + FileTypeEnum.values()[fileType]);
        }

        try{
            // Создаём новый файл
            file.createNewFile();
            System.out.println("Создан " + file.getName());
            RandomAccessFile randomAccessFile = new RandomAccessFile(file, "rw");
            // Указание размера файла
            randomAccessFile.setLength((int)(Math.random() * 90 + 10));
            // Передача следующего файла для Observable
            emitter.onNext(file);
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
