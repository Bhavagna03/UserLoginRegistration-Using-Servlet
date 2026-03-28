package com.codegnan.service;

import java.util.List;

import com.codegnan.dto.User;

public interface UserService {

    String registerUser(User user);

    User getUserByUsername(String username);

    User getUserById(int id);   

    List<User> getAllUsers();

    String updateUser(User user);

    String deleteUser(int id);
}
