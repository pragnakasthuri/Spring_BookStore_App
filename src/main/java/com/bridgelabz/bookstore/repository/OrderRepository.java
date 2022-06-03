package com.bridgelabz.bookstore.repository;

import com.bridgelabz.bookstore.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;


public interface OrderRepository extends JpaRepository<Order, Integer> {

    // public Optional<Order> findByUserId(int userId);
}
