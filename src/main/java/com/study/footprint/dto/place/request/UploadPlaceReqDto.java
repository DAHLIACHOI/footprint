package com.study.footprint.dto.place.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public record UploadPlaceReqDto(
        @NotBlank(message = "requiredPlaceName") String name, // 장소명
        @NotBlank(message = "requiredAddress") String address, // 주소
        @Min(value = -90, message = "notLatitudeFormat") @Max(value = 90, message = "notLatitudeFormat") Double latitude, // 위도
        @Min(value = -180, message = "notLongitudeFormat") @Max(value = 180, message = "notLongitudeFormat") Double longitude // 경도
) {}
