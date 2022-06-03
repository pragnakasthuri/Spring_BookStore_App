package com.bridgelabz.bookstore.controller;

import com.bridgelabz.bookstore.dto.CartDTO;
import com.bridgelabz.bookstore.dto.ResponseDTO;
import com.bridgelabz.bookstore.model.Cart;
import com.bridgelabz.bookstore.repository.CartRepository;
import com.bridgelabz.bookstore.service.ICartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cart")
public class CartController {

    @Autowired
    public ICartService cartService;

    @Autowired
    CartRepository cartRepository;

    @PostMapping("/add")
    ResponseEntity<ResponseDTO> addToCart(@RequestBody CartDTO cartDTO) {
        Cart cart = cartService.addToCart(cartDTO);
        ResponseDTO responseDTO = new ResponseDTO("Product Added To Cart ", cart);
        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }

    @GetMapping("/get")
    ResponseEntity<ResponseDTO> getCartData() {
        List<Cart> cart = cartService.getCartData();
        ResponseDTO response = new ResponseDTO("All Items in Cart ", cart);
        return new ResponseEntity<ResponseDTO>(response, HttpStatus.OK);
    }

    @GetMapping("/get/{userId}")
    ResponseEntity<ResponseDTO> getCartDataById(@PathVariable("userId") int userId) {
        Cart cart = cartService.getCartDataById(userId);
        ResponseDTO response = new ResponseDTO("All Items in Cart for user ", cart);
        return new ResponseEntity<ResponseDTO>(response, HttpStatus.OK);
    }

    @DeleteMapping("/remove/{cartId}")
    ResponseEntity<ResponseDTO> removeCartData(@PathVariable("cartId") int cartId) {
        cartService.removeCartData(cartId);
        ResponseDTO response = new ResponseDTO("Delete call success for item ", "deleted id:" + cartId);
        return new ResponseEntity<ResponseDTO>(response, HttpStatus.OK);
    }

    @PutMapping("/update/{cartId}/{quantity}")
    ResponseEntity<ResponseDTO> updateQuantity( @PathVariable("cartId") int cartId, @PathVariable("quantity") int quantity) {
        Cart cart = cartService.updateQuantity( cartId, quantity);
        ResponseDTO response = new ResponseDTO("Update call success for item ", cart);
        return new ResponseEntity<ResponseDTO>(response, HttpStatus.OK);
    }

    @PutMapping("/update/{cartId}")
    ResponseEntity<ResponseDTO> updateCartData( @PathVariable("cartId") int cartId, @RequestBody CartDTO cartDTO) {
        Cart cart = cartService.updateCartData( cartId, cartDTO);
        ResponseDTO response = new ResponseDTO("Update call success for item ", cart);
        return new ResponseEntity<ResponseDTO>(response, HttpStatus.OK);
    }
}
