package com.bridgelabz.bookstore.repository;

import com.bridgelabz.bookstore.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CartRepository extends JpaRepository<Cart, Integer> {

    @Query(value = "SELECT * FROM cart where user_Id = :userId", nativeQuery = true)
    List<Cart> findAllCartsByUserId(int userId);

    Cart getById(int cartId);
}
