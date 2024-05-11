package org.zerock.securityprj2practiceback2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.zerock.securityprj2practiceback2.domain.CartItem;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    // 특정한 사용자의 모든 장바구니 아이템들을 가져올 경우 -> input 은 email output 은 cartItemListDTO



}
