package com.purely.services;

import com.purely.modals.Order;
import com.purely.modals.User;
import jakarta.mail.MessagingException;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.Set;

@Service
public interface NotificationService {

    void sendUserRegistrationVerificationEmail(User user) throws MessagingException, UnsupportedEncodingException;

    void sendForgotPasswordVerificationEmail(User user) throws MessagingException, UnsupportedEncodingException;

    void sendOrderConfirmationEmail(User user, Order order) throws MessagingException, UnsupportedEncodingException;
}
