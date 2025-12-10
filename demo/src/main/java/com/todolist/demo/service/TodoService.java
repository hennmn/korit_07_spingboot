package com.todolist.demo.service;

import com.todolist.demo.domain.Todo;
import com.todolist.demo.domain.TodoRepository;
import com.todolist.demo.domain.User;
import com.todolist.demo.domain.UserRepository;
import com.todolist.demo.dto.TodoRequestDto;
import com.todolist.demo.dto.TodoRequestRecord;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.file.AccessDeniedException;
import java.util.List;


@Slf4j
@Service
public class TodoService {
    private final UserRepository userRepository;
    private final TodoRepository todoRepository;

    public TodoService(UserRepository userRepository, TodoRepository todoRepository) {
        this.userRepository = userRepository;
        this.todoRepository = todoRepository;
    }

    private User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();  // 인증 객체를 꺼내오는 것임.
        String username = authentication.getName();
        return userRepository.findByUsername(username).orElseThrow(() -> new EntityNotFoundException("User를 찾을 수 없습니다."));
    }

    // 본인 확인 로직을 추가하겠습니다. 그러니까 To-do 객체 내에 있는 User와, 지금 요청을 보내는 애가 동일한 지를 확인하세요.
    private void checkOwnership(Todo todo) throws AccessDeniedException{   // 여기 내부에서만 쓸 거예용 updateTodoConten에서
        if(!todo.getUser().equals(getCurrentUser())) {
            throw new AccessDeniedException("해당 todo에 접근할 수 없습니다.");
        }
    }


    // 1.  사용자 구분 없이 모든 할 일 목록 조회
    @Transactional
    public List<Todo> getTodos() {
        return todoRepository.findAll();
    }

    // 2. 현재 사용자의 모든 할 일 목록 조회
    @Transactional(readOnly = true)
    public List<Todo> getTodosForCurrentUser() {
        User currentUser = getCurrentUser();
        return todoRepository.findByUserId(currentUser.getId());
    }

    // 3. 새로운 to-do 추가  (C에 해당)
    // DTO version
    public Todo createTodo(TodoRequestDto todoRequestDto) {
        User currentUser = getCurrentUser();
        Todo newTodo = new Todo(todoRequestDto.getContent(), currentUser);

        return todoRepository.save(newTodo);
    }

    // Record version
    public Todo createTodo2(TodoRequestRecord todoRequestRecord) {
        User currentUser = getCurrentUser();
        Todo newTodo = new Todo(todoRequestRecord.content(), currentUser);

        return todoRepository.save(newTodo);
    }

    // 4. 할 일 내용 수정
    @Transactional
    public Todo updateTodoContent(Long id, TodoRequestDto updateDto) throws AccessDeniedException {  // AccessDeniedException 이거 접근 불가(내꺼 아닌 거를 수정하려고 할 때)
       Todo todo = todoRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("해당 id를 가진 todo가 없습니다. id : " + id));
       checkOwnership(todo);
       todo.setContent(updateDto.getContent());   // 그냥 updateDto가 안되는 이유는 매개변수 자료형이 String이기 때문임
        return todoRepository.save(todo);
    }

    // 5. 할 일 삭제
    @Transactional
    public void deleteTodo(Long id) throws AccessDeniedException {
        Todo todo = todoRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("해당 id를 가진 todo가 없습니다. id : " + id));
        checkOwnership(todo);  // 내껄 지워야 하니깐 인증
        todoRepository.delete(todo);
    }


    // 6. 할 일 완료 상태 토글
    @Transactional
    public Todo toggleTodoStatus(Long id) throws AccessDeniedException {
        Todo todo = todoRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("해당 id를 가진 todo가 없습니다. id : " + id));
        checkOwnership(todo);
        todo.setCompleted(!todo.isCompleted());
        return todoRepository.save(todo);
    }

    // 7. 완료된 할 일 전체를 삭제하는 로직
    @Transactional
    public void clearCompletedTodos() {
        User currentUser = getCurrentUser();
        todoRepository.deleteByUserAndIsCompleted(currentUser,true);
    }
}
