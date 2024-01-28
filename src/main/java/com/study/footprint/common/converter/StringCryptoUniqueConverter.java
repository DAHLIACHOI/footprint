package com.study.footprint.common.converter;

import com.study.footprint.common.util.aes.AesUtil;
import io.micrometer.common.util.StringUtils;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Convert;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Convert
public class StringCryptoUniqueConverter implements AttributeConverter<String, String> {

    private final AesUtil aesUtil;

    public StringCryptoUniqueConverter(AesUtil aesUtil) {
        this.aesUtil = aesUtil;
    }

    @Override
    public String convertToDatabaseColumn(String attribute) {

        if(StringUtils.isBlank(attribute)) {
            return attribute;
        }

        String encodedData = aesUtil.encodeUnique(attribute);

        log.info(String.format("String Unique to DB : %s : %s", attribute, encodedData));

        return encodedData;
    }

    @Override
    public String convertToEntityAttribute(String dbData) {

        if(StringUtils.isBlank(dbData)) {
            return dbData;
        }

        String decodedData = aesUtil.decodeUnique(dbData);

        return decodedData;
    }
}

