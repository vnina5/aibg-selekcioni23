package aibg.selekcioni23.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErrorResponseDTO extends DTO {
    private String message;

    public  ErrorResponseDTO(){}
    public ErrorResponseDTO(String message) {
        this.message = message;
    }
}
