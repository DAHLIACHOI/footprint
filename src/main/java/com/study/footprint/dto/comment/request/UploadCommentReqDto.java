package com.study.footprint.dto.comment.request;

import jakarta.validation.constraints.NotBlank;

public record UploadCommentReqDto(
        @NotBlank(message = "requiredContent") String content
) {
}
