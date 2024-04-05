package com.study.footprint.common.converter;

import com.study.footprint.common.converter.common.CityTypeCd;
import jakarta.persistence.AttributeConverter;

public class CityTypeCdConverter implements AttributeConverter<CityTypeCd, String> {
    
    @Override
    public String convertToDatabaseColumn(CityTypeCd attribute) {
        if (attribute == null) {
            return null;
        }
        return attribute.getCode();
    }

    @Override
    public CityTypeCd convertToEntityAttribute(String dbData) {
        if (dbData == null) {
            return null;
        }

        return CityTypeCd.enumOf(dbData);
    }
}
