package db.coursework.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class OrderDTO {
    @Null
    private Long id;
    @NotNull
    private Integer humanNumber;
    @NotNull
    private String caste;
    @NotNull
    private List<String> futureJobTypes;
    @NotNull
    private boolean isProcessing;
}
