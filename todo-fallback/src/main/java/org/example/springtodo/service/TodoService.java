package org.example.springtodo.service;

import org.example.springtodo.dto.TodoDto;

import java.util.List;

public interface TodoService {
    List<TodoDto> getAllTodos();

    TodoDto getTodoById(long id);

    List<TodoDto> getTodosByStatus(String status);

    List<TodoDto> getAllTodosSorted(String mode);
}
