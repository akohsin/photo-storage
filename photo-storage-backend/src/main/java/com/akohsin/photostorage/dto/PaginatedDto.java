package com.akohsin.photostorage.dto;

import java.util.List;

public class PaginatedDto<T> {
    private List<T> content;
    private Long totalCount;

    public PaginatedDto(List<T> content, Long totalCount) {
        this.content = content;
        this.totalCount = totalCount;
    }

    public List<T> getContent() {
        return content;
    }

    public void setContent(List<T> content) {
        this.content = content;
    }

    public Long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Long totalCount) {
        this.totalCount = totalCount;
    }
}
