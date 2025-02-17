package java.db.coursework.entities.enums;

import lombok.Getter;

@Getter
public enum OrderCaste {
    Alpha("Альфа"),
    Beta("Бета"),
    Gamma("Гамма"),
    Delta("Дельта"),
    Epsilon("Эпсилон");
    private String label;

    OrderCaste(String label) {
        this.label = label;
    }
}
