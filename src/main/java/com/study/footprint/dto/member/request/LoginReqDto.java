package com.study.footprint.dto.member.request;

import lombok.Builder;
import lombok.Getter;

@Getter
public class LoginReqDto {

    private String email;
    private String password;

    @Builder
    public LoginReqDto(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public void deletePassword() {
        this.password = "";
    }
}
