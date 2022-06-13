package com.bridgelabz.bookstore.service;

import com.bridgelabz.bookstore.dto.CartDTO;
import com.bridgelabz.bookstore.exception.UserRegistrationException;
import com.bridgelabz.bookstore.model.Book;
import com.bridgelabz.bookstore.model.Cart;
import com.bridgelabz.bookstore.model.UserRegistrationData;
import com.bridgelabz.bookstore.repository.CartRepository;
import com.bridgelabz.bookstore.repository.UserRegistrationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CartService implements ICartService {

    @Autowired
    IUserRegistrationService userRegistrationService;

    @Autowired
    BookService bookService;

    @Autowired
    CartRepository cartRepository;

    @Autowired
    UserRegistrationRepository userRegistrationRepository;

    /**
     * Implemented addToCart method to add books and user in the cart
     * @param cartDTO - passing cartDTO param
     * @return
     */
    @Override
    public Cart addToCart(CartDTO cartDTO) {
        Optional<UserRegistrationData> user = Optional.ofNullable(userRegistrationService.getUserRegistrationDataById(cartDTO.userId));
        if (user.isPresent()) {
            Book book = bookService.getBookById(cartDTO.bookId);
            Cart cart = new Cart(user.get(), book, cartDTO.quantity);
            return cartRepository.save(cart);
        }
        return null;
    }

    /**
     * Implemented removeCartData method to delete cart by id
     * @param cartId - passing cartId param
     */
    @Override
    public void removeCartData(int cartId) {
        cartRepository.deleteById(cartId);
    }

    /**
     * Implemented updateQuantity method to update book quantity
     * @param cartId - passing cartId param
     * @param quantity - passing quantity param
     * @return
     */
    @Override
    public Cart updateQuantity(int cartId, int quantity) {
        Cart cart = cartRepository.findById(cartId).orElseThrow(() -> new UserRegistrationException("Cart Item with id " + cartId + " does not exists"));
        cart.setQuantity(quantity);
        return cartRepository.save(cart);
    }

    /**
     * Implemented updateCartData method to update book Data
     * @param cartId
     * @param cartDTO
     * @return
     */
    @Override
    public Cart updateCartData(int cartId, CartDTO cartDTO) {
        Cart cart = cartRepository.findById(cartId).orElseThrow(() -> new UserRegistrationException("Cart Item with id " + cartId + " does not exists"));
        cart.setQuantity(cartDTO.getQuantity());
        return cartRepository.save(cart);
    }

    /**
     * Implemented getCartDataById method to find cart by id
     * @param cartId - passing cartId param
     * @return
     */
    @Override
    public Cart getCartDataById(int cartId) {
        return cartRepository.findById(cartId).orElseThrow(() -> new UserRegistrationException("Cart Item with id " + cartId + " does not exists"));
    }

    /**
     * Implemented getCartData method to find all the carts
     * @return
     */
    public List<Cart> getCartData() {
        return cartRepository.findAll();
    }
}