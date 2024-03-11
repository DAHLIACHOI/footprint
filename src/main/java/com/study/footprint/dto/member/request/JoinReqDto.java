package com.study.footprint.dto.member.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.logging.log4j.util.Strings;
import org.springframework.security.crypto.password.PasswordEncoder;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class JoinReqDto {

    @Pattern(regexp = "^(?:\\w+\\.?)*\\w+@(?:\\w+\\.)+\\w+$", message = "notEmailFormat")
    private String email;

    @Pattern(regexp = "^([a-zA-Z0-9]{8,16})$", message = "notPasswordFormat")
    private String password;

    @NotNull(message = "requiredNickname")
    private String nickname;

    @Builder
    public JoinReqDto(String email, String password, String nickname) {
        this.email = email;
        this.password = password;
        this.nickname = nickname;
    }

    public void encodePassword(PasswordEncoder passwordEncoder, String password) {
        if ( !Strings.isBlank(password)) {
            this.password = passwordEncoder.encode(password);
        }
    }

    public void deletePassword() {
        this.password = "";
    }

}
