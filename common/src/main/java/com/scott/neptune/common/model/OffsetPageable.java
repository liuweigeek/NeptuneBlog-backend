package com.scott.neptune.common.model;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

/**
 * @Author: scott
 * @Email: <a href="mailto:liuweigeek@outlook.com">scott</a>
 * @Date: 2019/10/6 00:40
 * @Description: NeptuneBlog
 */
public class OffsetPageable implements Pageable {

    private final long offset;
    private final int limit;
    private final Sort sort;

    protected OffsetPageable(long offset, int limit) {
        this(offset, limit, Sort.unsorted());
    }

    protected OffsetPageable(long offset, int limit, Sort sort) {
        if (offset < 0) {
            throw new IllegalArgumentException("Offset index must not be less than zero!");
        }
        if (limit < 1) {
            throw new IllegalArgumentException("Limit must not be less than one!");
        }
        this.offset = offset;
        this.limit = limit;
        this.sort = sort;
    }

    public static OffsetPageable of(long offset, int limit) {
        return new OffsetPageable(offset, limit);
    }

    public static OffsetPageable of(long offset, int limit, Sort sort) {
        return new OffsetPageable(offset, limit, sort);
    }

    @Override
    public int getPageNumber() {
        return (int) (offset / limit);
    }

    @Override
    public int getPageSize() {
        return limit;
    }

    @Override
    public long getOffset() {
        return offset;
    }

    @Override
    public Sort getSort() {
        return sort;
    }

    @Override
    public Pageable next() {
        return new OffsetPageable(getOffset() + getPageSize(), getPageSize());
    }

    public Pageable previous() {
        return hasPrevious() ? new OffsetPageable(getOffset() - getPageSize(), getPageSize()) : this;
    }

    @Override
    public Pageable previousOrFirst() {
        return hasPrevious() ? previous() : first();
    }

    @Override
    public Pageable first() {
        return new OffsetPageable(0, getPageSize());
    }

    @Override
    public boolean hasPrevious() {
        return offset > limit;
    }
}
