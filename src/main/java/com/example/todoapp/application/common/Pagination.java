package com.example.todoapp.application.common;

import lombok.Getter;

@Getter
public class Pagination {
    private final int page;
    private final int size;
    private final int offset;
    private final int totalPages;
    private final int totalElements;

    public Pagination(int page, int size, int totalElements) {
        this.page = page;
        this.size = size;
        this.offset = (page - 1) * size;
        this.totalPages = (int) Math.ceil((double) totalElements/ size);
        this.totalElements = totalElements;
    }
}
