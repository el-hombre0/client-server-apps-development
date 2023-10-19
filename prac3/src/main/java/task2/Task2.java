package task2;

import io.reactivex.rxjava3.core.Observable;

import java.util.Random;

public class Task2 {

    /**
     * * 2.1.1 Преобразовать поток из 1000 случайных чисел от 0 до 1000 в поток, содержащий квадраты данных чисел.
     */
    private void ex1() {
        System.out.println("\tTask 1");
        // Генерация массива чисел
        Random random = new Random();
        int n = 1000;
        int[] arr = new int[n];
        for (int i = 0; i < n; i++) {
            arr[i] = random.nextInt(1000);
        }
        // Исходный массив
        System.out.print("Array: ");
        for (int i : arr) {
            System.out.print(i + " ");
        }
        System.out.println();
        // Наблюдаемый объект для считывания чисел массива
        Observable<Integer> observable = Observable.create(tempEmitter -> {
            for (int num : arr) {
                tempEmitter.onNext(num);
            }
        });
        // Новый массив
        System.out.print("Result array: ");
        // Подписка на наблюдаемый объект, возведение в квадрат чисел
        observable.subscribe(tempEmitter -> {
            System.out.print(tempEmitter * tempEmitter + " ");
        });
    }

    /**
     * * 2.2.1. Даны два потока по 1000 элементов: первый содержит случайную букву, второй — случайную цифру.
     * Сформировать поток, каждый элемент которого объединяет элементы из обоих потоков.
     * Например, при входных потоках (A, B, C) и (1, 2, 3) выходной поток — (A1, B2, B3).
     */
    private void ex2() {
        System.out.println("\n\tTask 2");
        // Генерация двух массивов из случайных букв и цифр
        Random random = new Random();
        int n = 1000;
        char[] chars = new char[n];
        int[] ints = new int[n];
        for (int i = 0; i < n; i++) {
            chars[i] = (char) ('a' + random.nextInt(26));
            ints[i] = random.nextInt(1000);
        }
        // Исходные массивы
        System.out.print("Characters: ");
        for (int i : chars) {
            System.out.print((char) i + " ");
        }
        System.out.print("\nIntegers: ");
        for (int i : ints) {
            System.out.print(i + " ");
        }
        System.out.println("]");
        System.out.print("Result array: ");
        // Соединение двух наблюдаемых объектов(массивов) в один методом zip
        Observable.zip(
                        // Отдельно буква
                        Observable.create(tempEmitter -> {
                            for (char c : chars) {
                                tempEmitter.onNext(c);
                            }
                        }),
                        // Отдельно число
                        Observable.create(tempEmitter -> {
                            for (int num : ints) {
                                tempEmitter.onNext(num);
                            }
                        }),
                        // Соединение
                        (character, integer) -> "" + character + integer)
                // В качестве наблюдателя сразу вывод соединенных элементов
                .subscribe(
                        tempEmitter -> {
                            System.out.print(tempEmitter + " ");
                        });
    }

    /**
     * 2.3.1. Дан поток из 10 случайных чисел. Сформировать поток, содержащий все числа, кроме первых трех.
     */
    private void ex3() {
        System.out.println("\n\tTask 3");
        // Генерация массива чисел
        Random random = new Random();
        int n = 10;
        int[] arr = new int[n];
        for (int i = 0; i < n; i++) {
            arr[i] = random.nextInt(1000);
        }
        // Исходный массив
        System.out.print("Array: ");
        for (int i : arr) {
            System.out.print(i + " ");
        }
        System.out.print("\nResult array: ");
        // Наблюдаемый объект - числа массива
        Observable.create(
                        tempEmitter -> {
                            for (int num : arr) {
                                tempEmitter.onNext(num);
                            }
                        })
                // Пропуск первых трёх
                .skip(3)
                // В качестве наблюдателя сразу вывод чисел
                .subscribe(
                        tempEmitter -> {
                            System.out.print(tempEmitter + " ");
                        });
        System.out.println();
    }

    public Task2() {
        ex1();
        ex2();
        ex3();
    }

    public static void main(String[] args) {
        new Task2();
    }
}
