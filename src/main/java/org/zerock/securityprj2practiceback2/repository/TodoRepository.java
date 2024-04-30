package org.zerock.securityprj2practiceback2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.zerock.securityprj2practiceback2.domain.Todo;

public interface TodoRepository extends JpaRepository<Todo, Long> {

}
