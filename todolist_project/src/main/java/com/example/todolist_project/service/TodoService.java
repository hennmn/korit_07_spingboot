package com.example.todolist_project.service;

import com.example.todolist_project.domain.AppUser;
import com.example.todolist_project.domain.AppUserRepository;
import com.example.todolist_project.domain.Todo;
import com.example.todolist_project.domain.TodoRepository;
import jakarta.persistence.Id;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class TodoService {
    private AppUserRepository appUserRepository;
    private TodoRepository todoRepository;

    public List<Todo> allTodo() {
        return todoRepository.findAll();
    }

    public Todo addTodo(Todo todo) {
        return todoRepository.save(todo);
    }

    public Optional<Todo> updateTodo(Long id, String content) {
        return todoRepository.findById(id)
                .map(todo -> {
                    todo.setContent(content);
                    return todo;
                });
    }

    public Todo deleteTodo(Long id, String content) {
        Todo todos = todoRepository.findById(id).orElseThrow();

        return todoRepository.save(todos);
    }



//    public Todo updateTodoStatus(Long id) {
//        if()
//        Optional<Todo> todos = todoRepository.findById(id);
//
//    }
//
//    public Todo clearCompleteTodos() {
//
//    }




}
