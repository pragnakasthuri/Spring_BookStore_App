package com.bridgelabz.bookstore.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Data
@Component
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO {
    private int totalPrice;
    private int userId;
    public int bookId;
    public int quantity;
    public String address;
    private boolean cancel;
}
