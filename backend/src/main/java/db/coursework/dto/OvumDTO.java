package db.coursework.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@AllArgsConstructor
public class OvumDTO {
    @NotNull
    Long id;
    @NotNull
    private boolean isBud;
    private Date fertilizationTime;
    private Date embryoTime;
    private Date babyTime;
}
