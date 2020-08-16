package com.scott.neptune.common.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.scott.neptune.common.exception.NeptuneBlogException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.io.IOException;

/**
 * @Author: scott
 * @Email: <a href="mailto:liuweigeek@outlook.com">Scott Lau</a>
 * @Date: 2020/8/17 00:18
 * @Description:
 */
@Slf4j
@RequiredArgsConstructor
@Converter(autoApply = true)
public class JpaConverterJson implements AttributeConverter<Object, String> {

    private final ObjectMapper objectMapper;

    @Override
    public String convertToDatabaseColumn(Object attribute) {
        try {
            return objectMapper.writeValueAsString(attribute);
        } catch (JsonProcessingException e) {
            log.error("convert database column exception: ", e);
            throw new NeptuneBlogException("convert database column exception: ", e);
        }
    }

    @Override
    public Object convertToEntityAttribute(String dbData) {
        try {
            return objectMapper.readValue(dbData, Object.class);
        } catch (IOException e) {
            log.error("convert database data JSON exception: ", e);
            throw new NeptuneBlogException("convert database data JSON exception: ", e);
        }
    }

}