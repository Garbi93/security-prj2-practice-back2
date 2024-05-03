package org.zerock.securityprj2practiceback2.repository.search;

import org.springframework.data.domain.Page;
import org.zerock.securityprj2practiceback2.domain.Todo;
import org.zerock.securityprj2practiceback2.dto.PageRequestDTO;

public interface TodoSearch {
    Page<Todo> search1(PageRequestDTO pageRequestDTO);

}
