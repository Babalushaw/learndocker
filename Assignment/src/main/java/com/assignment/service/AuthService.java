package com.assignment.service;

import com.assignment.requestDto.LoginRequest;
import com.assignment.requestDto.SignupRequest;
import com.assignment.responseDto.LoginResponse;
import com.assignment.responseDto.SignupResponse;
import org.springframework.stereotype.Service;

@Service
public interface AuthService {
    LoginResponse signup(SignupRequest signupRequest);

    LoginResponse logIn(LoginRequest loginRequest);
}
