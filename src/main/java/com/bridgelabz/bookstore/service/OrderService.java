package com.bridgelabz.bookstore.service;

import com.bridgelabz.bookstore.dto.OrderDTO;
import com.bridgelabz.bookstore.exception.UserRegistrationException;
import com.bridgelabz.bookstore.model.Book;
import com.bridgelabz.bookstore.model.Order;
import com.bridgelabz.bookstore.model.UserRegistrationData;
import com.bridgelabz.bookstore.repository.OrderRepository;
import com.bridgelabz.bookstore.repository.UserRegistrationRepository;
import com.bridgelabz.bookstore.util.TokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class OrderService implements IOrderService {

    @Autowired
    UserRegistrationRepository userRegistrationRepository;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    UserRegistrationService userRegistrationService;

    @Autowired
    BookService bookService;

    @Autowired
    private TokenUtil tokenUtil;

    /**
     * Implemented placeOrder method to place orders
     * @param orderDTO - passing orderDTO param
     * @return
     */
    @Override
    public Order placeOrder(int userId, OrderDTO orderDTO) {
        Optional<UserRegistrationData> user = Optional.ofNullable(userRegistrationService.getUserRegistrationDataById(userId));
        Optional<Book> book = Optional.ofNullable(bookService.getBookById(orderDTO.getBookId()));
        if (user.isPresent()) {
            Order order = new Order();
            order.createOrder(orderDTO);
            order.setTotalPrice(order.getQuantity() * book.get().getBookPrice());
            order.setUser(user.get());
            order.setBook(book.get());
            return orderRepository.save(order);
        }
        return null;
    }

    /**
     * Implemented cancelOrder method to cancel an order
     * @param orderId - passing orderId param
     * @return
     */
    @Override
    public Order cancelOrder(int orderId, int userId) {
        Optional<Order> order = orderRepository.findById(orderId);
        if (order.isPresent()) {
            order.get().setCancel(true);
            return orderRepository.save(order.get());
        }
        return null;
    }

    /**
     * Implemented getOrderById method to find order by id
     * @param orderId - passing orderId param
     * @return
     */
    @Override
    public Order getOrderById(int orderId) {
        return orderRepository.findById(orderId).orElseThrow(()-> new UserRegistrationException("Get Call is not Successful"));
    }

    /**
     * Implemented getOrders method to find all orders
     * @return
     */
    @Override
    public List<Order> getOrders() {
        return orderRepository.findAll();
    }

    /**
     * Implemented updateOrder method to update the order
     * @param orderId - passing order id as param
     * @param order
     * @return
     */
    @Override
    public Order updateOrder(int orderId, OrderDTO order) {
        Order order1 = orderRepository.findById(orderId).orElseThrow(() -> new UserRegistrationException("Order update failed"));
        Optional<Book> book = Optional.ofNullable(bookService.getBookById(order.getBookId()));
        order1.setCancel(order.isCancel());
        order1.setTotalPrice(order.getQuantity() * book.get().getBookPrice());
        order1.setQuantity(order.getQuantity());
        order1.setAddress(order.getAddress());
        orderRepository.save(order1);
        return order1;
    }

    /**
     * Implemented verifyOrder method to get the details of order
     * @param token - passing token param
     * @return
     */
    @Override
    public Order verifyOrder(String token) {
        Order orderData = this.getOrderById(tokenUtil.decodeToken(token));
        return orderRepository.save(orderData);
    }
}
