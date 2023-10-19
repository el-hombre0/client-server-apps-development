package task1.sensors;

import io.reactivex.rxjava3.core.Emitter;

/**
 * Датчик температуры
 */
public class TemperatureSensor extends Sensor{
    public TemperatureSensor(float minValue, float maxValue, Emitter emitter) {
        super(minValue, maxValue, emitter, SensorsEnum.TEMPERATURE);
    }
}
