package src.main.java.task1;

// Создать файл формата .txt, содержащий несколько строк текста. С
// помощью пакета java.nio нужно прочитать содержимое файла и вывести данные
// в стандартный поток вывода. 
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Paths;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;

public class Task1 {
    public static void readMyFile() throws IOException {
        Path path = Paths.get("/home/stas/University/4_kurs/7_semestr/Client-Server_Apps_Development/prac2/src/main/java/task1/task1text.txt");
        try (BufferedReader reader = Files.newBufferedReader(path, Charset.forName("UTF-8"))) {

            String currentLine = null;
            while ((currentLine = reader.readLine()) != null) {// while there is content on the current line
                System.out.println(currentLine); // print the current line
            }
        } catch (IOException ex) {
            ex.printStackTrace(); // handle an exception here
        }
    }

    public static void main(String[] args) {
        try {
            readMyFile();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}