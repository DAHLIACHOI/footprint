package com.study.footprint.common.converter.common;

import com.study.footprint.common.EnumMapperFieldType;
import com.study.footprint.common.exception.CommonNotFoundException;
import lombok.Getter;

import java.util.Arrays;

@Getter
public enum CityTypeCd implements EnumMapperFieldType {

    SEOUL("서울", "0"),
    SEJONG("세종", "1"),
    INCHEON("인천",  "2"),
    DAEJEON("대전", "3"),
    GWANGJU("광주", "4"),
    DAEGU("대구", "5"),
    ULSAN("울산", "6"),
    BUSAN("부산", "7"),
    GYEONGGI("경기", "8"),
    GANGWON("강원", "9"),
    CHUNGCHEONGBUK("충북", "10"),
    CHUNGCHEONGNAM("충남", "11"),
    JEOLLABUK("전북", "12"),
    JEOLLANAM("전남", "13"),
    GYEONGSANGBUK("경북", "14"),
    GYEONGSANGNAM("경남", "15"),
    JEJU("제주", "16");

    private final String name;
    private final String code;

    CityTypeCd(String name, String code) {
        this.name = name;
        this.code = code;
    }

    public static CityTypeCd enumOf(String code) {
        return Arrays.stream(CityTypeCd.values())
                .filter(c -> c.getCode().equals(code))
                .findFirst()
                .orElseThrow(() -> new CommonNotFoundException("cityNotFound"));
    }
}
