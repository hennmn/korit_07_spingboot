package com.example.todolist_project.service;

import com.example.todolist_project.domain.AppUser;
import com.example.todolist_project.domain.AppUserRepository;
import com.example.todolist_project.domain.Todo;
import com.example.todolist_project.domain.TodoRepository;
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

    public void deleteTodo(Long userid, Long todoId) {
        AppUser user = appUserRepository.findById(userid).orElseThrow();  // 유저 존재 여부
        Todo todo = todoRepository.findById(todoId).orElseThrow();   // Todo 존재 여부

        if(todo.getUser().getId().equals(user.getId())) {  // Todo가 해당 유저의 Todo 인지 검증
            todoRepository.delete(todo);
        }


        // 아래 코드처럼 유저의 투두가 아니라면 메시지를 던지는 조건문 추가해도 상관없음
//        if (!todo.getUser().getId().equals(user.getId())) {
//            throw new IllegalArgumentException("이 Todo는 해당 유저의 것이 아닙니다.");
//        }


//        return todoRepository.findById(id)
//                .map(todo -> {
//                    todo.setContent(null);
//                    return todo;
//                });   // null 로 바꾸는 건 진짜 삭제가 아니고 객체가 남아있는 거라 지양하셈

    }


    public Todo updateTodoStatus(Long id) {
        Optional<Todo> todos = todoRepository.findById(id);
        


    }
//
//    public Todo clearCompleteTodos() {
//
//    }




}
