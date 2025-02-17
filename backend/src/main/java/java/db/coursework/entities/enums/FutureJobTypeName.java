package java.db.coursework.entities.enums;


import lombok.Getter;

@Getter
public enum FutureJobTypeName {
    HIGH_TEMP("Высокие температуры"),
    LOW_TEMP("Низкие температуры"),
    HIGH_OXY("Высокий кислород"),
    LOW_OXY("Низкий кислород");

    private final String label;

    FutureJobTypeName(String label) {
        this.label = label;
    }
}
