package task1.sensors;

import io.reactivex.rxjava3.core.Emitter;

/**
 * Датчик CO2
 */
public class CO2Sensor extends Sensor{
    public CO2Sensor(float minValue, float maxValue, Emitter emitter) {
        super(minValue, maxValue, emitter, SensorsEnum.CO2);
    }
}
