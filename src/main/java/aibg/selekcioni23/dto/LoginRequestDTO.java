package aibg.selekcioni23.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class LoginRequestDTO extends DTO {
    @NotNull
    private String username;
    @NotNull
    private String password;

    public LoginRequestDTO(String username, String password){
        this.username = username;
        this.password = password;
    }
}
