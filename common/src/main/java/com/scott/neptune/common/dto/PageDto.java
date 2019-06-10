package com.scott.neptune.common.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class PageDto implements Serializable {

    private int pageNumber = 1;

    private int pageSize = 10;

    private int totalCount;

}
