package src.main.java.task2;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.channels.FileChannel;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.apache.commons.io.*;

/**
 * Реализовать копирование файла размером 100 Мб 4 методами:
 * 1) FileInputStream/FileOutputStream
 * 2) FileChannel
 * 3) Apache Commons IO
 * 4) Files class
 * Замерить затраты по времени и памяти и провести сравнительный анализ.
 */
public class Task2 {
    public static void inputStreamCopy(File src) {
        File dst = new File(
                "/home/stas/University/4_kurs/7_semestr/Client-Server_Apps_Development/prac2/src/main/java/task2/Task2Copies/task2_1copy.txt");
        try (InputStream inputStream = new FileInputStream(src);
                OutputStream outputStream = new FileOutputStream(dst)) {
            byte[] buffer = new byte[1024];
            int length;
            // Читаем данные в байтовый массив, а затем выводим в OutStream
            while ((length = inputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, length);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void channelCopy(File src) {
        File dst = new File(
                "/home/stas/University/4_kurs/7_semestr/Client-Server_Apps_Development/prac2/src/main/java/task2/Task2Copies/task2_2copy.txt");

        try (FileChannel srcFileChannel = new FileInputStream(src).getChannel();
                FileChannel dstFileChannel = new FileOutputStream(dst).getChannel()) {
            long count = srcFileChannel.size();
            while (count > 0) {
                long transferred = srcFileChannel.transferTo(srcFileChannel.position(), count, dstFileChannel);
                srcFileChannel.position(srcFileChannel.position() + transferred);
                count -= transferred;
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void apacheCopy(File src) {
        File dst = new File(
                "/home/stas/University/4_kurs/7_semestr/Client-Server_Apps_Development/prac2/src/main/java/task2/Task2Copies/task2_3copy.txt");
        try {
            FileUtils.copyFile(src, dst);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void filesClassCopy() {
        Path src = Paths.get(
                "/home/stas/University/4_kurs/7_semestr/Client-Server_Apps_Development/prac2/src/main/java/task2/task2.txt");
        Path dst = Paths.get(
                "/home/stas/University/4_kurs/7_semestr/Client-Server_Apps_Development/prac2/src/main/java/task2/Task2Copies/task2_4copy.txt");
        try {
            Files.copy(src, dst);

        } catch (FileAlreadyExistsException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        File src = new File(
                "/home/stas/University/4_kurs/7_semestr/Client-Server_Apps_Development/prac2/src/main/java/task2/task2.txt");
        long s1 = System.currentTimeMillis();
        inputStreamCopy(src);
        long e1 = System.currentTimeMillis();

        long s2 = System.currentTimeMillis();
        channelCopy(src);
        long e2 = System.currentTimeMillis();

        long s3 = System.currentTimeMillis();
        filesClassCopy();
        long e3 = System.currentTimeMillis();

        long s4 = System.currentTimeMillis();
        apacheCopy(src);
        long e4 = System.currentTimeMillis();
        System.out.println("FileInputStream/FileOutputStream: " + (e1 - s1) + " ms.");
        System.out.println("FileChannel: " + (e2 - s2) + " ms.");
        System.out.println("Apache Commons IO: " + (e3 - s3) + " ms.");
        System.out.println("Files Class: " + (e4 - s4) + " ms.");
    }
}
