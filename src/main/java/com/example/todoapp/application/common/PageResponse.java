package com.example.todoapp.application.common;

import lombok.Getter;

import java.util.List;

@Getter
public class PageResponse<T> {

    private int page;
    private int size;
    private int totalPages;
    private int totalElements;
    private List<T> data;

    public PageResponse(int page, int size, int totalPages, int totalElements, List<T> data) {
        this.page = page;
        this.size = size;
        this.totalPages = totalPages;
        this.totalElements = totalElements;
        this.data = data;
    }

}
