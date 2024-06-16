package com.assignment.securityConfiguration;

import com.assignment.model.User;
import com.assignment.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl  implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user=userRepository.findByEmail(username).orElseThrow(()-> new RuntimeException("User not found"));
        CustomUserDetails customUser=new CustomUserDetails();
        customUser.setUsername(user.getUsername());
        customUser.setPassword(user.getPassword());
        customUser.setRole(user.getRole());
        customUser.setExpired(user.isExpired());
        customUser.setActive(user.isActive());
        customUser.setLocked(user.isLocked());
        customUser.setRole(user.getRole());
        return customUser;
    }
}
