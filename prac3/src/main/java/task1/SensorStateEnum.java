package task1;

/**
 * Перечисление состояний системы
 */
public enum SensorStateEnum {
    OK("OK. TEMPERATURE and CO2 are both NORMAL"),
    HIGH_TEMPERATURE("WARN. TEMPERATURE is HIGH"),
    HIGH_CO2("WARN. CO2 is HIGH"),
    ALARM("ALARM!!! TEMPERATURE and CO2 are both HIGH");

    private String message;

    SensorStateEnum(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
