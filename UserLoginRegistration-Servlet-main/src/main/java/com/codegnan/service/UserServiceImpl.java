package com.codegnan.service;

import java.util.List;

import com.codegnan.dao.UserDao;
import com.codegnan.dao.UserDaoImpl;
import com.codegnan.dto.User;

public class UserServiceImpl implements UserService {

    UserDao dao = new UserDaoImpl();

    @Override
    public String registerUser(User user) {
        return dao.save(user);
    }

    @Override
    public User getUserByUsername(String username) {
        return dao.findByUsername(username);
    }

    @Override
    public User getUserById(int id) {        // ✅ ADD THIS
        return dao.findById(id);
    }

    @Override
    public List<User> getAllUsers() {
        return dao.findAll();
    }

    @Override
    public String updateUser(User user) {
        return dao.update(user);
    }

    @Override
    public String deleteUser(int id) {
        return dao.deleteById(id);
    }
}
