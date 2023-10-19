package task1.sensors;

/**
 * Перечисление датчиков
 */
public enum SensorsEnum {
    CO2("CO2"),
    TEMPERATURE("Temperature");

    private String sensorName;

    SensorsEnum(String sensorName) {
        this.sensorName = sensorName;
    }

    public String getSensorName() {
        return sensorName;
    }
}
