package com.study.footprint.dto.comment.response;

import lombok.Builder;

@Builder
public record GetCommentResDto(
        Long userId,
        String nickname,
        String content,
        Long commentId
) {
}
