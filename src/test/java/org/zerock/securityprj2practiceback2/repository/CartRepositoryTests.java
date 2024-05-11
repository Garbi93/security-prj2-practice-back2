package org.zerock.securityprj2practiceback2.repository;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;
import org.zerock.securityprj2practiceback2.domain.Cart;
import org.zerock.securityprj2practiceback2.domain.CartItem;
import org.zerock.securityprj2practiceback2.domain.Member;
import org.zerock.securityprj2practiceback2.domain.Product;
import org.zerock.securityprj2practiceback2.dto.CartItemListDTO;

import java.util.List;
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
        Long pno = 6L;
        int qty = 3;

        // 이메일과 상품 번호로 장바구니 아이템 확인 -> 없으면 새로 추가 -> 있으면 수량 변경 해주기

        CartItem cartItem = cartItemRepository.getItemOfPno(email, pno);

        // 이미 사용자의 장바구니에 담겨 있다면 ??? 시작 ---------------------
        if (cartItem != null) {
            cartItem.changeQty(qty);
            cartItemRepository.save(cartItem);
            return;
        }
        // 이미 사용자의 장바구니에 담겨 있다면 ??? 끝 ------------------------

        // 장바구니에 담겨 있지 않다면 ???-------시작---------------------------------------
        Optional<Cart> result = cartRepository.getCartOfMember(email);

        Cart cart = null;

        // 1. 아예 장바구니 자체가 없을 수 도??
        if (result.isEmpty()) {
            Member member = Member.builder()
                    .email(email)
                    .build();
            Cart tempCart = Cart.builder().owner(member).build();

            // 새로운 장바구니 만들기
            cart = cartRepository.save(tempCart);

        } else { // 2. 장바구니가 존재 하는데 장바구니 안에 해당 상품의 아이템이 없는 경우

            cart = result.get();

        }


        if (cartItem == null) {

            Product product = Product.builder()
                    .pno(pno)
                    .build();

            cartItem = CartItem.builder().cart(cart).product(product).qty(qty).build();

            cartItemRepository.save(cartItem);
        }
        // 장바구니에 담겨 있지 않다면 ???-------끝---------------------------------------

    }

    @Test
    public void testListOfMember() {

        String email = "user1@aaa.com";

        List<CartItemListDTO> cartItemListDTOList = cartItemRepository.getItemsOfCartDTOByEmail(email);

        for (CartItemListDTO dto : cartItemListDTOList) {
            log.info(dto);
        }
    }


    @Transactional
    @Commit
    @Test
    public void testUpdateByCino() {

        Long cino = 1L;
        int qty = 4;

        Optional<CartItem> result = cartItemRepository.findById(cino);

        CartItem cartItem = result.orElseThrow();

        cartItem.changeQty(qty);

        cartItemRepository.save(cartItem);

    }


    @Test
    public void testDeleteThenList() {

        Long cino = 1L;

        Long cno = cartItemRepository.getCartFromItem(cino);

        cartItemRepository.deleteById(cino);

        List<CartItemListDTO> cartItemList = cartItemRepository.getItemsOfCartDTOByCart(cno);

        for (CartItemListDTO dto : cartItemList) {
            log.info(dto);
        }

    }

}
