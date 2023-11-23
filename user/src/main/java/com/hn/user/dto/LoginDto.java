package com.hn.user.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LoginDto {

    @Schema(description = "用户名", defaultValue = "admin")
    @NotBlank
    private String username;

    @Schema(description = "密码", defaultValue = "21232F297A57A5A743894A0E4A801FC3")
    @NotBlank
    private String password;

    public LoginDto(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
