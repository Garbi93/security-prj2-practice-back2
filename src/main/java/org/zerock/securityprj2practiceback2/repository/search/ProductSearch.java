package org.zerock.securityprj2practiceback2.repository.search;

import org.zerock.securityprj2practiceback2.dto.PageRequestDTO;
import org.zerock.securityprj2practiceback2.dto.PageResponseDTO;
import org.zerock.securityprj2practiceback2.dto.ProductDTO;

public interface ProductSearch {

    PageResponseDTO<ProductDTO> searchList(PageRequestDTO pageRequestDTO);

}
