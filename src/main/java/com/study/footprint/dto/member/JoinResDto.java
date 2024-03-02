package com.study.footprint.dto.member;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class JoinResDto {

    Long id;

    @Builder
    public JoinResDto(Long id) {
        this.id = id;
    }
}
