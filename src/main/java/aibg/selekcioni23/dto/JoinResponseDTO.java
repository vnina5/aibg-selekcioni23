package aibg.selekcioni23.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JoinResponseDTO extends DTO {
    private String assignment;

    public JoinResponseDTO(String assignment) {
        this.assignment = assignment;
    }
}
