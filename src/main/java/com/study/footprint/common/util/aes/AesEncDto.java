package com.study.footprint.common.util.aes;

import lombok.Builder;
import lombok.Getter;

import javax.crypto.Cipher;

@Getter
public class AesEncDto {
    private final Cipher cipher;

    private final byte[] encryptedData;

    @Builder
    public AesEncDto(Cipher cipher, byte[] encryptedData) {
        this.cipher = cipher;
        this.encryptedData = encryptedData;
    }
}
