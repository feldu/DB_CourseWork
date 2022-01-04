package db.coursework.entities.enums;


import lombok.Getter;

@Getter
public enum FutureJobTypeName {
    HIGH_TEMP("HIGH_TEMP", "Высокие температуры"),
    LOW_TEMP("LOW_TEMP", "Низкие температуры"),
    HIGH_OXY("HIGH_OXY", "Высокий кислород"),
    LOW_OXY("LOW_OXY", "Низкий кислород");

    private final String value;
    private final String label;

    FutureJobTypeName(String value, String label) {
        this.value = value;
        this.label = label;
    }
}
