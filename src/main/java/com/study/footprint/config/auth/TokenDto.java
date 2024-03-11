package com.study.footprint.config.auth;

import lombok.Builder;
import lombok.Getter;

@Getter
public class TokenDto {

    private String grantType;
    private String accessToken;
    private String refreshToken;

    @Builder
    public TokenDto(String grantType, String accessToken, String refreshToken) {
        this.grantType = grantType;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

}
