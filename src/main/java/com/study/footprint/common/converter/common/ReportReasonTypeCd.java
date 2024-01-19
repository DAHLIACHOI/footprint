package com.study.footprint.common.converter.common;

import com.study.footprint.common.EnumMapperFieldType;
import lombok.Getter;

import java.util.Arrays;

@Getter
public enum ReportReasonTypeCd implements EnumMapperFieldType {

    ABUSE("욕설이나 차별 혐오성 글", 0),
    LEWD("음란, 선정적으로 유해한 글", 1),
    ILLEGAL("불법 정보를 포함하는 글", 2),
    PAPERING("영리목적/스팸홍보/도배글", 3),
    PERSONAL_INFO_EXPOSURE("개인 정보를 노출하는 글", 4),
    VIOLENT("폭력/차별적/불쾌한 글", 5),
    ETC("기타", 6);

    private String name;
    private int code;

    ReportReasonTypeCd(String name, int code) {
        this.name = name;
        this.code = code;
    }

    public static ReportReasonTypeCd enumOf(int code) {
        return Arrays.stream(ReportReasonTypeCd.values())
                .filter(c -> c.getCode() == code)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("없는 신고 사유 코드 입니다."));
    }

}
