package com.purely.services;

import com.purely.dtos.responses.ApiResponseDto;
import com.purely.dtos.requests.SignUpRequestDto;
import com.purely.exceptions.ServiceLogicException;
import com.purely.exceptions.UserAlreadyExistsException;
import com.purely.exceptions.UserNotFoundException;
import com.purely.exceptions.UserVerificationFailedException;
import jakarta.mail.MessagingException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;

@Service
public interface AuthService {
    ResponseEntity<ApiResponseDto<?>> save(SignUpRequestDto signUpRequestDto) throws MessagingException, UnsupportedEncodingException, UserAlreadyExistsException, ServiceLogicException;
    ResponseEntity<ApiResponseDto<?>> resendVerificationCode(String email) throws MessagingException, UnsupportedEncodingException, UserNotFoundException, ServiceLogicException;

    ResponseEntity<ApiResponseDto<?>> verifyRegistrationVerification(String code) throws UserVerificationFailedException;

}
