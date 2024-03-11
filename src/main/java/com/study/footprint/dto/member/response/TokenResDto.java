package com.study.footprint.dto.member.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class TokenResDto {

    private String accessToken;
    private String refreshToken;

    @Builder
    public TokenResDto(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }
}
