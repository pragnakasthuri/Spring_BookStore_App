package com.bridgelabz.bookstore.controller;

import com.bridgelabz.bookstore.dto.LoginDTO;
import com.bridgelabz.bookstore.dto.ResponseDTO;
import com.bridgelabz.bookstore.dto.UserRegistrationDTO;
import com.bridgelabz.bookstore.model.UserRegistrationData;
import com.bridgelabz.bookstore.service.IUserRegistrationService;
import com.bridgelabz.bookstore.util.TokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/userregistration")
@Slf4j
public class UserRegistrationController {

    @Autowired
    private IUserRegistrationService userRegistrationService;

    @Autowired
    private TokenUtil tokenUtil;


    @PostMapping("/register")
    public ResponseEntity<ResponseDTO> addUserRegistrationData(@Valid @RequestBody UserRegistrationDTO UserRegistrationDTO) {
        UserRegistrationData userRegistrationData = userRegistrationService.createUserRegistrationData(UserRegistrationDTO);
        String token = tokenUtil.createToken(userRegistrationData.getUserId());
        ResponseDTO responseDTO = new ResponseDTO("Added User Registration data Successfully", userRegistrationData, token);
        return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.OK);
    }

    @PutMapping("/update/{tokenId}")
    public ResponseEntity<ResponseDTO> updateUserRegistrationData(@PathVariable("tokenId") String tokenId,
                                                                  @Valid @RequestBody UserRegistrationDTO userRegistrationDTO) {
        int userId = tokenUtil.decodeToken(tokenId);
        UserRegistrationData userRegistrationData = userRegistrationService.updateUserRegistrationData(userId, userRegistrationDTO);
        ResponseDTO responseDTO = new ResponseDTO("Updated User Registration Data for Id", userRegistrationData);
        return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.OK);
    }

    @GetMapping("/get")
    public ResponseEntity<ResponseDTO> getUserRegistrationData() {
        List<UserRegistrationData> userRegistrationDataList = userRegistrationService.getUserRegistrationData();
        ResponseDTO responseDTO = new ResponseDTO("Get Call Success", userRegistrationDataList);
        return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.OK);
    }

    @GetMapping("/get/{userId}")
    public ResponseEntity<ResponseDTO> getUserRegistrationDataById(@PathVariable("userId") int userId) {
        UserRegistrationData userRegistrationData = userRegistrationService.getUserRegistrationDataById(userId);
        ResponseDTO responseDTO = new ResponseDTO("Get Call Success for Id", userRegistrationData);
        return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.OK);
    }

    @GetMapping("/getbyemail/{emailId}")
    public ResponseEntity<ResponseDTO> getUserByEmailId(@PathVariable("emailId") String emailId) {
        UserRegistrationData userRegistrationData = userRegistrationService.getUserByEmailId(emailId);
        ResponseDTO responseDTO = new ResponseDTO("Get Call Success for Email Id", userRegistrationData);
        return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<ResponseDTO> deleteUserRegistrationDataById(@PathVariable("userId") int userId) {
        userRegistrationService.deleteUserRegistrationDataById(userId);
        ResponseDTO response = new ResponseDTO("Delete call success for id ", "deleted id:" + userId);
        return new ResponseEntity<ResponseDTO>(response, HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<ResponseDTO> userLogin(@RequestBody LoginDTO loginDTO) {
        Optional<UserRegistrationData> login = userRegistrationService.userLogin(loginDTO);
        if(login != null) {
            ResponseDTO responseDTO = new ResponseDTO("Login Successful!", login);
            return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.ACCEPTED);
        } else {
            ResponseDTO responseDTO = new ResponseDTO("User login not successful", login);
            return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.ACCEPTED);
        }
    }
}
