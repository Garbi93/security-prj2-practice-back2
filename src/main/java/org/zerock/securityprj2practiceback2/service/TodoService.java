package org.zerock.securityprj2practiceback2.service;

import jakarta.transaction.Transactional;
import org.zerock.securityprj2practiceback2.domain.Todo;
import org.zerock.securityprj2practiceback2.dto.TodoDTO;

@Transactional
public interface TodoService {

    TodoDTO get(Long tno);

    // Todo Entity 를 TodoDTO 타입으로 변경
    default TodoDTO entityToDTO(Todo todo) {
        return TodoDTO.builder()
                        .tno(todo.getTno())
                        .title(todo.getTitle())
                        .content(todo.getContent())
                        .complete(todo.isComplete())
                        .dueDate(todo.getDueDate())
                        .build();

    }

    // TodoDTO 를 Todo Entity 타입으로 변경
    default Todo dtoToEntity(TodoDTO todoDTO) {
        return Todo.builder()
                .tno(todoDTO.getTno())
                .title(todoDTO.getTitle())
                .content(todoDTO.getContent())
                .complete(todoDTO.isComplete())
                .dueDate(todoDTO.getDueDate())
                .build();

    }
}
