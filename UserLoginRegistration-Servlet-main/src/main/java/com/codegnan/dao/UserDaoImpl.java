package com.codegnan.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.codegnan.dto.User;
import com.codegnan.factory.ConnectionFactory;

public class UserDaoImpl implements UserDao {

    @Override
    public String save(User u) {
        String status = "failure";
        try {
            Connection con = ConnectionFactory.getConnection();

            PreparedStatement ps =
                con.prepareStatement(
                    "INSERT INTO users(username,password,email) VALUES(?,?,?)");

            ps.setString(1, u.getUsername());
            ps.setString(2, u.getPassword());
            ps.setString(3, u.getEmail());

            if (ps.executeUpdate() == 1)
                status = "success";

            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return status;
    }

    @Override
    public User findByUsername(String username) {
        User u = null;
        try {
            Connection con = ConnectionFactory.getConnection();
            PreparedStatement ps =
                con.prepareStatement("SELECT * FROM users WHERE username=?");

            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                u = new User(
                        rs.getInt("id"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("email"));
            }
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return u;
    }

    @Override
    public User findById(int id) {     // ✅ REQUIRED METHOD
        User u = null;
        try {
            Connection con = ConnectionFactory.getConnection();
            PreparedStatement ps =
                con.prepareStatement("SELECT * FROM users WHERE id=?");

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                u = new User(
                        rs.getInt("id"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("email"));
            }
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return u;
    }

    @Override
    public List<User> findAll() {
        List<User> list = new ArrayList<>();
        try {
            Connection con = ConnectionFactory.getConnection();
            PreparedStatement ps =
                con.prepareStatement("SELECT * FROM users");

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new User(
                        rs.getInt("id"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("email")));
            }
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public String update(User u) {
        String status = "failure";
        try {
            Connection con = ConnectionFactory.getConnection();
            PreparedStatement ps =
                con.prepareStatement(
                    "UPDATE users SET username=?,password=?,email=? WHERE id=?");

            ps.setString(1, u.getUsername());
            ps.setString(2, u.getPassword());
            ps.setString(3, u.getEmail());
            ps.setInt(4, u.getId());

            if (ps.executeUpdate() == 1)
                status = "success";

            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return status;
    }

    @Override
    public String deleteById(int id) {
        String status = "failure";
        try {
            Connection con = ConnectionFactory.getConnection();
            PreparedStatement ps =
                con.prepareStatement("DELETE FROM users WHERE id=?");

            ps.setInt(1, id);
            if (ps.executeUpdate() == 1)
                status = "success";

            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return status;
    }
}
