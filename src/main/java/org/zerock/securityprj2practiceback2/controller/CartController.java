package org.zerock.securityprj2practiceback2.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.zerock.securityprj2practiceback2.dto.CartItemDTO;
import org.zerock.securityprj2practiceback2.dto.CartItemListDTO;
import org.zerock.securityprj2practiceback2.service.CartService;

import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Log4j2
@RequestMapping("/api/cart")
public class CartController {

    private final CartService cartService;

    @PreAuthorize("#itemDTO.email == authentication.name") // 장바구니 소유 이메일과 수정하는 사람의 이메일이 동일할때 작동 시키기
    @PostMapping("/change")
    public List<CartItemListDTO> changeCart(@RequestBody CartItemDTO itemDTO) {

        log.info(itemDTO);

        if (itemDTO.getQty() <= 0) {
            return cartService.remove(itemDTO.getCino());
        }

        return cartService.addOrModify(itemDTO);

    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/items")
    public List<CartItemListDTO> getCartItems(Principal principal) {

        // 스프링 시큐리티에 로그인한 사용자 아이디 정보
        String email = principal.getName();

        log.info("email: " + email);

        return cartService.getCartItems(email);

    }

    @PreAuthorize("hasAnyRole('ROLE_USER')")
    @DeleteMapping("/{cino}")
    public List<CartItemListDTO> removeFromCart(@PathVariable("cino") Long cino) {

        log.info("cart item no: " + cino);

        return cartService.remove(cino);

    }
}
