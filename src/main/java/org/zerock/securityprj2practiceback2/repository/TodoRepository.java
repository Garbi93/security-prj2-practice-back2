package org.zerock.securityprj2practiceback2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.zerock.securityprj2practiceback2.domain.Todo;
import org.zerock.securityprj2practiceback2.repository.search.TodoSearch;

public interface TodoRepository extends JpaRepository<Todo, Long>, TodoSearch {

}
