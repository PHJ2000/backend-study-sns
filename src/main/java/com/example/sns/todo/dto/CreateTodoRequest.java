package com.example.sns.todo.dto;

public class CreateTodoRequest {
    private String title;
    private String description;

    // getter / setter
    public CreateTodoRequest() {

    }
    public String getTitle() {
        return title;
    }
    public String getDescription() {
        return description;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public void setDescription(String description) {
        this.description = description;
    }
}