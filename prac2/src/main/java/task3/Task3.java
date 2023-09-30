package src.main.java.task3;

/**
 * Реализовать функцию нахождения 16-битной контрольной суммы файла с
использованием бинарных операций и ByteBuffer. 
Программа принимает путь к файлу в переменной `filePath`. 
В функции `calculateChecksum` открывается файловый поток, и в цикле считываются каждый раз 2 байта из файла. 
Затем используется ByteBuffer для преобразования считанных байтов в короткое целое число (short), с учетом порядка Little-endian. Полученное число XOR'ится с чек-суммой. 
В конце возвращается рассчитанная контрольная сумма (checksum).
Программа выводит контрольную сумму файла в консоль.
*/
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class Task3 {

    public static void main(String[] args) {
        String filePath = "src/main/java/task3/check.txt";
        try {
            short checksum = calculateChecksum(filePath); // short занимает 2 байта
            System.out.println("Checksum: " + checksum);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static short calculateChecksum(String filePath) throws IOException {
        FileInputStream fileInputStream = new FileInputStream(filePath);
        byte[] buffer = new byte[2]; // байтовый массив на 2 числа. byte от -128 до 127 и занимает 1 байт
        short checksum = 0;

        while (fileInputStream.read(buffer) != -1) { // Пока есть данные в файле, считываем по 2 байта
            /**
             * wrap() используется для переноса массива байтов в буфер.
             * 
             * order() класса java.nio.ByteBuffer используется для извлечения порядка байтов
             * этого буфера.
             * Порядок байтов используется при чтении или записи многобайтовых значений, а
             * также при создании буферов, которые являются представлениями этого байтового
             * буфера.
             * Порядок вновь созданного байтового буфера всегда равен BIG_ENDIAN.
             * 
             * getShort() используется для считывания следующих двух байтов в текущей
             * позиции этого буфера, преобразования их в короткое значение в соответствии с
             * текущим порядком байтов, а затем увеличивает позицию на два.
             **/
            short value = ByteBuffer.wrap(buffer).order(ByteOrder.LITTLE_ENDIAN).getShort();
            checksum ^= value;
        }

        fileInputStream.close();
        return checksum;
    }
}
