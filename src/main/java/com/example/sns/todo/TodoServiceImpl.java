package com.example.sns.todo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import com.example.sns.todo.dto.CreateTodoRequest;
import com.example.sns.todo.dto.UpdateTodoRequest;
import com.example.sns.todo.dto.TodoResponse;

@Service
@RequiredArgsConstructor
public class TodoServiceImpl implements TodoService {

    private final TodoRepository todoRepository;

    @Override
    public TodoResponse createTodo(CreateTodoRequest request) {
        Todo todo = new Todo(request.getTitle(), request.getDescription());
        Todo saved = todoRepository.save(todo);
        return TodoResponse.from(saved);
    }

    @Override
    public TodoResponse getTodo(Long id) {
        Todo todo = todoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Todo not found: " + id));
        return TodoResponse.from(todo);
    }

    @Override
    public TodoResponse updateTodo(Long id, UpdateTodoRequest request) {
        Todo todo = todoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Todo not found: " + id));

        todo.setTitle(request.getTitle());
        todo.setDescription(request.getDescription());
        todo.setCompleted(request.isCompleted());

        Todo saved = todoRepository.save(todo);
        return TodoResponse.from(saved);
    }

    @Override
    public void deleteTodo(Long id) {
        if (!todoRepository.existsById(id)) {
            throw new IllegalArgumentException("Todo not found: " + id);
        }
        todoRepository.deleteById(id);
    }
}
