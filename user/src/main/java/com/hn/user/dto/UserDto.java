package com.hn.user.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserDto {

    @NotNull(groups = Update.class)
    private Long id;
    @NotBlank(groups = {Save.class, Login.class})
    private String username;
    @NotBlank(groups = {Save.class, Login.class})
    private String password;

    @Pattern(regexp = "^1[0-9]{10}$")
    private String phone;
    private String permissions;
    private Integer status;
    private Integer isDel;
    private String createTime;
    private String updateTime;

    public UserDto(String username, String password, String phone, String permissions, Integer status, Integer isDel,
                   String createTime, String updateTime) {
        this.username = username;
        this.password = password;
        this.phone = phone;
        this.permissions = permissions;
        this.status = status;
        this.isDel = isDel;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    public interface Save {
    }

    public interface Update {
    }

    public interface Login {
    }

}
