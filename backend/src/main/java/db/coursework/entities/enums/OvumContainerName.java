package db.coursework.entities.enums;

import lombok.Getter;

public enum OvumContainerName {
    OVUMRECEIVER("Яйцеприемник"),
    BOTTLE("Бутыль");
    @Getter
    private String label;

    OvumContainerName(String label) {
        this.label = label;
    }
}
