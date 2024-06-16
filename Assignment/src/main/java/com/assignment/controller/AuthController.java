package com.assignment.controller;

import com.assignment.requestDto.LoginRequest;
import com.assignment.requestDto.SignupRequest;
import com.assignment.responseDto.LoginResponse;
import com.assignment.service.AuthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@Slf4j
public class AuthController {
    @Autowired
    private AuthService authService;
    @PostMapping("/signup")
    public ResponseEntity<LoginResponse> signUp(@RequestBody SignupRequest signupRequest){
        return new ResponseEntity<>(authService.signup(signupRequest), HttpStatus.CREATED);
    }
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> logIn(@RequestBody LoginRequest loginRequest){
        return new ResponseEntity<>(authService.logIn(loginRequest), HttpStatus.OK);
    }
    @GetMapping("/manish")
    public String print(){
        return "hello Mr Manish";
    }
}
