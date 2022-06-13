package com.bridgelabz.bookstore.service;

import com.bridgelabz.bookstore.dto.ResponseDTO;
import com.bridgelabz.bookstore.model.Email;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

@Service
@Slf4j
public class EmailService implements IEmailService {

    /**
     * Implemented sendEmail method to send mail to the user while creating user and ordering
     * @param email - passing emailData param
     * @return
     */
    @Override
    public ResponseEntity<ResponseDTO> sendEmail(Email email) {
        final String fromEmail = "kpragna25@gmail.com";
        final String fromPassword = "bemtpnplozlncdwq";

        Properties properties = new Properties();
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.ssl.trust", "smtp.gmail.com");

        Authenticator authenticator = new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(fromEmail, fromPassword);
            }
        };

        Session session = Session.getInstance(properties, authenticator);
        MimeMessage mail = new MimeMessage(session);
        try {
            mail.addHeader("Content-type", "text/HTML; charset=UTF-8");
            mail.addHeader("format", "flowed");
            mail.addHeader("Content-Transfer-Encoding", "8bit");

            mail.setFrom(new InternetAddress(fromEmail));
            mail.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email.getTo()));
            mail.setText(email.getBody(), "UTF-8");
            mail.setSubject(email.getSubject(), "UTF-8");

            Transport.send(mail);
            ResponseDTO responseDTO = new ResponseDTO("Sent email ", mail,null);
            return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.OK);
        } catch (MessagingException e) {
            e.printStackTrace();
        }

        ResponseDTO responseDTO = new ResponseDTO("ERROR : Couldn't send email", null,null);
        return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Implemented getLink method to pass it in the user verify email
     * @param token - passing token param
     * @return
     */
    @Override
    public String getLink(String token) {
        return "http://localhost:8085/userregistration/verify/" + token;
    }

    /**
     * Implemented getOrderLink method to pass it in the order email
     * @param token - passing token param
     * @return
     */
    @Override
    public String getOrderLink(String token) {
        return "http://localhost:8085/order/verify/" + token;
    }
}
