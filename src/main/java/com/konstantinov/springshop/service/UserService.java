package com.konstantinov.springshop.service;

import com.konstantinov.springshop.exception.EmailExistsException;
import com.konstantinov.springshop.model.Role;
import com.konstantinov.springshop.model.User;
import com.konstantinov.springshop.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class UserService {
    UserRepository userRepository;

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public void setbCryptPasswordEncoder(BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Transactional
    public User registerNewUserAccount(User user)
            throws EmailExistsException {
        if (userRepository.findByEmail(user.getEmail()) != null) {
            throw new EmailExistsException("email address already exists: " + user.getEmail());
        }
        user.setUsername(user.getUsername());
        user.setEmail(user.getEmail());
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setRole(Role.USER);
        user.setIsAccountNonLocked(Boolean.TRUE);

        return userRepository.save(user);
    }
}