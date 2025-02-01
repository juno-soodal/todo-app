package com.example.todoapp.domain.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@ToString
public class Schedule {

    private Long id;
    private final Long authorId;
    private String toDo;
    private final LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public Schedule(Long authorId, String toDo) {
        this.authorId = authorId;
        this.toDo = toDo;
        this.createdAt = LocalDateTime.now();
        this.modifiedAt = LocalDateTime.now();
    }

    public Schedule(Long id, Long authorId, String toDo, LocalDateTime createdAt, LocalDateTime modifiedAt) {
        this.id = id;
        this.authorId = authorId;
        this.toDo = toDo;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }

    public void updateSchedule(String toDo) {
        this.toDo = toDo;
        this.modifiedAt = LocalDateTime.now();
    }

    public Schedule withId(Long id, Schedule schedule) {
        return new Schedule(id, schedule.getAuthorId(), schedule.getToDo(),schedule.getCreatedAt(), schedule.getModifiedAt());
    }
}
