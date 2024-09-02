package com.chat.chat.services;

import com.chat.chat.model.User;

import java.util.List;

public interface UserServices {

    User signUpUser(User user);


    User listUserById(Long id);

    List<User> listAllUsers();

    User editUser(User user);

    void deleteUser(Long id);
}
