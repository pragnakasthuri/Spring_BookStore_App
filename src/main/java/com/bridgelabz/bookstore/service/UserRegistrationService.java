package com.bridgelabz.bookstore.service;

import com.bridgelabz.bookstore.dto.LoginDTO;
import com.bridgelabz.bookstore.dto.UserRegistrationDTO;
import com.bridgelabz.bookstore.exception.UserRegistrationException;
import com.bridgelabz.bookstore.model.UserRegistrationData;
import com.bridgelabz.bookstore.repository.UserRegistrationRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class UserRegistrationService implements IUserRegistrationService{
    @Autowired
    private UserRegistrationRepository userRegistrationRepository;

    @Override
    public List<UserRegistrationData> getUserRegistrationData() {
        return userRegistrationRepository.findAll();
    }

    @Override
    public UserRegistrationData getUserRegistrationDataById(int userId) {
        return userRegistrationRepository.findById(userId)
                .orElseThrow(() -> new UserRegistrationException("User with id " + userId + " does not exists"));
    }

    @Override
    public UserRegistrationData getUserByEmailId(String emailId) {
        UserRegistrationData userRegistrationData = userRegistrationRepository.getUserByEmailId(emailId);
        if (userRegistrationData != null)
            return userRegistrationData;
        else
            throw new UserRegistrationException("User with email id " + emailId + " does not exists");
    }

    @Override
    public UserRegistrationData createUserRegistrationData(UserRegistrationDTO userRegistrationDTO) {
        UserRegistrationData userRegistrationData = new UserRegistrationData(userRegistrationDTO);
        return userRegistrationRepository.save(userRegistrationData);
    }

    @Override
    public UserRegistrationData updateUserRegistrationData(int userId, UserRegistrationDTO userRegistrationDTO) {
        UserRegistrationData userRegistrationData = this.getUserRegistrationDataById(userId);
        userRegistrationData.updateUserRegistrationData(userRegistrationDTO);
        return userRegistrationRepository.save(userRegistrationData);
    }

    @Override
    public void deleteUserRegistrationDataById(int userId) {
        UserRegistrationData userData = this.getUserRegistrationDataById(userId);
        userRegistrationRepository.delete(userData);
    }

    @Override
    public Optional<UserRegistrationData> userLogin(LoginDTO loginDTO) {
        Optional<UserRegistrationData> userLogin = userRegistrationRepository.findByEmailIdAndPassword(loginDTO.emailId, loginDTO.password);
        if (userLogin.isPresent()) {
            log.info("Login Successful");
            return userLogin;
        } else {
            log.error("User not Found Exception");
            return null;
        }
    }
}
