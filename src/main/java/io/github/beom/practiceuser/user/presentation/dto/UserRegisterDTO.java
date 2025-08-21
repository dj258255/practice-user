package io.github.beom.practiceuser.user.presentation.dto;

import lombok.Data;

@Data
public class UserRegisterDTO {

    private String id;
    private String pw;
    private String email;
    private boolean del;
    private boolean social;
}
