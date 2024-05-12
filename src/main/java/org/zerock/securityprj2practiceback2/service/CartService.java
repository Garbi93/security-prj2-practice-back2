package org.zerock.securityprj2practiceback2.service;

import org.springframework.transaction.annotation.Transactional;
import org.zerock.securityprj2practiceback2.dto.CartItemDTO;
import org.zerock.securityprj2practiceback2.dto.CartItemListDTO;

import java.util.List;

@Transactional
public interface CartService {

    List<CartItemListDTO> addOrModify(CartItemDTO cartItemDTO);

    List<CartItemListDTO> getCartItems(String email);

    List<CartItemListDTO> remove(Long cino);

}
