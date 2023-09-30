package src.main.java.task4;

/**
 * При помощи WatchService реализовать наблюдение за каталогом:
 * 1) При создании нового файла в этом каталоге вывести его название;
 * 2) При изменении файла вывести список изменений(добавленных и
 * удаленных строк);
 * 3) При удалении файла вывести его размер и контрольную
 * сумму(использовать реализацию из задания 3).
 * Если реализовать пункт 3 не представляется возможным – докажите это.
 */

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;
import src.main.java.task3.Task3;

public class Task4 {
    // Путь к целевой директории (директория, где работает программа + относительная
    // директория)
    static String directoryPath = System.getProperty("user.dir") + "/src/main/java/task4/catalogue";
    // отображение, словарь, элемент "ключ-значение", измененные строки файла, нужна
    // для запоминания кол-ва строк в файле
    static Map<Path, Integer> fileLinesMap = new HashMap<>();
    // AbstractMap - скелет для Map-интерфейса.
    // Коллекция Map-HashMap, хранящая структуру: путь к файлу, в котором
    // зафиксированы изменения, и объект, основанное на скелетной реализации
    // Map-интерфейса, содержащий длину и контрольную сумму файла, который может
    // быть удален
    // Нужен именно Abstract.SimpleEntry, т.к нужна проходка по вложенной коллекции,
    // экземпляр которой будет значением Map.
    // Однако, из-за того, что проходку возможно сделать с помощью интерфейса
    // Map.Entry,
    static Map<Path, AbstractMap.SimpleEntry<Long, Short>> fileSizeHashSumMap = new HashMap<>();

    public static void main(String[] args) throws IOException, InterruptedException {
        // Сущность, реализующая интерфейс, для мониторинга изменений в директории
        WatchService watchService = FileSystems.getDefault().newWatchService();
        // директория папки, из строки получаем объект класса
        Path directory = Paths.get(directoryPath);
        // регистрация нужных для наблюдения событий (создание, модификация, удаление)
        directory.register(watchService,
                StandardWatchEventKinds.ENTRY_CREATE,
                StandardWatchEventKinds.ENTRY_MODIFY,
                StandardWatchEventKinds.ENTRY_DELETE);
        // класс watchkey перемещает возникшее событие в очередь наблюдателя
        WatchKey key = watchService.take();
        // директория - объект, для которого был создан ключ
        Path dir = (Path) key.watchable();

        while (true) {
            // Для всех событий объекта, зарегистрированного как WatchService, в списке
            // рассматриваемых событий
            for (WatchEvent<?> event : key.pollEvents()) {
                // Тип события
                WatchEvent.Kind<?> kind = event.kind();
                // Имя файла(относительный путь между директорией с watchservice и местом, где
                // было произведено изменение)
                Path fileName = (Path) event.context();
                // Путь к файлу - вычисляется из пути к объекту, для которого был создан ключ
                // наблюдения
                Path filePath = dir.resolve(fileName.toString());
                // Формат времени
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss");
                // Засечка текущего времени
                LocalDateTime now = LocalDateTime.now();
                System.out.print("\n" + dtf.format(now));
                // Выбираем, что делать, в зависимости от типа события
                if (kind == StandardWatchEventKinds.ENTRY_CREATE) {
                    System.out.println("\tFile created: " + fileName);
                } else if (kind == StandardWatchEventKinds.ENTRY_MODIFY) {
                    System.out.println("\tFile modified: " + fileName);
                    // Смотрим изменения файла
                    detectFileChanges(filePath);
                } else if (kind == StandardWatchEventKinds.ENTRY_DELETE) {
                    System.out.println("\tFile deleted: " + fileName);
                    // Смотрим размер и контрольную сумму
                    displayFileInformation(filePath);
                }
            }
        }
    }

    // Метод обнаружения изменений файла
    private static void detectFileChanges(Path filePath) throws IOException {
        // Строка - путь к файлу, в котором зафиксированы изменения
        File file = new File(filePath.toUri());
        // Чтение файла через поток
        try (Stream<String> fileStream = Files.lines(filePath)) {
            // Новое количество строк(старое + новое) - элементов потока
            int newLines = (int) fileStream.count();
            // количество измененных строк = всего строк в файле - сколько было
            int diffLines = newLines - fileLinesMap.getOrDefault(filePath, 0);
            // Записать новое количество строк
            fileLinesMap.put(filePath, newLines);
            System.out.println(filePath.getFileName() + " lines diff: " + diffLines);
        }
        // Помещение в коллекцию объекта с данными о размере и контрольной сумы файла,
        // если он будет удален
        fileSizeHashSumMap.put(filePath,
                new AbstractMap.SimpleEntry<>(file.length(), Task3.calculateChecksum(file.getPath())));
    }
    // Метод отображение размера файла и контрольной суммы
    private static void displayFileInformation(Path filePath) {
        System.out.println("Deleted file's data (bytes size, CRC-16 checksum):");
        // Вытаскиваем по заданному пути зафиксированный ранее размер файла и его контрольную сумму
        System.out.println(fileSizeHashSumMap.remove(filePath));
    }
}