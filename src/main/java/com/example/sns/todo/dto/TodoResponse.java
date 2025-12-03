package com.example.sns.todo.dto;

import java.time.LocalDateTime;
import com.example.sns.todo.Todo;

public class TodoResponse {
    private Long id;
    private String title;
    private String description;
    private boolean completed;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // 생성자, getter
    public long getId() {
        return id;
    }
    public String getTitle() {
        return title;
    }
    public String getDescription() {
        return description;
    }
    public boolean isCompleted() {
        return completed;
    }
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public static TodoResponse from(Todo todo) {
        TodoResponse res = new TodoResponse();
        res.id = todo.getId();
        res.title = todo.getTitle();
        res.description = todo.getDescription();
        res.completed = todo.isCompleted();
        res.createdAt = todo.getCreatedAt();
        res.updatedAt = todo.getUpdatedAt();
        return res;
    }
}