package com.study.footprint.common.converter.common;

import com.study.footprint.common.EnumMapperFieldType;
import lombok.Getter;

import java.util.Arrays;

@Getter
public enum ReportTargetTypeCd implements EnumMapperFieldType {

    USER("유저 신고", 0),
    POSTING("게시글 신고", 1),
    COMMENT("댓글 신고", 2);

    private String name;
    private int code;

    ReportTargetTypeCd(String name, int code) {
        this.name = name;
        this.code = code;
    }

    public static ReportTargetTypeCd enumOf(int code) {
        return Arrays.stream(ReportTargetTypeCd.values())
                .filter(c -> c.getCode() == code)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("신고 대상이 올바르지 않습니다."));
    }
}
