package com.study.footprint.dto.member.request;

import lombok.Builder;
import lombok.Getter;

@Getter
public class TokenReqDto {

    private String accessToken;
    private String refreshToken;

    @Builder
    public TokenReqDto(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }
}
