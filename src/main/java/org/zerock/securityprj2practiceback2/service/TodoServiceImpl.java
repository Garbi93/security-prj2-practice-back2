package org.zerock.securityprj2practiceback2.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.zerock.securityprj2practiceback2.domain.Todo;
import org.zerock.securityprj2practiceback2.dto.TodoDTO;
import org.zerock.securityprj2practiceback2.repository.TodoRepository;

import java.util.Optional;

@Service
@Log4j2
@RequiredArgsConstructor
public class TodoServiceImpl implements TodoService{

    private final TodoRepository todoRepository;

    // 조회 하기
    @Override
    public TodoDTO get(Long tno) {

        Optional<Todo> result = todoRepository.findById(tno);

        Todo todo = result.orElseThrow();

        return entityToDTO(todo);
    }

    // 등록 하기
    @Override
    public Long register(TodoDTO dto) {

        Todo todo = dtoToEntity(dto);

        Todo result = todoRepository.save(todo);

        return result.getTno();
    }

    // 수정 하기
    @Override
    public void modify(TodoDTO dto) {

        Optional<Todo> result = todoRepository.findById(dto.getTno());

        Todo todo = result.orElseThrow();

        todo.changeTitle(dto.getTitle());
        todo.changeContent(dto.getContent());
        todo.changeComplete(dto.isComplete());
        todo.changeDueDate(dto.getDueDate());

        todoRepository.save(todo);
    }

    // 삭제 하기
    @Override
    public void remove(Long tno) {

        todoRepository.deleteById(tno);

    }
}
