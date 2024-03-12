package com.study.footprint.dto.posting.request;

import com.study.footprint.dto.place.request.UploadPlaceReqDto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import lombok.Builder;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Builder
public record UploadPostingReqDto(
        @NotBlank(message = "requiredTitle") String title,
        @NotBlank(message = "requiredContent") String content,
        @PastOrPresent(message = "pastOrPresentDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date recordDate,
        UploadPlaceReqDto uploadPlaceReqDto,
        Boolean isPublic) {
}