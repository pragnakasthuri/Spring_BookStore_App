package com.bridgelabz.bookstore.controller;

import com.bridgelabz.bookstore.dto.CartDTO;
import com.bridgelabz.bookstore.dto.ResponseDTO;
import com.bridgelabz.bookstore.model.Cart;
import com.bridgelabz.bookstore.service.ICartService;
import com.bridgelabz.bookstore.util.TokenUtil;
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
    private TokenUtil tokenUtil;

    /***
     * Implemented addToCart method to add books to cart
     * @param cartDTO - passing cartDTO as param
     * @return - ResponseEntity
     */
    @PostMapping("/add")
    ResponseEntity<ResponseDTO> addToCart(@RequestBody CartDTO cartDTO) {
        Cart cart = cartService.addToCart(cartDTO);
        String token = tokenUtil.createToken(cart.getCartId());
        ResponseDTO responseDTO = new ResponseDTO("Product Added To Cart ", cart, token);
        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }

    /***
     * Implemented getCartData method to get all cart data form database
     * @return - ResponseEntity
     */
    @GetMapping("/get")
    ResponseEntity<ResponseDTO> getCartData() {
        List<Cart> cart = cartService.getCartData();
        ResponseDTO response = new ResponseDTO("All Items in Cart ", cart);
        return new ResponseEntity<ResponseDTO>(response, HttpStatus.OK);
    }

    /***
     * Implemented getCartDataById method to get cart data by id from database
     * @param token - passing token as param
     * @return - ResponseEntity
     */
    @GetMapping("/get/{token}")
    ResponseEntity<ResponseDTO> getCartDataById(@PathVariable("token") String token) {
        int tokenId = tokenUtil.decodeToken(token);
        Cart cart = cartService.getCartDataById(tokenId);
        ResponseDTO response = new ResponseDTO("All Items in Cart for user ", cart);
        return new ResponseEntity<ResponseDTO>(response, HttpStatus.OK);
    }

    /***
     * Implemented removeCartData method to delete the cart data from database
     * @param token - passing token as param
     * @return - ResponseEntity
     */
    @DeleteMapping("/remove/{token}")
    ResponseEntity<ResponseDTO> removeCartData(@PathVariable("token") String token) {
        int tokenId = tokenUtil.decodeToken(token);
        cartService.removeCartData(tokenId);
        ResponseDTO response = new ResponseDTO("Delete call success for item ", "deleted id:" + tokenId);
        return new ResponseEntity<ResponseDTO>(response, HttpStatus.OK);
    }

    /***
     * Implemented updateQuantity method to update quantity of the books in the cart
     * @param token - passing token as param
     * @param quantity - passing quantity as param
     * @return - ResponseEntity
     */
    @PutMapping("/updatequantity/{quantity}/{token}")
    ResponseEntity<ResponseDTO> updateQuantity( @PathVariable("token") String token, @PathVariable("quantity") int quantity) {
        int tokenId = tokenUtil.decodeToken(token);
        Cart cart = cartService.updateQuantity( tokenId, quantity);
        ResponseDTO response = new ResponseDTO("Update call success for item ", cart);
        return new ResponseEntity<ResponseDTO>(response, HttpStatus.OK);
    }

    /***
     * Implemented updateCartData method to update data of the books in the cart
     * @param token - passing token as param
     * @return - ResponseEntity
     */
    @PutMapping("/update/{token}")
    ResponseEntity<ResponseDTO> updateCartData( @PathVariable("token") String token, @RequestBody CartDTO cartDTO) {
        int tokenId = tokenUtil.decodeToken(token);
        Cart cart = cartService.updateCartData( tokenId, cartDTO);
        ResponseDTO response = new ResponseDTO("Update call success for item ", cart);
        return new ResponseEntity<ResponseDTO>(response, HttpStatus.OK);
    }
}
