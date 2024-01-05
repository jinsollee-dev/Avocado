package com.example.avocado.service;


import com.example.avocado.domain.Cart;
import com.example.avocado.domain.CartItem;
import com.example.avocado.domain.Product;
import com.example.avocado.domain.User;
import com.example.avocado.dto.CartItemDto;
import com.example.avocado.dto.CartListDto;
import com.example.avocado.repository.CartItemRepository;
import com.example.avocado.repository.CartRepository;
import com.example.avocado.repository.ProductRepository;
import com.example.avocado.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.util.StringUtils;


import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final UserRepository userRepository;
    private final ProductRepository repository;



    // 장바구니 담기
    public Long addCart(CartItemDto cartItemDto, String email) {

        User user= userRepository.findByUsername(email);
        Cart cart = cartRepository.findByUser_Id(user.getId());

        // 장바구니가 존재하지 않는다면 생성
        if (cart == null) {
            cart = Cart.createCart(user);
            cartRepository.save(cart);
        }

        Product product =repository.findById(cartItemDto.getItemId()).orElseThrow(EntityNotFoundException::new);
        CartItem cartItem = cartItemRepository.findByCartIdAndProductPno(cart.getId(), product.getPno());


        if (cartItem == null) {
            cartItem = CartItem.createCartItem(cart, product);
            cartItemRepository.save(cartItem);
        }

        return cartItem.getId();
    }


    // 장바구니 조회
    @Transactional(readOnly = true)
    public List<CartListDto> getCartList(String email) {

        List<CartListDto> cartListDtos = new ArrayList<>();

      User user = userRepository.findByUsername(email);
        Cart cart = cartRepository.findByUser_Id(user.getId());

        if (cart == null) {
            return cartListDtos;
        }

        cartListDtos = cartItemRepository.findCartListDto(cart.getId());
        return cartListDtos;
    }

    // 현재 로그인한 사용자가 장바구니의 주인인지 확인
    @Transactional(readOnly = true)
    public boolean validateCartItem(Long cartItemId, String email) {

        // 현재 로그인된 사용자
        User curmember = userRepository.findByUsername(email);

        CartItem cartItem = cartItemRepository.findById(cartItemId).orElseThrow(EntityNotFoundException::new);
        User savedmember = cartItem.getCart().getUser();

        if (StringUtils.equals(curmember.getUsername(), savedmember.getUsername())) {
            return true;
        }
        return false;
    }

//    // 장바구니 상품 수량 변경
//    public void updateCartItemCount(Long cartItemId, int count) {
//        CartItem cartItem = cartItemRepository.findById(cartItemId).orElseThrow(EntityNotFoundException::new);
//        cartItem.updateCount(count);
//    }

    // 장바구니 상품 삭제
    public void deleteCartItem(Long cartItemId) {
        CartItem cartItem = cartItemRepository.findById(cartItemId).orElseThrow(EntityNotFoundException::new);
        cartItemRepository.delete(cartItem);
    }

//    // 장바구니 상품(들) 주문
//    public Long orderCartItem(List<CartOrderDto> cartOrderDtoList, String email) {
//
//        List<OrderDto> orderDtoList = new ArrayList<>();
//
//        for (CartOrderDto cartOrderDto : cartOrderDtoList) {
//            CartItem cartItem = cartItemRepository.findById(cartOrderDto.getCartItemId()).orElseThrow(EntityNotFoundException::new);
//            OrderDto orderDto = new OrderDto();
//            orderDto.setItemId(cartItem.getItem().getId());
//            orderDto.setCount(cartItem.getCount());
//            orderDtoList.add(orderDto);
//        }
//
//        Long orderId = orderService.orders(orderDtoList, email);
//
//        // 주문한 장바구니 상품을 제거
//        for (CartOrderDto cartOrderDto : cartOrderDtoList) {
//            CartItem cartItem = cartItemRepository.findById(cartOrderDto.getCartItemId()).orElseThrow(EntityNotFoundException::new);
//            cartItemRepository.delete(cartItem);
//        }
//        return orderId;
//    }
}
