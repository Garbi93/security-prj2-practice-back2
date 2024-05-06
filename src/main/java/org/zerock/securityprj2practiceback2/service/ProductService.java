package org.zerock.securityprj2practiceback2.service;

import org.springframework.transaction.annotation.Transactional;
import org.zerock.securityprj2practiceback2.dto.PageRequestDTO;
import org.zerock.securityprj2practiceback2.dto.PageResponseDTO;
import org.zerock.securityprj2practiceback2.dto.ProductDTO;

@Transactional
public interface ProductService {

    PageResponseDTO<ProductDTO> getList(PageRequestDTO pageRequestDTO);

}
