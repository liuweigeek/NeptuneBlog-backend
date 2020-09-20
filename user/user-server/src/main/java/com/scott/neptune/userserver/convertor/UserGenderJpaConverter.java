package com.scott.neptune.userserver.convertor;

import com.scott.neptune.userclient.enumerate.GenderEnum;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * @Author: scott
 * @Email: <a href="mailto:liuweigeek@outlook.com">Scott Lau</a>
 * @Date: 2020/8/17 00:18
 * @Description:
 */
@Slf4j
@Converter(autoApply = true)
public class UserGenderJpaConverter implements AttributeConverter<GenderEnum, String> {

    @Override
    public String convertToDatabaseColumn(GenderEnum attribute) {
        if (attribute == null) {
            return null;
        }
        return attribute.getValue();
    }

    @Override
    public GenderEnum convertToEntityAttribute(String dbData) {
        return GenderEnum.getEnum(dbData);
    }
}