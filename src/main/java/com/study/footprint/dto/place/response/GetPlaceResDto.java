package com.study.footprint.dto.place.response;

import lombok.Builder;

import java.util.Objects;

@Builder
public record GetPlaceResDto(
        Long placeId,
        Double latitude,
        Double longitude
){

    // equals, hashCode 메소드를 사용하여 placeId를 기준으로 중복을 제거한다.
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GetPlaceResDto that = (GetPlaceResDto) o;
        return Objects.equals(placeId, that.placeId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(placeId);
    }
}
