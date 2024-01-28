package com.study.footprint.common.util.aes;

import lombok.Builder;
import lombok.Getter;

@Getter
public class AesDecDto {
    private final byte[] saltBytes;
    private final byte[] originalIvBytes;
    private final byte[] originalEncryptedData;

    @Builder
    public AesDecDto(byte[] saltBytes, byte[] originalIvBytes, byte[] originalEncryptedData) {
        this.saltBytes = saltBytes;
        this.originalIvBytes = originalIvBytes;
        this.originalEncryptedData = originalEncryptedData;
    }
}
