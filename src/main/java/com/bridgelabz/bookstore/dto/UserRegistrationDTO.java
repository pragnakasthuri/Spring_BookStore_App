package com.bridgelabz.bookstore.dto;

import lombok.ToString;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

public @ToString class UserRegistrationDTO {
    @NotEmpty(message = "First name cannot be empty")
    @Pattern(regexp = "^[A-Z]{1}[a-zA-Z]{2,}$", message = "Invalid first name")
    public String firstName;

    @NotEmpty(message = "Last name cannot be empty")
    @Pattern(regexp = "^[A-Z]{1}[a-zA-Z]{2,}$", message = "Invalid last name")
    public String lastName;

    @NotEmpty(message = "Address cannot be empty")
    @Pattern(regexp = "^(.*[A-Za-z,.-0-9\\s]){3,}$", message = "Invalid address")
    public String address;

    @NotEmpty(message = "EmailId cannot be empty!")
    @Pattern(regexp = "^[a-zA-Z-9]+([._+-]*[0-9A-Za-z]+)*@[a-zA-Z0-9]+.[a-zA-Z]{2,4}([.][a-z]{2,4})?$",
            message = "Invalid emailId")
    public String emailId;

    @NotEmpty(message = "Password cannot be empty!")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*]{1})[A-Za-z\\d!@#$%^&*]{8,}$",
            message = "Invalid password")
    public String password;
}
