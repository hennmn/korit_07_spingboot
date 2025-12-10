package com.example.cardatabase4.service;

import com.example.cardatabase4.domain.Owner;
import com.example.cardatabase4.domain.OwnerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class OwnerService {
    private final OwnerRepository ownerRepository;

    // 모든 Owner 목록 조회
    public List<Owner> getOwner() {
        return ownerRepository.findAll();
    }

    // 새로운 Owner 저장
    public Owner addOwner(Owner owner) {
        return ownerRepository.save(owner);
    }

    // Owner 한 명 조회
    public Optional<Owner> getOwnerById(Long id) {
        return ownerRepository.findById(id);
    }

    // Owner 한 명 삭제
    public boolean deleteOwner(Long id) {
        if(ownerRepository.existsById(id)) {
            ownerRepository.deleteById(id);
            return true;
        }
        return false;
    }

    // Owner 수정
    public Optional<Owner> updateOwner(Long id, Owner ownerDetails) {
        return ownerRepository.findById(id)
                .map(owner -> {
                    owner.setCars(ownerDetails.getCars());
                    owner.setLastName(ownerDetails.getLastName());      // Owner에 final이랑 @NonNull이랑 겹쳐 있으면 오류남(정확히는  final 때문에 오류남)
                    owner.setFirstName(ownerDetails.getFirstName());   // Owner에 final이랑 @NonNull이랑 겹쳐 있으면 오류남
                    return owner;
                });
    }

/*
    Optional<Todo> todos = todoRepository.findById(id);
        if(todos.isPresent()) {

        }
        return todos;

        return todoRepository.findById(id)
                .map(todo -> {
                todo.setConent(content);
                return todo;
             });
 */


    /*
    Owner 전체 조회 / id 별 조회 / Owner 객체 추가 /
    Owner 객체 삭제 / Owner 객체 수정
     */
}