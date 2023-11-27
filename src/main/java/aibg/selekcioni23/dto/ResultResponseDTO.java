package aibg.selekcioni23.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResultResponseDTO extends DTO {
    private String message;

    public ResultResponseDTO(String message) {
        this.message = message;
    }
}
