package com.purely.controllers;

import com.purely.dtos.responses.ApiResponseDto;
import com.purely.dtos.requests.SignUpRequestDto;
import com.purely.exceptions.ServiceLogicException;
import com.purely.exceptions.UserAlreadyExistsException;
import com.purely.exceptions.UserNotFoundException;
import com.purely.exceptions.UserVerificationFailedException;
import com.purely.services.AuthService;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/purely/auth")
public class SignUpController {
    @Autowired
    private AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<ApiResponseDto<?>> registerUser(@RequestBody @Valid SignUpRequestDto signUpRequestDto)
            throws MessagingException, UnsupportedEncodingException, UserAlreadyExistsException, ServiceLogicException {
        return authService.save(signUpRequestDto);
    }

    @GetMapping("/signup/verify")
    public ResponseEntity<ApiResponseDto<?>> verifyUserRegistration(@Param("code") String code)
            throws UserVerificationFailedException {
        return authService.verifyRegistrationVerification(code);
    }

    @GetMapping("/signup/resend")
    public ResponseEntity<ApiResponseDto<?>> resendVerificationCode(@Param("email") String email)
            throws UserNotFoundException, MessagingException, UnsupportedEncodingException, ServiceLogicException {
        return authService.resendVerificationCode(email);
    }

}
