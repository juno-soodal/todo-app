package com.example.todoapp.domain.entity;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
public class Schedule {

    @Setter
    private Long id;
    private Long authorId;
    private String toDo;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public Schedule(Long authorId, String toDo) {
        this.authorId = authorId;
        this.toDo = toDo;
        this.createdAt = LocalDateTime.now();
        this.modifiedAt = LocalDateTime.now();
    }

    public void updateSchedule(String toDo) {
        this.toDo = toDo;
        this.modifiedAt = LocalDateTime.now();
    }
}
