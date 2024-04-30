package org.zerock.securityprj2practiceback2.repository;


import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.zerock.securityprj2practiceback2.domain.Todo;

import java.time.LocalDate;
import java.util.Optional;

@SpringBootTest
@Log4j2
public class TodoRepositoryTests {

    @Autowired
    private TodoRepository todoRepository;

    // todoRepository 의 이름이 무엇인지 그냥 보려고 만든 테스트 코드
    @Test
    public void test1() {

        Assertions.assertNotNull(todoRepository);
        log.info(todoRepository.getClass().getName());

    }

    // todo 데이터 생성 테스트 코드
    @Test
    public void testInsert() {

        Todo todo = Todo.builder()
                .title("Title")
                .content("Content....")
                .dueDate(LocalDate.of(2023,12,30))
                .build();

        Todo result = todoRepository.save(todo);

        log.info(result);
    }

    // todo 데이터 Id 값으로 찾아오기 테스트
    @Test
    public void testRead() {
        Long tno = 1L;

        Optional<Todo> result = todoRepository.findById(tno);

        Todo todo = result.orElseThrow();

        log.info(todo);
    }

    // todo 데이터 Id 로 찾고 그 값을 업데이트 해보는 테스트
    @Test
    public void testUpdate() {
        // 일단 먼저 로딩 엔티티 객체를 변경 //setter 사용
        Long tno = 1L;

        Optional<Todo> result = todoRepository.findById(tno);

        Todo todo = result.orElseThrow();

        todo.changeTitle("Update Title");
        todo.changeContent("Update Content");
        todo.changeComplete(true);

        todoRepository.save(todo);
    }

}
