package com.bridgelabz.bookstore.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public @Data class LoginDTO {
    public String emailId;
    public String password;
}
