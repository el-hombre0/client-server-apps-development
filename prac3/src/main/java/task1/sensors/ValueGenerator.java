package task1.sensors;

import java.util.Random;

/**
 * Генератор случайного значения для сенсора
 */
public class ValueGenerator {
    static float valueGenerator(float min, float max){
        return min + new Random().nextFloat() * (max - min);
    }
}
