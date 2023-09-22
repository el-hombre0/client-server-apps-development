package task2;

import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.*;

// Программа запрашивает у пользователя на вход число. 
// Программа имитирует обработку запроса пользователя в виде задержки от 1 до 5 секунд выводит результат: число, возведенное в квадрат. 
// В момент выполнения запроса пользователь имеет возможность отправить новый запрос. 
// Реализовать с использованием Future.

public class Main {
    public static void main(String[] args) {
        // Executor нужен для запуска задачи в потоке, служит оболочкой над Runnable
        // ExecutorService это особый Executor, имеющий доступ к Future для отслеживания
        // процесса выполнения задачи

        // Создание пула из одного потока с помощью фабрики Executers
        ExecutorService executorService = Executors.newSingleThreadExecutor();

        try {
            Scanner sc = new Scanner(System.in);

            while (true) {
                System.out.print("\nВведите число для возведения в квадрат (или 'q' для выхода): ");
                String in = sc.nextLine();

                if (in.equals("q")) {
                    sc.close();
                    break;
                }

                try {
                    int number = Integer.parseInt(in); // Преобразование строки в int
                    // submit отправляет выполняемую задачу в ExecutorService и возвращает результат
                    // Future
                    Future<Integer> future = executorService.submit(() -> {
                        Random rand = new Random();
                        int delay = rand.nextInt(5); // Генерация задержи
                        System.out.println("Задержка " + delay);
                        TimeUnit.SECONDS.sleep(delay); // Конвертация задержки в секунды
                        return number * number;
                    });

                    System.out.println("Выполняется запрос...");

                    while (!future.isDone()) {
                        // Ждем, пока запрос не завершится
                    }

                    Object result = future.get();
                    System.out.println("Результат: " + result);
                } catch (NumberFormatException e) {
                    System.out.println("Ошибка: введите корректное число!");
                } catch (InterruptedException | ExecutionException e) {
                    System.out.println("Ошибка при выполнении запроса: " + e.getMessage());
                }
            }

        } finally {
            if (executorService != null) {
                executorService.shutdown(); // shutdown для завершения работы программы
            }
        }
    }
}
