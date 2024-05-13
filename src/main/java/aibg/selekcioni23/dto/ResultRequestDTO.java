package aibg.selekcioni23.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResultRequestDTO extends DTO {
    private int result;

    public ResultRequestDTO() {}
    public ResultRequestDTO(int result) {
        this.result = result;
    }
}
