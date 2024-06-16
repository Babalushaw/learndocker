package com.assignment.serviceImplementation;

import com.assignment.model.Role;
import com.assignment.model.User;
import com.assignment.repository.UserRepository;
import com.assignment.requestDto.LoginRequest;
import com.assignment.requestDto.SignupRequest;
import com.assignment.responseDto.LoginResponse;
import com.assignment.responseDto.SignupResponse;
import com.assignment.securityConfiguration.JWTTokenHelper;
import com.assignment.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

@Repository
public class AuthServiceImplementation implements AuthService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JWTTokenHelper jwtTokenHelper;
    @Override
    public LoginResponse signup(SignupRequest signupRequest) {
        User user=new User();
        user.setActive(true);
        user.setExpired(false);
        user.setLocked(false);
        user.setRole(Role.USER.name());
        user.setUsername(signupRequest.getEmail());
        user.setPassword(passwordEncoder.encode(signupRequest.getPassword()));
        userRepository.save(user);
        LoginRequest loginRequest=new LoginRequest();
        loginRequest.setPassword(signupRequest.getPassword());
        loginRequest.setEmail(signupRequest.getEmail());

        return logIn(loginRequest);
    }

    @Override
    public LoginResponse logIn(LoginRequest loginRequest) {
        doAuthenticate(loginRequest.getEmail(),loginRequest.getPassword());
        UserDetails userDetails=userDetailsService.loadUserByUsername(loginRequest.getEmail());
        String token =jwtTokenHelper.generateToken(userDetails);
        LoginResponse loginResponse=new LoginResponse(token, userDetails.getUsername());
        return loginResponse;
    }

    private void doAuthenticate(String email, String password) {
        UsernamePasswordAuthenticationToken authentication=new UsernamePasswordAuthenticationToken(email,password);
        try{
            authenticationManager.authenticate(authentication);
        }catch (BadCredentialsException e){
            throw new BadCredentialsException("Invalid username or password");
        }
    }
}
