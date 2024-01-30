package com.study.footprint.dto.member;

import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class JoinReqDto {

    @Pattern(regexp = "^(?:\\w+\\.?)*\\w+@(?:\\w+\\.)+\\w+$")
    private String email;

    @Pattern(regexp = "^([a-zA-Z0-9]{8,16})$")
    private String password;

    private String nickName;

    @Builder
    public JoinReqDto(String email, String password, String nickName) {
        this.email = email;
        this.password = password;
        this.nickName = nickName;
    }

}
