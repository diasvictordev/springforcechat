package com.chat.chat.services;

import com.chat.chat.config.SecurityConfigurations;
import com.chat.chat.model.User;
import com.chat.chat.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServicesImpl implements UserServices {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public User signUpUser(User user){
        User user1 = new User();
        user1.setPassword(passwordEncoder.encode(user.getPassword()));
        user1.setName(user.getName());
        user1.setEmail(user.getEmail());
        return userRepository.save(user1);
    }

    @Override
    public User listUserById(Long id){
        Optional<User> user = userRepository.findById(id);
        return user.orElse(null);
    }

    @Override
    public List<User> listAllUsers(){
        return userRepository.findAll();
    }

    @Override
    public User editUser(User user){
        return userRepository.save(user);
    }

    @Override
    public void deleteUser(Long id){
        Optional<User> user = userRepository.findById(id);
        user.ifPresent(userRepository::delete);
    }
}
