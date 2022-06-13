package com.bridgelabz.bookstore.service;

import com.bridgelabz.bookstore.dto.ResponseDTO;
import com.bridgelabz.bookstore.model.Email;
import org.springframework.http.ResponseEntity;

public interface IEmailService {
    ResponseEntity<ResponseDTO> sendEmail(Email email);

    String getLink(String token);

    String getOrderLink(String token);
}
