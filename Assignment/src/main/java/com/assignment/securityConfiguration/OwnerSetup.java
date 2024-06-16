package com.assignment.securityConfiguration;

import com.assignment.model.Role;
import com.assignment.model.User;
import com.assignment.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class OwnerSetup implements CommandLineRunner {
    @Value("${admin.password}")
    private String password;
    @Value("${admin.email}")
    private String email;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    @Autowired
    private UserRepository userRepository;
    @Override
    public void run(String... args) throws Exception {
       // log.info(userRepository.findAdmin(Role.ADMIN).getUsername());
        if(userRepository.findAdmin(Role.OWNER.name())==null){
            User user=new User();
            user.setUsername(email);
            user.setPassword(passwordEncoder.encode(password));
            user.setRole(Role.OWNER.name());
            user.setExpired(false);
            user.setLocked(false);
            user.setActive(true);
            userRepository.save(user);
        }
    }
}
