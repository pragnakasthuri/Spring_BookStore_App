package com.bridgelabz.bookstore.controller;

import com.bridgelabz.bookstore.dto.OrderDTO;
import com.bridgelabz.bookstore.dto.ResponseDTO;
import com.bridgelabz.bookstore.model.Order;
import com.bridgelabz.bookstore.service.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    IOrderService orderService;

    @PostMapping("/placeOrder/{userId}")
    public ResponseEntity<ResponseDTO> placeOrder(@PathVariable("userId") int userId, @RequestBody OrderDTO orderDTO) {
        Order order = orderService.placeOrder(userId, orderDTO);
        ResponseDTO response = new ResponseDTO("Order Placed Successfully", order);
        return new ResponseEntity<> (response, HttpStatus.CREATED);
    }

    @PutMapping("/cancelOrder/{orderId}/{userId}")
    public ResponseEntity<ResponseDTO> cancelOrder(@PathVariable int orderId,@PathVariable int userId) {
        String order = orderService.cancelOrder(orderId,userId);
        ResponseDTO response = new ResponseDTO("id "+orderId,  order);
        return new ResponseEntity<> (response, HttpStatus.ACCEPTED);
    }

    @PutMapping("/updateOrder/{orderId}/{userId}")
    public ResponseEntity<ResponseDTO> updateOrder(@PathVariable int orderId, @RequestBody OrderDTO orderDto) {
        Order order = orderService.updateOrder(orderId, orderDto);
        ResponseDTO response = new ResponseDTO("id "+orderId, order);
        return new ResponseEntity<> (response, HttpStatus.ACCEPTED);
    }

    @GetMapping("/orders/{userId}")
    public ResponseEntity<ResponseDTO> getOrderById(@PathVariable("userId") int userId){
        Order order = orderService.getOrderById(userId);
        ResponseDTO response = new ResponseDTO("Orders of user", order);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/orders")
    public ResponseEntity<ResponseDTO> getOrders(){
        List<Order> orderList = orderService.getOrders();
        ResponseDTO response = new ResponseDTO("Orders of user", orderList);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
