package com.bridgelabz.bookstore.service;

import com.bridgelabz.bookstore.dto.LoginDTO;
import com.bridgelabz.bookstore.dto.UserRegistrationDTO;
import com.bridgelabz.bookstore.exception.UserRegistrationException;
import com.bridgelabz.bookstore.model.UserRegistrationData;
import com.bridgelabz.bookstore.repository.UserRegistrationRepository;
import com.bridgelabz.bookstore.util.TokenUtil;
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

    @Autowired
    TokenUtil tokenUtil;

    /**
     * Implemented getUserRegistrationData to find all users
     * @return
     */
    @Override
    public List<UserRegistrationData> getUserRegistrationData() {
        return userRegistrationRepository.findAll();
    }

    /**
     * Implemented getUserRegistrationDataById method to find user by id
     * @param userId - passing userId param
     * @return
     */
    @Override
    public UserRegistrationData getUserRegistrationDataById(int userId) {
        return userRegistrationRepository.findById(userId)
                .orElseThrow(() -> new UserRegistrationException("User with id " + userId + " does not exists"));
    }

    /**
     * Implemented getUserByEmailId method to find user by email
     * @param emailId - passing email param
     * @return
     */
    @Override
    public UserRegistrationData getUserByEmailId(String emailId) {
        UserRegistrationData userRegistrationData = userRegistrationRepository.getUserByEmailId(emailId);
        if (userRegistrationData != null)
            return userRegistrationData;
        else
            throw new UserRegistrationException("User with email id " + emailId + " does not exists");
    }

    /***
     * Implemented createUserRegistrationData to create user
     * @param userRegistrationDTO - passing userRegistrationDTO param
     * @return
     */
    @Override
    public UserRegistrationData createUserRegistrationData(UserRegistrationDTO userRegistrationDTO) {
        UserRegistrationData userRegistrationData = new UserRegistrationData(userRegistrationDTO);
        return userRegistrationRepository.save(userRegistrationData);
    }

    /***
     * Implemented updateUserRegistrationData to update user
     * @param userId - passing userId param
     * @param userRegistrationDTO - passing userRegistrationDTO param
     * @return
     */
    @Override
    public UserRegistrationData updateUserRegistrationData(int userId, UserRegistrationDTO userRegistrationDTO) {
        UserRegistrationData userRegistrationData = this.getUserRegistrationDataById(userId);
        userRegistrationData.updateUserRegistrationData(userRegistrationDTO);
        return userRegistrationRepository.save(userRegistrationData);
    }

    /**
     * Implemented deleteUserRegistrationDataById to delete user by id
     * @param userId
     */
    @Override
    public void deleteUserRegistrationDataById(int userId) {
        UserRegistrationData userData = this.getUserRegistrationDataById(userId);
        userRegistrationRepository.delete(userData);
    }

    /***
     * Implemented userLogin to login user
     * @param loginDTO - passing loginDTO param
     * @return
     */
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

    /**
     * Implemented verifyUser method to verify user
     * @param token - passing token param
     * @return
     */
    @Override
    public UserRegistrationData verifyUser(String token) {
        UserRegistrationData userRegistrationData = this.getUserRegistrationDataById(tokenUtil.decodeToken(token));
        userRegistrationData.setVerified(true);
        return userRegistrationRepository.save(userRegistrationData);
    }
}
