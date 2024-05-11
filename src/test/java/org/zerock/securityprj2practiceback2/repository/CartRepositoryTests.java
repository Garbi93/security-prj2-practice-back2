package org.zerock.securityprj2practiceback2.repository;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;
import org.zerock.securityprj2practiceback2.domain.CartItem;

import java.util.Optional;

@SpringBootTest
@Log4j2
public class CartRepositoryTests {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Transactional
    @Commit
    @Test
    public void testInsertByProduct() {

        String email = "user1@aaa.com";
        Long pno = 5L;
        int qty = 1;

        // 이메일과 상품 번호로 장바구니 아이템 확인 -> 없으면 새로 추가 -> 있으면 수량 변경 해주기

        CartItem cartItem = cartItemRepository.getItemOfPno(email, pno);

        // 이미 사용자의 장바구니에 담겨 있다면 ???
        if (cartItem != null) {
            cartItem.changeQty(qty);
            cartItemRepository.save(cartItem);
            return;
        }

        // 장바구니에 담겨 있지 않다면 ???

    }

    @Test
    public void testListOfMember() {

        String email = "user1@aaa.com";

        cartItemRepository.getItemsOfCartDTOByEmail(email);

    }

}
