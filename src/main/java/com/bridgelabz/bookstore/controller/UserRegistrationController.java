package com.bridgelabz.bookstore.controller;

import com.bridgelabz.bookstore.dto.LoginDTO;
import com.bridgelabz.bookstore.dto.ResponseDTO;
import com.bridgelabz.bookstore.dto.UserRegistrationDTO;
import com.bridgelabz.bookstore.model.Email;
import com.bridgelabz.bookstore.model.UserRegistrationData;
import com.bridgelabz.bookstore.service.IEmailService;
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
    private IEmailService emailService;

    @Autowired
    private TokenUtil tokenUtil;


    /***
     * Implemented createUserRegistrationData to create user registration
     * After registering a mail will send to the registered email id
     * @param userRegistrationDTO - passing userRegistrationDTO as param
     * @return
     */
    @PostMapping("/register")
    public ResponseEntity<ResponseDTO> addUserRegistrationData(@Valid @RequestBody UserRegistrationDTO userRegistrationDTO) {
        UserRegistrationData userRegistrationData = userRegistrationService.createUserRegistrationData(userRegistrationDTO);
        String token = tokenUtil.createToken(userRegistrationData.getUserId());
        Email email = new Email(userRegistrationData.getEmailId(), "Registered Successfully",
                        "Hi " + userRegistrationData.getFirstName() + " " +userRegistrationData.getLastName() +
                        ", Click the below link to verify \n" + emailService.getLink(token));
        emailService.sendEmail(email);
        ResponseDTO responseDTO = new ResponseDTO("Added User Registration data Successfully", userRegistrationData, token);
        return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.OK);
    }

    /***
     * Implemented updateUserRegistrationData method to update user by id
     * @param token - passing token as param
     * @param userRegistrationDTO - passing userRegistrationDTO as param
     * @return
     */
    @PutMapping("/update/{token}")
    public ResponseEntity<ResponseDTO> updateUserRegistrationData(@PathVariable("token") String token,
                                                                  @Valid @RequestBody UserRegistrationDTO userRegistrationDTO) {
        int tokenId = tokenUtil.decodeToken(token);
        UserRegistrationData userRegistrationData = userRegistrationService.updateUserRegistrationData(tokenId, userRegistrationDTO);
        ResponseDTO responseDTO = new ResponseDTO("Updated User Registration Data for Id", userRegistrationData);
        return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.OK);
    }

    /***
     * Implemented getUserRegistrationData method to get all the users from the database
     * @return
     */
    @GetMapping("/get")
    public ResponseEntity<ResponseDTO> getUserRegistrationData() {
        List<UserRegistrationData> userRegistrationDataList = userRegistrationService.getUserRegistrationData();
        ResponseDTO responseDTO = new ResponseDTO("Get Call Success", userRegistrationDataList);
        return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.OK);
    }

    /***
     * Implemented getUserRegistrationDataById method to get user by id from database
     * @param token - passing token as param
     * @return
     */
    @GetMapping("/get/{token}")
    public ResponseEntity<ResponseDTO> getUserRegistrationDataById(@PathVariable("token") String token) {
        int tokenId = tokenUtil.decodeToken(token);
        UserRegistrationData userRegistrationData = userRegistrationService.getUserRegistrationDataById(tokenId);
        ResponseDTO responseDTO = new ResponseDTO("Get Call Success for Id", userRegistrationData);
        return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.OK);
    }

    /***
     * Implemented getUserByEmailId method to get user by email id from database
     * @param emailId - passing email as param
     * @return
     */
    @GetMapping("/getbyemail/{emailId}")
    public ResponseEntity<ResponseDTO> getUserByEmailId(@PathVariable("emailId") String emailId) {
        UserRegistrationData userRegistrationData = userRegistrationService.getUserByEmailId(emailId);
        ResponseDTO responseDTO = new ResponseDTO("Get Call Success for Email Id", userRegistrationData);
        return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.OK);
    }

    /***
     * Implemented deleteUserRegistrationDataById method to update user by id
     * @param token - passing token as param
     * @return
     */
    @DeleteMapping("/delete/{token}")
    public ResponseEntity<ResponseDTO> deleteUserRegistrationDataById(@PathVariable("token") String token) {
        int tokenId = tokenUtil.decodeToken(token);
        userRegistrationService.deleteUserRegistrationDataById(tokenId);
        ResponseDTO response = new ResponseDTO("Delete call success for id ", "deleted id:" + tokenId);
        return new ResponseEntity<ResponseDTO>(response, HttpStatus.OK);
    }

    /***
     * Implemented userLogin method for user login
     * @param loginDTO - passing loginDTO as param
     * @return
     */
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

    /***
     * Implemented verifyUser method, while registering user a mail with verification link
     * will send to the registered user email id, by clicking on the link user details is going to display
     * @param token - passing token as param
     * @return
     */
    @GetMapping("/verify/{token}")
    public ResponseEntity<ResponseDTO> verifyUser(@PathVariable("token") String token) {
        UserRegistrationData userRegistrationData = userRegistrationService.verifyUser(token);
        if (userRegistrationData.isVerified()) {
            ResponseDTO responseDTO = new ResponseDTO("User has been verified", userRegistrationData, token);
            return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.OK);
        }
        else {
            ResponseDTO responseDTO = new ResponseDTO("ERROR : Invalid Token", null, token);
            return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.OK);
        }
    }
}
