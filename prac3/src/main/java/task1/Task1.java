package task1;

/**
 * Реализовать следующую систему:
 * Датчик температуры. Каждую секунду публикует значение температуры (случайное значение от 15 до 30).
 * Датчик CO2. Каждую секунду публикует значение содержания CO2 в воздухе.
 * (Случайное значение от 30 до 100). Сигнализация. Получает значения от датчиков.
 * Если один из показателей превышает норму, выводит предупреждение об этом.
 * Если норму превышаю оба показателя выводит сообщение «ALARM!!!».
 * Норма показателей: Температура — 25. CO2 — 70.
 * Обязательно использование классов Observer и Observable из библиотеки RxJava.
 */

import io.reactivex.rxjava3.core.Observable;
import task1.sensors.CO2Sensor;
import task1.sensors.Sensor;
import task1.sensors.SensorsEnum;
import task1.sensors.TemperatureSensor;

import java.util.HashMap;
import java.util.Map;

public class Task1 {
    // Норма показателей
    private final float normalCO2 = 70;
    private final float normalTemperature = 25;

    // Пределы CO2
    private final float minCO2 = 30;
    private final float maxCO2 = 100;

    //Пределы температуры
    private final float minTemperature = 15;
    private final float maxTemperature = 30;

    // Коллекция для хранения показаний счётчиков
    static private Map<SensorsEnum, Float> sensorsDataMapper(Sensor temperatureSensor, Sensor co2Sensor) {
        /**
         * Коллекция, хранящая показатель и значение
         */
        Map<SensorsEnum, Float> sensorsData = new HashMap<>();
        /**
         * Добавление показателей температуры и CO2
         */
        sensorsData.put(SensorsEnum.TEMPERATURE, temperatureSensor.getCurrentValue());
        sensorsData.put(SensorsEnum.CO2, co2Sensor.getCurrentValue());
        return sensorsData;
    }

    public Task1() {
        /**
         * Observer
         */
        AlarmSystem alarmSystem = new AlarmSystem(normalCO2, normalTemperature);

        // Объединение observable в один
        Observable.zip(
                        // Создание нового observable
                        Observable.create(tempEmitter -> {
                            // Поток для создания датчика температуры. Данные передаются через tempEmitter
                            Thread temperatureSensorThread = new Thread(
                                    new TemperatureSensor(minTemperature, maxTemperature, tempEmitter));
                            temperatureSensorThread.start();
                        }),
                        // Поток для создания датчика CO2. Данные передаются через tempEmitter
                        Observable.create(tempEmitter -> {
                            Thread co2SensorThread = new Thread(new CO2Sensor(minCO2, maxCO2, tempEmitter));
                            co2SensorThread.start();
                        }),
                        // Объединение созданных observable в 1
                        Task1::sensorsDataMapper)
                .subscribe(alarmSystem); // Данные передаются alarmSystem, который подписывается на на их получение от observable
    }

    public static void main(String[] args) {
        new Task1();
    }
}
