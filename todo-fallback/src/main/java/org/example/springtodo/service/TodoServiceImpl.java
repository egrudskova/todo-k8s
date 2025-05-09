package org.example.springtodo.service;

import lombok.RequiredArgsConstructor;
import org.example.springtodo.dto.TodoDto;
import org.example.springtodo.entity.Todo;
import org.example.springtodo.exception.TodoNotFoundException;
import org.example.springtodo.model.SortMode;
import org.example.springtodo.model.TodoStatus;
import org.example.springtodo.repository.TodoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class TodoServiceImpl implements TodoService {
    private final TodoRepository repository;

    private TodoDto convertToDto(final Todo todo) {
        TodoDto todoDto = new TodoDto();
        todoDto.setId(todo.getId());
        todoDto.setName(todo.getName());
        todoDto.setDescription(todo.getDescription());
        todoDto.setDue(todo.getDue());
        todoDto.setStatus(String.valueOf(todo.getStatus()));

        return todoDto;
    }

    @Override
    public List<TodoDto> getAllTodos() {
        return repository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public TodoDto getTodoById(final long id) {
        return repository.findById(id)
                .map(this::convertToDto)
                .orElseThrow(() -> new TodoNotFoundException("Todo not found with id: " + id));
    }

    @Override
    public List<TodoDto> getTodosByStatus(String status) {
        TodoStatus todoStatus = TodoStatus.valueOf(status.toUpperCase());
        return repository
                .findTodosByStatus(todoStatus)
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<TodoDto> getAllTodosSorted(String sortMode) {
        SortMode mode = SortMode.valueOf(sortMode.toUpperCase());
        Comparator<Todo> comparator = switch (mode) {
            case DUE -> Comparator.comparing(Todo::getDue);
            case STATUS -> Comparator.comparing(Todo::getStatus);
        };
        return repository
                .findAll()
                .stream()
                .sorted(comparator)
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
}
