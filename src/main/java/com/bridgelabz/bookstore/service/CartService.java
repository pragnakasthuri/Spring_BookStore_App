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

    @Override
    public void removeCartData(int cartId) {
        cartRepository.deleteById(cartId);
    }

    @Override
    public Cart updateQuantity(int cartId, int quantity) {
        Cart cart = cartRepository.findById(cartId).orElseThrow(() -> new UserRegistrationException("Cart Item with id " + cartId + " does not exists"));
        cart.setQuantity(quantity);
        return cartRepository.save(cart);
    }

    @Override
    public Cart updateCartData(int cartId, CartDTO cartDTO) {
        Cart cart = cartRepository.findById(cartId).orElseThrow(() -> new UserRegistrationException("Cart Item with id " + cartId + " does not exists"));
        cart.setQuantity(cartDTO.getQuantity());
        return cartRepository.save(cart);
    }

    @Override
    public Cart getCartDataById(int cartId) {
        return cartRepository.findById(cartId).orElseThrow(() -> new UserRegistrationException("Cart Item with id " + cartId + " does not exists"));
    }

    public List<Cart> getCartData() {
        return cartRepository.findAll();
    }
}