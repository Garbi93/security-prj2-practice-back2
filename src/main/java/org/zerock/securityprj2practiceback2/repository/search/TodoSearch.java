package org.zerock.securityprj2practiceback2.repository.search;

import org.springframework.data.domain.Page;
import org.zerock.securityprj2practiceback2.domain.Todo;

public interface TodoSearch {
    Page<Todo> search1();

}
