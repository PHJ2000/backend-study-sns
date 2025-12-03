package com.example.sns.todo;
import java.util.Optional;
import com.example.sns.todo.dto.CreateTodoRequest;
import com.example.sns.todo.dto.UpdateTodoRequest;
import com.example.sns.todo.dto.TodoResponse;

public interface TodoService {
    TodoResponse createTodo(CreateTodoRequest request);
    TodoResponse getTodo(Long id);
    TodoResponse updateTodo(Long id, UpdateTodoRequest request);
    void deleteTodo(Long id);
}
