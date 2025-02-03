package com.example.todoapp.application.common;

import lombok.Getter;

import java.util.List;

@Getter
public class PageResponse<T> {

    private final int page;
    private final int size;
    private final int totalPages;
    private final int totalElements;
    private final List<T> data;

    public PageResponse(int page, int size, int totalPages, int totalElements, List<T> data) {
        this.page = page;
        this.size = size;
        this.totalPages = totalPages;
        this.totalElements = totalElements;
        this.data = data;
    }

}
