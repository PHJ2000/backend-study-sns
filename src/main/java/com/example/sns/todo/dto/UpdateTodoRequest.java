package com.example.sns.todo.dto;

public class UpdateTodoRequest {
    private String title;
    private String description;
    private boolean completed;

    // getter / setter
    public UpdateTodoRequest() {

    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public boolean isCompleted() {
        return completed;
    }
    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
}
