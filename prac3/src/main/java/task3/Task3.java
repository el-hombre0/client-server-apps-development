package task3;
/**
 * Реализовать класс UserFriend. Поля — int userId, friendId. Заполнить массив объектов UserFriend случайными данными.
 * Реализовать функцию:
 * Observable<UserFriend> getFriends(int userId), возвращающую поток объектов UserFriend, по заданному userId.
 * (Для формирования потока из массива возможно использование функции Observable.fromArray(T[] arr)).
 * Дан массив из случайных userId. Сформировать поток из этого массива.
 * Преобразовать данный поток в поток объектов UserFriend. Обязательно получение UserFriend через функцию getFriends.
 */

import io.reactivex.rxjava3.core.Observable;

import java.util.Random;

public class Task3 {

    private UserFriend[] arr;

    /**
     * Возвращает поток друзей пользователя, у которых id равен переданному userId
    */
    Observable<UserFriend> getFriends(int userId) {
        return Observable.fromArray(arr).filter(userFriend -> userFriend.getFriendId() == userId);
    }

    public Task3() {
        // Заполнение массива UserFriend случайными данными
        Random random = new Random();
        int n = 100;
        arr = new UserFriend[n];

        // Массив id пользователей
        Integer[] userIds = new Integer[n];

        // Заполнение массивов UserFriend и id пользователей
        for (int i = 0; i < n; i++) {
            arr[i] = new UserFriend(i, random.nextInt(100));
            userIds[i] = random.nextInt(100);
        }
        // Наблюдаемый объект - массив userIds. К каждому элементу применяем функцию getFriends.
        // Observer - вывод объектов UserFriend
        Observable.fromArray(userIds)
                .flatMap(i -> getFriends(i))
                .subscribe(i -> System.out.println("\t" + i));
    }

    public static void main(String[] args) {
        new Task3();
    }
}
