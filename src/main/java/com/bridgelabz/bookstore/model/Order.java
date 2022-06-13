package com.bridgelabz.bookstore.model;

import com.bridgelabz.bookstore.dto.OrderDTO;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
@Table(name = "Orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "order_id")
    private int orderId;

    private LocalDate orderDate;
    private int totalPrice;
    private int quantity;
    private String address;

    @JsonIgnoreProperties(value = {"applications", "hibernateLazyInitializer"})
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id")
    private Book book;

    @JsonIgnoreProperties(value = {"applications", "hibernateLazyInitializer"})
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserRegistrationData user;

    private boolean cancel;

    public Order() {
    }

    public Order(OrderDTO orderDTO) {
        this.updateOrder(orderDTO, user, book);
    }

    public void updateOrder(OrderDTO orderDTO, UserRegistrationData user, Book book) {
        this.orderDate = LocalDate.now();
        this.user = user;
        this.book = book;
        this.quantity = orderDTO.getQuantity();
        this.address = orderDTO.getAddress();
        this.totalPrice = orderDTO.getTotalPrice();
    }

    public void createOrder(OrderDTO orderDTO) {
        this.orderDate = LocalDate.now();
        this.totalPrice = orderDTO.getTotalPrice();
        this.address = orderDTO.getAddress();
        this.quantity = orderDTO.getQuantity();
    }
}
