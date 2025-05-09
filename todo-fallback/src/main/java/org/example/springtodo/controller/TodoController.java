package org.example.springtodo.controller;

import lombok.RequiredArgsConstructor;
import org.example.springtodo.dto.TodoDto;
import org.example.springtodo.exception.TodoNotFoundException;
import org.example.springtodo.service.TodoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v2/todos")
@RequiredArgsConstructor
public class TodoController {
    private final TodoService service;

    @GetMapping
    public ResponseEntity<List<TodoDto>> getAllTodos() {
        try {
            List<TodoDto> todos = service.getAllTodos();
            return new ResponseEntity<>(todos, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<TodoDto> findById(@PathVariable long id) {
        try {
            TodoDto todo = service.getTodoById(id);
            return new ResponseEntity<>(todo, HttpStatus.OK);
        } catch (TodoNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<TodoDto>> getTodosByStatus(@PathVariable String status) {
        try {
            List<TodoDto> todos = service.getTodosByStatus(status);
            return new ResponseEntity<>(todos, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/sort/{mode}")
    public ResponseEntity<List<TodoDto>> getAllTodosSorted(@PathVariable String mode) {
        try {
            List<TodoDto> todos = service.getAllTodosSorted(mode);
            return new ResponseEntity<>(todos, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
