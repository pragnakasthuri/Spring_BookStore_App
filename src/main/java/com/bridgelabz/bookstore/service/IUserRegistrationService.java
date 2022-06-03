package com.bridgelabz.bookstore.service;

import com.bridgelabz.bookstore.dto.LoginDTO;
import com.bridgelabz.bookstore.dto.UserRegistrationDTO;
import com.bridgelabz.bookstore.model.UserRegistrationData;
import java.util.List;
import java.util.Optional;

public interface IUserRegistrationService {

    List<UserRegistrationData> getUserRegistrationData();

    UserRegistrationData getUserRegistrationDataById(int userId);

    UserRegistrationData createUserRegistrationData(UserRegistrationDTO userRegistrationDTO);

    UserRegistrationData updateUserRegistrationData(int userId, UserRegistrationDTO userRegistrationDTO);

    UserRegistrationData getUserByEmailId(String emailId);

    void deleteUserRegistrationDataById(int userId);

    Optional<UserRegistrationData> userLogin(LoginDTO loginDTO);
}



