package com.study.footprint.dto.member;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class JoinResDto {

    Long id;

    @Builder
    public JoinResDto(Long id) {
        this.id = id;
    }
}
