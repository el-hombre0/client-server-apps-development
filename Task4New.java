// package src.main.java.task4;

// import java.io.*;
// import java.nio.file.*;
// import java.nio.file.attribute.BasicFileAttributes;
// import java.util.*;

// public class Task4New {
//     private static final String DIRECTORY_PATH = "src/main/java/task4/catalogue";
//     // путь к наблюдаемому каталогу
//     private static final String FILE_NAME = "file.txt"; // имя наблюдаемого файла

//     public static void main(String[] args) throws IOException,
//             InterruptedException {
//         // Создаем экземпляр WatchService
//         WatchService watchService = FileSystems.getDefault().newWatchService();

//         // Регистрируем каталог для наблюдения за событиями MODIFY и DELETE
//         Path directory = Paths.get(DIRECTORY_PATH);
//         directory.register(watchService, StandardWatchEventKinds.ENTRY_MODIFY,
//                 StandardWatchEventKinds.ENTRY_DELETE);

//         // Создаем мапы для хранения добавленных и удаленных строк
//         Map addedLines = new HashMap<>();
//         Map removedLines = new HashMap<>();

//         // Бесконечный цикл для обработки событий
//         while (true) {
//             // Ждем следующего события
//             WatchKey key = watchService.take();

//             // Перебираем все события внутри ключа
//             for (WatchEvent event : key.pollEvents()) {
//                 WatchEvent.Kind kind = event.kind();

//                 // Если событие - MODIFY, то обрабатываем изменения в файле
//                 if (kind == StandardWatchEventKinds.ENTRY_MODIFY) {
//                     @SuppressWarnings("unchecked")
//                     WatchEvent pathEvent = (WatchEvent) event;
//                     Path modifiedFile = directory.resolve(pathEvent.context());

//                     // Обрабатываем изменения в файле
//                     handleFileChanges(modifiedFile, addedLines, removedLines);
//                 }
//                 // Если событие - DELETE, то очищаем списки добавленных и удаленных строк
//                 else if (kind == StandardWatchEventKinds.ENTRY_DELETE) {
//                     addedLines.clear();
//                     removedLines.clear();
//                 }
//             }

//             // Сбрасываем ключ, чтобы продолжить получение событий
//             key.reset();
//         }
//     }

//     private static void handleFileChanges(Path modifiedFile, Map addedLines, Map removedLines) throws IOException {
//         try (BufferedReader reader = Files.newBufferedReader(modifiedFile)) {
//             String line;
//             int lineIndex = 1;

//             while ((line = reader.readLine()) != null) {
//                 if (!addedLines.containsValue(line)) {
//                     // Если новая строка не была добавлена ранее, то это добавленная строка
//                     addedLines.put(lineIndex, line);
//                 }
//                 lineIndex++;
//             }

//             // Проверяем, какие строки были удалены
//             Iterator iterator = addedLines.entrySet().iterator();
//             while (iterator.hasNext()) {
//                 Map.Entry entry = iterator.next();
//                 if (!entry.getValue().equals(getLineFromTextFile(modifiedFile,
//                         entry.getKey()))) {
//                     // Если строка была удалена, добавляем ее в список удаленных строк
//                     removedLines.put(entry.getKey(), entry.getValue());
//                     iterator.remove();
//                 }
//             }
//         }

//         // Выводим списки добавленных и удаленных строк
//         System.out.println("Added lines:");
//         addedLines.forEach((lineIndex, line) -> System.out.println(lineIndex + ": " +
//                 line));
//         System.out.println("Removed lines:");
//         removedLines.forEach((lineIndex, line) -> System.out.println(lineIndex + ": "
//                 + line));
//     }

//     private static String getLineFromTextFile(Path filePath, int lineIndex)
//             throws IOException {
//         try (BufferedReader reader = Files.newBufferedReader(filePath)) {
//             for (int i = 1; i < lineIndex; i++) {
//                 reader.readLine();
//             }
//             return reader.readLine();
//         }
//     }
// }
