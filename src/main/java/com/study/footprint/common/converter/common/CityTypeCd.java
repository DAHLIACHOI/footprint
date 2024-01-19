package com.study.footprint.common.converter.common;

import com.study.footprint.common.EnumMapperFieldType;
import lombok.Getter;

import java.util.Arrays;

@Getter
public enum CityTypeCd implements EnumMapperFieldType {

    SEOUL("서울", 0),
    SEJONG("세종특별자치시", 1),
    INCHEON("인천", 2),
    DAEJEON("대전", 3),
    GWANGJU("광주", 4),
    DAEGU("대구", 5),
    ULSAN("울산", 6),
    BUSAN("부산", 7),
    GYEONGGI_DO("경기", 8),
    GANGWON_DO("강원", 9),
    CHUNGCHEONGBUK_DO("충북", 10),
    CHUNGCHEONGNAM_DO("충남", 11),
    JEOLLABUK_DO("전북", 12),
    JEOLLANAM_DO("전남", 13),
    GYEONGSANGBUK_DO("경북", 14),
    GYEONGSANGNAM_DO("경남", 15),
    JEJU("제주특별자치도", 16);

    private final String name;
    private final int code;

    CityTypeCd(String name, int code) {
        this.name = name;
        this.code = code;
    }

    public static CityTypeCd enumOf(int code) {
        return Arrays.stream(CityTypeCd.values())
                .filter(c -> c.getCode() == code)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("없는 도시"));
    }

}
