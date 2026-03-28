package com.codegnan.dao;

import java.util.List;

import com.codegnan.dto.User;

public interface UserDao {

    String save(User user);

    User findByUsername(String username);

    User findById(int id);    // ✅ ADD THIS

    List<User> findAll();

    String update(User user);

    String deleteById(int id);
}
