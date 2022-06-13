package com.bridgelabz.bookstore.controller;

import com.bridgelabz.bookstore.dto.OrderDTO;
import com.bridgelabz.bookstore.dto.ResponseDTO;
import com.bridgelabz.bookstore.model.Email;
import com.bridgelabz.bookstore.model.Order;
import com.bridgelabz.bookstore.service.IEmailService;
import com.bridgelabz.bookstore.service.IOrderService;
import com.bridgelabz.bookstore.util.TokenUtil;
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

    @Autowired
    private TokenUtil tokenUtil;

    @Autowired
    private IEmailService emailService;

    /***
     * Implemented placeOrder method to place the order
     * After placing the order a mail will send to the registered mail id
     * @param orderDTO - passing orderDTO as param
     * @return
     */
    @PostMapping("/placeOrder/{userId}")
    public ResponseEntity<ResponseDTO> placeOrder(@PathVariable("userId") int userId, @RequestBody OrderDTO orderDTO) {
        Order order = orderService.placeOrder(userId, orderDTO);
        String token = tokenUtil.createToken(order.getOrderId());
        Email email = new Email(order.getUser().getEmailId(), "Order Placed Successfully", "Hi " + order.getUser().getFirstName() +
                            " " + order.getUser().getLastName() +
                            ", Click the given link to get details \n" + emailService.getOrderLink(token));
        emailService.sendEmail(email);
        ResponseDTO response = new ResponseDTO("Order Placed Successfully", order, token);
        return new ResponseEntity<> (response, HttpStatus.CREATED);
    }

    /***
     * Implemented cancelOrder method to cancel the order
     * After cancelling the order a mail will send to the registered mail id
     * @param token
     * @return
     */
    @PutMapping("/cancelOrder/{orderId}/{token}")
    public ResponseEntity<ResponseDTO> cancelOrder(@PathVariable int orderId,@PathVariable String token) {
        int tokenId = tokenUtil.decodeToken(token);
        Order order = orderService.cancelOrder(orderId, tokenId);
        Email emailData = new Email(order.getUser().getEmailId(), "Cancelled order", "Hi " + order.getUser().getFirstName() +
                            " " + order.getUser().getLastName() +
                            ", Your order has been cancelled successfully");
        emailService.sendEmail(emailData);
        ResponseDTO response = new ResponseDTO("Order cancelled Successfully for Id " +orderId, order, token);
        return new ResponseEntity<> (response, HttpStatus.ACCEPTED);
    }

    /***
     * Implemented getOrderById method to get order by id from database
     * @param token - passing token as param
     * @return
     */
    @GetMapping("/orders/{token}")
    public ResponseEntity<ResponseDTO> getOrderById(@PathVariable("token") String token){
        int tokenId = tokenUtil.decodeToken(token);
        Order order = orderService.getOrderById(tokenId);
        ResponseDTO response = new ResponseDTO("Orders of user", order);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /***
     * Implemented getOrders method to get all the orders from database
     * @return
     */
    @GetMapping("/orders")
    public ResponseEntity<ResponseDTO> getOrders(){
        List<Order> orderList = orderService.getOrders();
        ResponseDTO response = new ResponseDTO("Orders of user", orderList);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /***
     * Implemented verifyOrder method to check the order list
     * @param token - passing token as param
     * @return
     */
    @GetMapping("/verify/{token}")
    public ResponseEntity<ResponseDTO> verifyOrder(@PathVariable("token") String token) {
        Order order = orderService.verifyOrder(token);
        ResponseDTO responseDTO = new ResponseDTO("Your order", order, token);
        return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.OK);
    }
}
