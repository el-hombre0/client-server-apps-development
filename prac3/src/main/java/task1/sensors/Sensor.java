package task1.sensors;

import io.reactivex.rxjava3.core.Emitter;

// Класс, описывающий сенсор
public abstract class Sensor implements Runnable{
    private final float minValue;
    private final float maxValue;
    private float currentValue = 0;

    /**
     * Интерфейс для передачи сигналов
     */
    private final Emitter emitter;
    private SensorsEnum sensorsEnum;

    public Sensor(float minValue, float maxValue, Emitter emitter, SensorsEnum sensorsEnum) {
        this.minValue = minValue;
        this.maxValue = maxValue;
        this.emitter = emitter;
        this.sensorsEnum = sensorsEnum;

        this.generateValue();
    }

    public float getCurrentValue() {
        return currentValue;
    }

    /**
     * Сгенерировать случайное значение датчика
     */
    public void generateValue(){
        currentValue = ValueGenerator.valueGenerator(minValue, maxValue);
    }

    /**
     * Метод потока для работы в течение 20 секунд
     */
    @Override
    public void run() {
        int i = 0;
        while(i < 20){
            try {
                Thread.sleep(1000);
                this.generateValue();
                /**
                 * Получение следующего сигнала
                 */
                emitter.onNext(this);
                i++;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
