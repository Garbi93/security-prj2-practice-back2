package org.zerock.securityprj2practiceback2.service;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Log4j2
public class TodoServiceTests {

    // 실제 기능 코드를 의존성 주입 받아와 테스트에 사용
    @Autowired
    TodoService todoService;


    // 실제 조회 기능 테스트
    @Test
    public void  testGet() {

        Long tno = 50L;

        log.info(todoService.get(tno));

    }

}
