package com.bridgelabz.bookstore.service;

import com.bridgelabz.bookstore.dto.OrderDTO;
import com.bridgelabz.bookstore.model.Order;

import java.util.List;

public interface IOrderService {
    Order placeOrder(int userId, OrderDTO orderDTO);

    Order cancelOrder(int orderId,int userId);

    Order updateOrder(int orderId, OrderDTO orderDTO);

    Order getOrderById(int userId);

    List<Order> getOrders();

    Order verifyOrder(String token);
}
