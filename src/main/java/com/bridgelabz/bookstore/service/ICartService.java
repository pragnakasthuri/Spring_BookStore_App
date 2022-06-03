package com.bridgelabz.bookstore.service;

import com.bridgelabz.bookstore.dto.CartDTO;
import com.bridgelabz.bookstore.model.Cart;

import java.util.List;

public interface ICartService {

    Cart addToCart(CartDTO cartDTO);

    List<Cart> getCartData();

    Cart getCartDataById(int userId);

    Cart updateCartData(int cartId, CartDTO cartDTO);

    Cart updateQuantity(int cartId, int quantity);

    void removeCartData(int cartId);
}
