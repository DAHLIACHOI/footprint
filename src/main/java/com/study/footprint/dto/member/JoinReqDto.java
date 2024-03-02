package com.study.footprint.dto.member;

import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.logging.log4j.util.Strings;
import org.springframework.security.crypto.password.PasswordEncoder;

@Getter
@NoArgsConstructor
public class JoinReqDto {

    @Pattern(regexp = "^(?:\\w+\\.?)*\\w+@(?:\\w+\\.)+\\w+$", message = "notEmailFormat")
    private String email;

    @Pattern(regexp = "^([a-zA-Z0-9]{8,16})$", message = "notPasswordFormat")
    private String password;

    private String nickName;

    @Builder
    public JoinReqDto(String email, String password, String nickName) {
        this.email = email;
        this.password = password;
        this.nickName = nickName;
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
