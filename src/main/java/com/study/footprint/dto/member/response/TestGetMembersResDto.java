package com.study.footprint.dto.member.response;

import lombok.Builder;

@Builder
public record TestGetMembersResDto(
        Long memberId,
        String nickname,
        String imageUrl,
        Long postingCount
) {
}
