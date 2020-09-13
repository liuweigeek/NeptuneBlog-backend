package com.scott.neptune.userserver.convertor;

import com.scott.neptune.userclient.dto.RelationshipDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;


/**
 * @Author: scott
 * @Email: <a href="mailto:liuweigeek@outlook.com">Scott Lau</a>
 * @Date: 2020/8/17 00:18
 * @Description:
 */
@Slf4j
public class RelationshipConnectionMvcConverter implements Converter<String, RelationshipDto.ConnectionEnum> {
    @Override
    public RelationshipDto.ConnectionEnum convert(String name) {
        return RelationshipDto.ConnectionEnum.getEnum(name);
    }
}