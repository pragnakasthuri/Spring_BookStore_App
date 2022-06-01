package com.bridgelabz.bookstore.model;

import com.bridgelabz.bookstore.dto.UserRegistrationDTO;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "userregistration_db")
@Data
public class UserRegistrationData {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int userId;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column
    private String address;

    @Column(name = "email_id")
    private String emailId;

    @Column
    private String password;


    public UserRegistrationData() {

    }

    public UserRegistrationData(UserRegistrationDTO userRegistrationDTO) {
        this.updateUserRegistrationData(userRegistrationDTO);
    }

    public void updateUserRegistrationData(UserRegistrationDTO userRegistrationDTO) {
        this.firstName = userRegistrationDTO.firstName;
        this.lastName = userRegistrationDTO.lastName;
        this.address = userRegistrationDTO.address;
        this.emailId = userRegistrationDTO.emailId;
        this.password = userRegistrationDTO.password;
    }
}